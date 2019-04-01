package com.lynpo.video;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lynpo.video.model.ItemBean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * user: fujw
 * date: 2018/6/8
 * version: 8.0.1
 */

public class ScrollLoadMoreWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_LOAD_FAILED_VIEW = Integer.MAX_VALUE - 1;
    private static final int ITEM_TYPE_NO_MORE_VIEW = Integer.MAX_VALUE - 2;
    private static final int ITEM_TYPE_LOAD_MORE_VIEW = Integer.MAX_VALUE - 3;
    private static final int ITEM_TYPE_NO_VIEW = Integer.MAX_VALUE - 4;//不展示footer view

    private Context mContext;
    private ItemAdapter mInnerAdapter;

    private View mLoadMoreView;
    private View mLoadMoreFailedView;
    private View mNoMoreView;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    private int mCurrentItemType = ITEM_TYPE_LOAD_MORE_VIEW;
    private LoadMoreScrollListener mLoadMoreScrollListener;

    private boolean isLoadError = false;//标记是否加载出错
    private boolean isHaveStatesView = true;
    private boolean isLoading = false;
    private boolean hasData = true;

    private boolean mEnableAutoPlay = true;

    public ScrollLoadMoreWrapper(Context context, ItemAdapter adapter) {
        this.mContext = context;
        this.mInnerAdapter = adapter;
        mLoadMoreScrollListener = new LoadMoreScrollListener() {
            @Override
            public void loadMore() {
                if (mOnLoadListener != null && isHaveStatesView) {
                    if (!isLoadError && !isLoading && hasData) {
                        showLoadMore();
                        isLoading = true;
                        mOnLoadListener.onLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy != 0) {
                    findVideoItemToPlay(false, dy > 0);
                }
            }
        };
    }

    private int mAppbarVerticalOffset;
    private int mAppbarTotalScrollRange;
    public void onAppbarOffsetChanged(int verticalOffset, int totalScrollRange) {
        mAppbarTotalScrollRange = totalScrollRange;
        int appbarVerticalOffset = Math.abs(verticalOffset);
        boolean scrollUp = appbarVerticalOffset > mAppbarVerticalOffset;
        int scrollDistance = appbarVerticalOffset - mAppbarVerticalOffset;
        mAppbarVerticalOffset = appbarVerticalOffset;

        if (mLayoutManager == null || mLayoutManager.getChildCount() <= 0) {
            return;
        }

        if (scrollDistance > 0) {
            findVideoItemToPlay(true, scrollUp);
        }
    }

    private void findVideoItemToPlay(boolean appbarScroll, boolean scrollUp) {
        if (!mEnableAutoPlay) return;
        if (JZVideoPlayer.getScreenMode() != ScreenMode.LIST) return;

        int start = findFirstValidPosition();
        int end = findLastValidPosition(appbarScroll, scrollUp);
        ItemBean currentPlayingItem = mInnerAdapter.getCurrentPlayingItem();
        ItemBean item = findPlayItem(start, end);
        if (start <= end) {
            if (item != null) {
                if (item != currentPlayingItem) {
                    performPauseOneAndPlayAnother(currentPlayingItem, item);
                } else {
                    performPlayWhenItemStop(item);
                }
            } else if (currentPlayingItem != null) {
                performPauseOneAndPlayAnother(currentPlayingItem, null);
            }
        } else if (currentPlayingItem != null) {
            performPauseOneAndPlayAnother(currentPlayingItem, null);
        }
    }

    private void performPlayWhenItemStop(@NonNull ItemBean playItem) {
        View playViewOfPos = mLayoutManager.findViewByPosition(playItem.getAdapterPosition());
        JZVideoPlayerStandard playVideoView = playViewOfPos.findViewById(mInnerAdapter.getVideoViewId());

        if (playVideoView != null && playVideoView.getPlayStatus() != PlayStatus.PLAYING && playVideoView.getPlayStatus() != PlayStatus.COMPLETE && playVideoView.getPlayStatus() != PlayStatus.ERROR) {
            if (playVideoView.currentState == JZVideoPlayer.CURRENT_STATE_PREPARING) {
                return;
            }
            playVideoView.setTag(PlayerConst.TAG_SOFT_CLICK_PLAY);
            playVideoView.performClick();
            playVideoView.setPlayStatus(PlayStatus.PLAYING);
            mInnerAdapter.setCurrentPlayingItem(playItem);
        }
    }

    private long pauseTimeMills;
    private void performPauseOneAndPlayAnother(@Nullable ItemBean pauseItem, @Nullable ItemBean playItem) {
        JZVideoPlayerStandard playVideoView = null;
        JZVideoPlayerStandard pauseVideoView = null;
        if (playItem != null) {
            View playViewOfPos = mLayoutManager.findViewByPosition(playItem.getAdapterPosition());
            if (playViewOfPos != null) {
                playVideoView = playViewOfPos.findViewById(mInnerAdapter.getVideoViewId());
            }
        }
        if (pauseItem != null) {
            View pauseViewOfPos = mLayoutManager.findViewByPosition(pauseItem.getAdapterPosition());
            if (pauseViewOfPos != null) {
                pauseVideoView = pauseViewOfPos.findViewById(mInnerAdapter.getVideoViewId());
            }
        }

        if (playItem != null && playVideoView != null) {
            if (playVideoView.getPlayStatus() != PlayStatus.COMPLETE && playVideoView.currentState != JZVideoPlayer.CURRENT_STATE_AUTO_COMPLETE) {
                if (playVideoView.currentState == JZVideoPlayer.CURRENT_STATE_PREPARING) {
                    return;
                }
                playVideoView.setTag(PlayerConst.TAG_SOFT_CLICK_PLAY);
                final View videoView = playVideoView;
                if (System.currentTimeMillis() - pauseTimeMills < 300) {
                    videoView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            videoView.performClick();
                        }
                    }, 200);
                } else {
                    playVideoView.performClick();
                }
                mInnerAdapter.setCurrentPlayingItem(playItem);
            } else if (pauseItem != null && pauseVideoView != null){
                performPauseItem(pauseVideoView);
            }
        } else if (pauseItem != null && pauseVideoView != null) {
            performPauseItem(pauseVideoView);
        }
    }

    private void performPauseItem(JZVideoPlayerStandard pauseVideoView) {
        if (pauseVideoView.currentState == JZVideoPlayer.CURRENT_STATE_PREPARING) {
            JZVideoPlayer.releaseAllVideos();
        } else {
            pauseVideoView.setTag(PlayerConst.TAG_SOFT_CLICK_PAUSE);
            pauseVideoView.performClick();
        }
        pauseVideoView.showCoverImage(true);
        mInnerAdapter.setCurrentPlayingItem(null);
        pauseTimeMills = System.currentTimeMillis();
    }

    /**
     * 查找符合播放条件的 item, start from {@param start} to {@param end}
     * @param start where to start from
     * @param end where to stop looking
     */
    private ItemBean findPlayItem(int start, int end) {
        ItemBean item;
        for (int i = start; i <= end; i++) {
            item = mInnerAdapter.getItem(i);
            if (item != null && item.isMediaVideo()) {
                item.setAdapterPosition(i);
                return item;
            }
        }
        return null;
    }

    private int findFirstValidPosition() {
        int firstVisiblePosition = mLayoutManager.findFirstVisibleItemPosition();
        View viewOfPos = mLayoutManager.findViewByPosition(firstVisiblePosition);
        View videoView = viewOfPos.findViewById(mInnerAdapter.getVideoViewId());
        if (videoView == null) {
            return firstVisiblePosition + 1;
        }

        int itemViewTop = viewOfPos.getTop();
        int top = videoView.getTop();
        int videoHeight = videoView.getHeight();

        if (itemViewTop > 0 || Math.abs(itemViewTop) < top + videoHeight / 2) {
            return firstVisiblePosition;
        }

        return firstVisiblePosition + 1;
    }

    private int findLastValidPosition(boolean appbarCollapsing, boolean scrollUp) {
        int lastVisiblePosition = mLayoutManager.findLastVisibleItemPosition();
        View viewOfPos = mLayoutManager.findViewByPosition(lastVisiblePosition);
        View videoView = viewOfPos.findViewById(mInnerAdapter.getVideoViewId());
        if (videoView == null) {
            return lastVisiblePosition - 1;
        }

        int itemViewHeight = viewOfPos.getHeight();
        int itemViewBottom = viewOfPos.getBottom();
        int bottomDistance = itemViewHeight - videoView.getBottom();
        int recyclerViewBottom = mRecyclerView.getBottom();

        if (appbarCollapsing) {
            if (scrollUp) {
                if (itemViewBottom < recyclerViewBottom + bottomDistance) {
                    return lastVisiblePosition;
                }
            } else {
                if (itemViewBottom + (mAppbarVerticalOffset == 0 ? mAppbarTotalScrollRange : mAppbarVerticalOffset) < recyclerViewBottom + bottomDistance) {
                    return lastVisiblePosition;
                }
            }
        } else {
            if (scrollUp) {
                if (itemViewBottom < recyclerViewBottom + bottomDistance) {
                    return lastVisiblePosition;
                }
            } else {
                if (itemViewBottom + mAppbarTotalScrollRange < recyclerViewBottom + bottomDistance) {
                    return lastVisiblePosition;
                }
            }
        }

        return lastVisiblePosition - 1;
    }

    public void enableAutoPlay(boolean enable) {
        mEnableAutoPlay = enable;
        JZVideoPlayer.enableAutoPlay(enable);
        if (JZVideoPlayer.getScreenMode() == ScreenMode.LIST && !enable) {
            JZVideoPlayer player = JZVideoPlayerManager.getCurrentJzvd();
            if (player != null) {
                player.pause();
            }
        }
    }

    public void showLoadMore() {
        mCurrentItemType = ITEM_TYPE_LOAD_MORE_VIEW;
        isLoadError = false;
        isHaveStatesView = true;
        isLoading = false;
        hasData = true;
        notifyItemChanged(getItemCount());
    }

    public void showLoadError() {
        mCurrentItemType = ITEM_TYPE_LOAD_FAILED_VIEW;
        isLoadError = true;
        isHaveStatesView = true;
        isLoading = false;
        hasData = true;
        notifyItemChanged(getItemCount());
    }

    public void showLoadComplete() {
        mCurrentItemType = ITEM_TYPE_NO_MORE_VIEW;
        isLoadError = false;
        isHaveStatesView = true;
        isLoading = false;
        hasData = false;
        notifyItemChanged(getItemCount());
    }

    public void disableLoadMore() {
        mCurrentItemType = ITEM_TYPE_NO_VIEW;
        isHaveStatesView = false;
        isLoading = false;
        hasData = false;
        notifyDataSetChanged();
    }

    //region Get ViewHolder
    private ViewHolder getLoadMoreViewHolder() {
        if (mLoadMoreView == null) {
            mLoadMoreView = new TextView(mContext);
            mLoadMoreView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mLoadMoreView.setPadding(45, 45, 45, 45);
            ((TextView) mLoadMoreView).setText("正在加载中");
            ((TextView) mLoadMoreView).setTextSize(16);
            ((TextView) mLoadMoreView).setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
            ((TextView) mLoadMoreView).setGravity(Gravity.CENTER);
        }
        return ViewHolder.createViewHolder(mContext, mLoadMoreView);
    }

    private ViewHolder getLoadFailedViewHolder() {
        if (mLoadMoreFailedView == null) {
            mLoadMoreFailedView = new TextView(mContext);
            mLoadMoreFailedView.setPadding(45, 45, 45, 45);
            mLoadMoreFailedView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((TextView) mLoadMoreFailedView).setText("加载失败");
            ((TextView) mLoadMoreFailedView).setTextSize(16);
            ((TextView) mLoadMoreFailedView).setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
            ((TextView) mLoadMoreFailedView).setGravity(Gravity.CENTER);
        }
        return ViewHolder.createViewHolder(mContext, mLoadMoreFailedView);
    }

    private ViewHolder getNoMoreViewHolder() {
        if (mNoMoreView == null) {
            mNoMoreView = new TextView(mContext);
            mNoMoreView.setPadding(45, 45, 45, 45);
            mNoMoreView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((TextView) mNoMoreView).setText("没有更多内容啦");
            ((TextView) mNoMoreView).setTextSize(16);
            ((TextView) mNoMoreView).setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
            ((TextView) mNoMoreView).setGravity(Gravity.CENTER);
        }
        return ViewHolder.createViewHolder(mContext, mNoMoreView);
    }
    //endregion

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && isHaveStatesView) {
            return mCurrentItemType;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NO_MORE_VIEW) {
            return getNoMoreViewHolder();
        } else if (viewType == ITEM_TYPE_LOAD_MORE_VIEW) {
            return getLoadMoreViewHolder();
        } else if (viewType == ITEM_TYPE_LOAD_FAILED_VIEW) {
            return getLoadFailedViewHolder();
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_TYPE_LOAD_FAILED_VIEW) {
            mLoadMoreFailedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLoadListener != null) {
                        mOnLoadListener.onRetry();
                        showLoadMore();
                    }
                }
            });
            return;
        }
        if (!isFooterType(holder.getItemViewType()))
            mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
//            @Override
//            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
//                if (position == getItemCount() - 1 && isHaveStatesView) {
//                    return layoutManager.getSpanCount();
//                }
//                if (oldLookup != null && isHaveStatesView) {
//                    return oldLookup.getSpanSize(position);
//                }
//                return 1;
//            }
//        });
        recyclerView.addOnScrollListener(mLoadMoreScrollListener);

        mRecyclerView = recyclerView;
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (holder.getLayoutPosition() == getItemCount() - 1 && isHaveStatesView) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

                p.setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (isHaveStatesView ? 1 : 0);
    }

    public boolean isFooterType(int type) {

        return type == ITEM_TYPE_NO_VIEW ||
                type == ITEM_TYPE_LOAD_FAILED_VIEW ||
                type == ITEM_TYPE_NO_MORE_VIEW ||
                type == ITEM_TYPE_LOAD_MORE_VIEW;
    }
    //region 加载监听

    public interface OnLoadListener {
        void onRetry();
        void onLoadMore();
    }

    private OnLoadListener mOnLoadListener;

    public ScrollLoadMoreWrapper setOnLoadListener(OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
        return this;
    }
}
