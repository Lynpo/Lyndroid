package com.lynpo.video;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lynpo.video.util.NetworkUtil;
import com.lynpo.video.util.SPUtil;

import java.util.Timer;
import java.util.TimerTask;

/**jum
 * Created by Nathen
 * On 2016/04/18 16:15
 */
public class JZVideoPlayerStandard extends JZVideoPlayer {

    protected static Timer DISMISS_CONTROL_VIEW_TIMER;

    public ImageView backButton;
    public ProgressBar loadingProgressBar;
    public ImageView thumbImageView;

    protected DismissControlViewTimerTask mDismissControlViewTimerTask;
    protected Dialog mProgressDialog;
    protected ProgressBar mDialogProgressBar;
    protected TextView mDialogSeekTime;
    protected TextView mDialogTotalTime;
    protected TextView mOpenCourseButton;
    protected ImageView mDialogIcon;
    protected Dialog mVolumeDialog;
    protected ProgressBar mDialogVolumeProgressBar;
    protected TextView mDialogVolumeTextView;
    protected ImageView mDialogVolumeImageView;
    protected View mFullScreenCover;
    private TextView mTimesLeftCount;

    private long mVideoDuration;

    private AlertDialog mMobilePlayDialog;

    public JZVideoPlayerStandard(Context context) {
        super(context);
    }

    public JZVideoPlayerStandard(Context context, String openCourseUrl, ScreenMode mode) {
        super(context, openCourseUrl, mode);
    }

    public JZVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        if (mVideoView == null) {
            return;
        }
        backButton = mVideoView.findViewById(R.id.back);
        thumbImageView = mVideoView.findViewById(R.id.thumb);
        loadingProgressBar = mVideoView.findViewById(R.id.loading);
        mOpenCourseButton = mVideoView.findViewById(R.id.jumpToOpenCourseButton);
        mFullScreenCover = mVideoView.findViewById(R.id.cover);
        mTimesLeftCount = mVideoView.findViewById(R.id.timesLeftCount);

        dispatchOpenCourseButtonShowOrHide();

        showOperationViewByScreenMode();

        thumbImageView.setOnClickListener(this);
        mFullScreenCover.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        JZVideoPlayer player = JZVideoPlayerManager.getCurrentJzvd();
        if (player != null) {
            player.setFullscreenImageByVideoType();
            dispatchOpenCourseButtonShowOrHide();
        }
    }

    @Override
    public void dispatchOpenCourseButtonShowOrHide() {
        super.dispatchOpenCourseButtonShowOrHide();
        if (!TextUtils.isEmpty(mOpenCourseUrl) && mScreenMode == ScreenMode.PORTRAIT) {
            mOpenCourseButton.setVisibility(VISIBLE);
            mOpenCourseButton.setOnClickListener(this);
        } else {
            mOpenCourseButton.setVisibility(GONE);
        }
    }

    public void setUp(Object[] dataSourceObjects, int defaultUrlMapIndex, int screen, Object... objects) {
        super.setUp(dataSourceObjects, defaultUrlMapIndex, screen, objects);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            setFullScreenImage(true);
            backButton.setVisibility(View.VISIBLE);
            changeStartButtonSize((int) getResources().getDimension(R.dimen.jz_start_button_w_h_fullscreen));
        } else if (currentScreen == SCREEN_WINDOW_NORMAL
                || currentScreen == SCREEN_WINDOW_LIST) {
            setFullScreenImage(false);
            backButton.setVisibility(View.GONE);
            changeStartButtonSize((int) getResources().getDimension(R.dimen.jz_start_button_w_h_normal));
        }
    }

    public void setVideoDuration(long duration) {
        mVideoDuration = duration;
        mTimesLeftCount.setText(JZUtils.stringForTime(mVideoDuration));
        if (mScreenMode == ScreenMode.LIST) {
            mTimesLeftCount.setVisibility(VISIBLE);
        }
    }

    public void showCoverImage(boolean show) {
        if (show) {
            thumbImageView.setVisibility(VISIBLE);
            textureViewContainer.setVisibility(INVISIBLE);
        } else {
            thumbImageView.setVisibility(INVISIBLE);
            textureViewContainer.setVisibility(VISIBLE);
        }
    }

    public void changeStartButtonSize(int size) {
        ViewGroup.LayoutParams lp = startButton.getLayoutParams();
        lp.height = size;
        lp.width = size;
        lp = loadingProgressBar.getLayoutParams();
        lp.height = size;
        lp.width = size;
    }

    @Override
    public void fullScreenPlayAndCheckingWifi() {
        super.fullScreenPlayAndCheckingWifi();

        JZVideoPlayer currPlayer = JZVideoPlayerManager.getCurrentJzvd();

        if (currPlayer != null && currPlayer != this) {
            clickStart(PlayerConst.TAG_SOFT_CLICK_PLAY);
        }
        if (currPlayer != null && currPlayer != this && (mPlayStatus == PlayStatus.NORMAL || mPlayStatus == PlayStatus.COMPLETE
                || currentState == CURRENT_STATE_NORMAL || currentState == CURRENT_STATE_AUTO_COMPLETE)) {
            JZVideoPlayerManager.completeAll();
        }
        JZVideoPlayerManager.setFirstFloor(this);

        onEvent(JZUserAction.ON_ENTER_FULLSCREEN);
        setScreenMode(ScreenMode.PORTRAIT);
        JZVideoPlayer player = startWindowFullscreen(true);
        cancelMute();
        checkWifi(player);
    }

    private void tryPlaying(JZVideoPlayer player) {
        if (player == null) {
            return;
        }

        switch (mPlayStatus) {
            case NORMAL:
            case COMPLETE:
                player.restartVideo();
                break;
            case PAUSE:
                player.resume();
            case PLAYING:
            case ERROR:
                break;
        }
    }

    protected void checkWifi(JZVideoPlayer player) {
        int autoPlayOption = SPUtil.getInt(mContext, PlayerConst.AUTO_PLAY_OPTION, AutoPlayOption.ONLY_WIFI.option);
        AutoPlayOption option = AutoPlayOption.getOption(autoPlayOption);
        int status = NetworkUtil.getConnectivityStatus(mContext);

        switch (option) {
            case ONLY_WIFI:
            case OFF:
                if (status == NetworkUtil.MOBILE_DATA) {
                    if (currentState == CURRENT_STATE_PLAYING || currentState == CURRENT_STATE_PREPARING) {
                        pause();
                    }
                    showNonWifiPlayDialog(player);
                    reportNonWifiDialogEvent(REPORT_TYPE_DIALOG);
                } else {
                    tryPlaying(player);
                }
                break;
            case MOBILE_WIFI:
                tryPlaying(player);
                break;
        }
    }

    protected void showNonWifiPlayDialog(final JZVideoPlayer player) {
//        mMobilePlayDialog = DialogUtil.createDialog(mContext,
//                "使用移动网络播放",
//                String.format("播放将消耗 %sM 手机流量", mVideoSize),
//                "播放", "取消", new DialogUtil.DialogInterface() {
//                    @Override
//                    public void buttonClick(boolean positive) {
//                        if (positive) {
//                            tryPlaying(player);
//                            reportNonWifiDialogEvent(REPORT_TYPE_DIALOG_POSITIVE);
//                        } else {
//                            backPress();
//                            reportNonWifiDialogEvent(REPORT_TYPE_DIALOG_NEGATIVE);
//                        }
//                    }
//                });
    }

    private static final int REPORT_TYPE_DIALOG = 1;
    private static final int REPORT_TYPE_DIALOG_POSITIVE = 2;
    private static final int REPORT_TYPE_DIALOG_NEGATIVE = 3;
    private static final String PAGE_VIDEO_PLAY = "app_p_video";

    private void reportNonWifiDialogEvent(int type) {
        switch (type) {
            case REPORT_TYPE_DIALOG:
                break;
            case REPORT_TYPE_DIALOG_POSITIVE:
                break;
            case REPORT_TYPE_DIALOG_NEGATIVE:
                break;
        }
    }

    private void reportPlayAllOpenCourseEvent() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.jz_layout_standard_new;
    }

    private void notifyPlayStateChange() {
//        if (mListener != null) {
//            mListener.stateChange(mPlayStatus);
//        }
    }

//    private PlayStateChangeListener mListener;
//    public void setPlayStateChangeListener(PlayStateChangeListener listener) {
//        mListener = listener;
//    }

//    public interface PlayStateChangeListener {
//        void stateChange(PlayStatus status);
//    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
        changeUiToNormal();

        if (mScreenMode == ScreenMode.LIST) {
            setVideoDuration(mVideoDuration);
        }
    }

    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
        changeUiToPreparing();
    }

    @Override
    public void onStatePreparingChangingUrl(int urlMapIndex, long seekToInAdvance) {
        super.onStatePreparingChangingUrl(urlMapIndex, seekToInAdvance);
        loadingProgressBar.setVisibility(VISIBLE);
        startButton.setVisibility(INVISIBLE);
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
        changeUiToPlayingClear();
        if (mScreenMode != ScreenMode.LIST) {
            updateStartImage();
            startDismissControlViewTimer();
            setFullscreenImageByVideoType();
        }

        showCoverImage(false);

        if (mPlayStatus == PlayStatus.PAUSE && mMobilePlayDialog != null && mMobilePlayDialog.isShowing()) {
            pause();
            return;
        }
        mPlayStatus = PlayStatus.PLAYING;
        notifyPlayStateChange();
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
        changeUiToPauseShow();
        if (mScreenMode == ScreenMode.LIST) {
            showOperationViewByScreenMode();
        } else {
            cancelDismissControlViewTimer();
        }

        if (mScreenMode == ScreenMode.LIST) {
            setVideoDuration(mVideoDuration);
        }
        mPlayStatus = PlayStatus.PAUSE;
        notifyPlayStateChange();
    }

    @Override
    public void onStateError() {
        super.onStateError();
        changeUiToError();

        if (mScreenMode == ScreenMode.LIST) {
            setVideoDuration(mVideoDuration);
            showCoverImage(true);
        } else {
            showCoverImage(false);
        }
        mPlayStatus = PlayStatus.ERROR;
        notifyPlayStateChange();
    }

    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        changeUiToComplete();
        if (mScreenMode == ScreenMode.LIST) {
            showOperationViewByScreenMode();
        } else {
            cancelDismissControlViewTimer();
        }
        if (mScreenMode == ScreenMode.LIST) {
            setVideoDuration(mVideoDuration);
            showCoverImage(true);
        } else {
            showCoverImage(false);
        }
        mPlayStatus = PlayStatus.COMPLETE;
        notifyPlayStateChange();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mScreenMode == ScreenMode.LIST) {
            return super.onTouch(v, event);
        }
        int id = v.getId();
        if (id == R.id.surface_container) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    if (currentState != CURRENT_STATE_PREPARING) {
                        startDismissControlViewTimer();
                        if (!mChangePosition && !mChangeVolume) {
                            onEvent(JZUserActionStandard.ON_CLICK_BLANK);
                            onClickUiToggle();
                        }
                    }
                    break;
            }
        } else if (id == R.id.bottom_seek_progress) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cancelDismissControlViewTimer();
                    break;
                case MotionEvent.ACTION_UP:
                    startDismissControlViewTimer();
                    break;
            }
        }
        return super.onTouch(v, event);
    }

    public void clickStart(Object tag) {
        startButton.setTag(tag);
        startButton.performClick();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (getScreenMode() != ScreenMode.LIST && currentState == CURRENT_STATE_PREPARING) {
            setAllControlsVisibility(View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
            startDismissControlViewTimer();
            if (i == R.id.back){
                backPress();
                mute();
                showOperationViewByScreenMode();
            }
            return;
        }

        if (i == R.id.thumb) {
            if (dataSourceObjects == null) {
                Toast.makeText(mContext, getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
                return;
            }
            Object obj = JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex);
            if (obj == null) {
                Toast.makeText(mContext, getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
                return;
            }

            /*
            1. 如果是列表点击行为，跳入全屏界面
            2. 如果全屏时点击行为，正常播控逻辑
             */
            if (mScreenMode != ScreenMode.LIST) {
                if (currentState == CURRENT_STATE_NORMAL) {
                    onEvent(JZUserActionStandard.ON_CLICK_START_THUMB);
                    startVideo();
                } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
                    onClickUiToggle();
                }
            } else {
                fullScreenPlayAndCheckingWifi();
            }
        } else if (i == R.id.surface_container || i == R.id.cover) {
            if (getScreenMode() == ScreenMode.LIST) {
                fullScreenPlayAndCheckingWifi();
            } else {
                startDismissControlViewTimer();
            }
        } else if (i == R.id.jumpToOpenCourseButton) {
            pause();
            mute();
            backPress();
            if (!TextUtils.isEmpty(mOpenCourseUrl)) {
            }
        } else if (i == R.id.back) {
            backPress();
            mute();
        }

        showOperationViewByScreenMode();
    }

    @Override
    public void showOperationViewByScreenMode() {
        super.showOperationViewByScreenMode();

        switch (mScreenMode) {
            case LIST:
                backButton.setVisibility(GONE);
                break;
            case PORTRAIT:
                backButton.setVisibility(VISIBLE);
                break;
            case LANDSCAPE:
                backButton.setVisibility(VISIBLE);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        super.onStartTrackingTouch(seekBar);
        cancelDismissControlViewTimer();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        super.onStopTrackingTouch(seekBar);
        if (currentState == CURRENT_STATE_PLAYING) {
            dismissControlView();
        } else {
            startDismissControlViewTimer();
        }
    }

    public void onClickUiToggle() {
        if (currentState == CURRENT_STATE_PREPARING) {
            changeUiToPreparing();
        } else if (currentState == CURRENT_STATE_PLAYING) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPlayingClear();
            } else {
                changeUiToPlayingShow();
            }
        } else if (currentState == CURRENT_STATE_PAUSE) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPauseClear();
            } else {
                changeUiToPauseShow();
            }
        }
    }

    public void onCLickUiToggleToClear() {
        if (currentState == CURRENT_STATE_PREPARING) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPreparing();
            }
        } else if (currentState == CURRENT_STATE_PLAYING) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPlayingClear();
            }
        } else if (currentState == CURRENT_STATE_PAUSE) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPauseClear();
            }
        } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToComplete();
            }
        }
    }

    @Override
    public void setProgressAndText(int progress, long position, long duration) {
        super.setProgressAndText(progress, position, duration);
        if (mScreenMode == ScreenMode.LIST) {
            if (duration > 0 && duration > position) {
                mTimesLeftCount.setText(JZUtils.stringForTime(duration - position));
            } else {
                mTimesLeftCount.setText(JZUtils.stringForTime(duration));
            }
            mTimesLeftCount.setVisibility(VISIBLE);
        } else {
            mTimesLeftCount.setVisibility(GONE);
        }
    }

    @Override
    public void setBufferProgress(int bufferProgress) {
        super.setBufferProgress(bufferProgress);
    }

    @Override
    public void resetProgressAndTime() {
        super.resetProgressAndTime();
    }

    public void changeUiToNormal() {
        switch (currentScreen) {
            case SCREEN_WINDOW_NORMAL:
            case SCREEN_WINDOW_LIST:
                setAllControlsVisibility(View.INVISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.VISIBLE);
                updateStartImage();
                break;
            case SCREEN_WINDOW_FULLSCREEN:
                setAllControlsVisibility(View.VISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.VISIBLE);
                updateStartImage();
                break;
        }
    }

    public void changeUiToPreparing() {
        switch (currentScreen) {
            case SCREEN_WINDOW_NORMAL:
            case SCREEN_WINDOW_LIST:
                setAllControlsVisibility(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.VISIBLE, View.VISIBLE);
                updateStartImage();
                break;
            case SCREEN_WINDOW_FULLSCREEN:
                setAllControlsVisibility(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.VISIBLE, View.VISIBLE);
                updateStartImage();
                break;
        }

    }

    public void changeUiToPlayingShow() {
        switch (currentScreen) {
            case SCREEN_WINDOW_NORMAL:
            case SCREEN_WINDOW_LIST:
                setAllControlsVisibility(View.INVISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_WINDOW_FULLSCREEN:
                setAllControlsVisibility(View.VISIBLE, View.VISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
        }

    }

    public void changeUiToPlayingClear() {
        switch (currentScreen) {
            case SCREEN_WINDOW_NORMAL:
            case SCREEN_WINDOW_LIST:
                setAllControlsVisibility(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE);
                break;
            case SCREEN_WINDOW_FULLSCREEN:
                setAllControlsVisibility(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE);
                break;
        }

    }

    public void changeUiToPauseShow() {
        switch (currentScreen) {
            case SCREEN_WINDOW_NORMAL:
            case SCREEN_WINDOW_LIST:
                setAllControlsVisibility(View.INVISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_WINDOW_FULLSCREEN:
                setAllControlsVisibility(View.VISIBLE, View.VISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
        }
    }

    public void changeUiToPauseClear() {
        switch (currentScreen) {
            case SCREEN_WINDOW_NORMAL:
            case SCREEN_WINDOW_LIST:
                setAllControlsVisibility(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE);
                break;
            case SCREEN_WINDOW_FULLSCREEN:
                setAllControlsVisibility(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE);
                break;
        }

    }

    public void changeUiToComplete() {
        switch (currentScreen) {
            case SCREEN_WINDOW_NORMAL:
            case SCREEN_WINDOW_LIST:
                setAllControlsVisibility(View.INVISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.VISIBLE);
                updateStartImage();
                break;
            case SCREEN_WINDOW_FULLSCREEN:
                setAllControlsVisibility(View.VISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.VISIBLE);
                updateStartImage();
                break;
        }

    }

    public void changeUiToError() {
        switch (currentScreen) {
            case SCREEN_WINDOW_NORMAL:
            case SCREEN_WINDOW_LIST:
                setAllControlsVisibility(View.INVISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_WINDOW_FULLSCREEN:
                setAllControlsVisibility(View.VISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
        }

    }

    public void setAllControlsVisibility(int topCon, int bottomCon, int startBtn, int loadingPro,
                                         int thumbImg) {
        backButton.setVisibility(topCon);
        bottomContainer.setVisibility(bottomCon);
        startButton.setVisibility(startBtn);
        loadingProgressBar.setVisibility(loadingPro);
        thumbImageView.setVisibility(thumbImg);
    }

    public void updateStartImage() {
        if (currentState == CURRENT_STATE_PLAYING) {
            startButton.setVisibility(VISIBLE);
            startButton.setImageResource(R.drawable.video_pause);
            smallStart.setImageResource(R.drawable.video_pause_mini);
        } else if (currentState == CURRENT_STATE_ERROR) {
            startButton.setVisibility(INVISIBLE);
        } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
            startButton.setVisibility(VISIBLE);
            startButton.setImageResource(R.drawable.video_play);
            smallStart.setImageResource(R.drawable.play_video);
        } else {
            startButton.setImageResource(R.drawable.video_play);
            smallStart.setImageResource(R.drawable.play_video);
        }
    }

    @Override
    public void showProgressDialog(float deltaX, String seekTime, long seekTimePosition, String totalTime, long totalTimeDuration) {
        super.showProgressDialog(deltaX, seekTime, seekTimePosition, totalTime, totalTimeDuration);
        if (mProgressDialog == null) {
            View localView = LayoutInflater.from(mContext).inflate(R.layout.jz_dialog_progress, null);
            mDialogProgressBar = localView.findViewById(R.id.duration_progressbar);
            mDialogSeekTime = localView.findViewById(R.id.tv_current);
            mDialogTotalTime = localView.findViewById(R.id.tv_duration);
            mDialogIcon = localView.findViewById(R.id.duration_image_tip);
            mProgressDialog = createDialogWithView(localView);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

        mDialogSeekTime.setText(seekTime);
        mDialogTotalTime.setText(String.format(" / %s", totalTime));
        mDialogProgressBar.setProgress(totalTimeDuration <= 0 ? 0 : (int) (seekTimePosition * 100 / totalTimeDuration));
        if (deltaX > 0) {
            mDialogIcon.setBackgroundResource(R.drawable.jz_forward_icon);
        } else {
            mDialogIcon.setBackgroundResource(R.drawable.jz_backward_icon);
        }
        onCLickUiToggleToClear();
    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showVolumeDialog(float deltaY, int volumePercent) {
        super.showVolumeDialog(deltaY, volumePercent);
        if (mVolumeDialog == null) {
            View localView = LayoutInflater.from(mContext).inflate(R.layout.jz_dialog_volume, null);
            mDialogVolumeImageView = localView.findViewById(R.id.volume_image_tip);
            mDialogVolumeTextView = localView.findViewById(R.id.tv_volume);
            mDialogVolumeProgressBar = localView.findViewById(R.id.volume_progressbar);
            mVolumeDialog = createDialogWithView(localView);
        }
        if (!mVolumeDialog.isShowing()) {
            mVolumeDialog.show();
        }
        if (volumePercent <= 0) {
            mDialogVolumeImageView.setBackgroundResource(R.drawable.jz_close_volume);
        } else {
            mDialogVolumeImageView.setBackgroundResource(R.drawable.jz_add_volume);
        }
        if (volumePercent > 100) {
            volumePercent = 100;
        } else if (volumePercent < 0) {
            volumePercent = 0;
        }
        mDialogVolumeTextView.setText(String.format("%s", volumePercent));
        mDialogVolumeProgressBar.setProgress(volumePercent);
        onCLickUiToggleToClear();
    }

    @Override
    public void dismissVolumeDialog() {
        super.dismissVolumeDialog();
        if (mVolumeDialog != null) {
            mVolumeDialog.dismiss();
        }
    }

    public Dialog createDialogWithView(View localView) {
        Dialog dialog = new Dialog(mContext, R.style.jz_style_dialog_progress);
        dialog.setContentView(localView);
        Window window = dialog.getWindow();
        if (window != null) {
            window.addFlags(Window.FEATURE_ACTION_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            window.setLayout(-2, -2);
            WindowManager.LayoutParams localLayoutParams = window.getAttributes();
            localLayoutParams.gravity = Gravity.CENTER;
            window.setAttributes(localLayoutParams);
        }
        return dialog;
    }

    public void startDismissControlViewTimer() {
        cancelDismissControlViewTimer();
        DISMISS_CONTROL_VIEW_TIMER = new Timer();
        mDismissControlViewTimerTask = new DismissControlViewTimerTask();
        DISMISS_CONTROL_VIEW_TIMER.schedule(mDismissControlViewTimerTask, 1200);
    }

    public void cancelDismissControlViewTimer() {
        if (DISMISS_CONTROL_VIEW_TIMER != null) {
            DISMISS_CONTROL_VIEW_TIMER.cancel();
        }
        if (mDismissControlViewTimerTask != null) {
            mDismissControlViewTimerTask.cancel();
        }

    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        if (mScreenMode == ScreenMode.LIST) {
            cancelDismissControlViewTimer();
        }

        JZVideoPlayer player = JZVideoPlayerManager.getFirstFloor();
        if (player != null) {
            showOperationViewByScreenMode();
        }
    }

    @Override
    public void onCompletion() {
        super.onCompletion();
        if (mScreenMode == ScreenMode.LIST) {
            cancelDismissControlViewTimer();
        } else {
            showOperationViewByScreenMode();
        }
    }

    public void dismissControlView() {
        if (currentState != CURRENT_STATE_NORMAL
                && currentState != CURRENT_STATE_ERROR
                && currentState != CURRENT_STATE_AUTO_COMPLETE) {
            post(new Runnable() {
                @Override
                public void run() {
                    bottomContainer.setVisibility(View.INVISIBLE);
                    backButton.setVisibility(View.INVISIBLE);
                    startButton.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    public class DismissControlViewTimerTask extends TimerTask {

        @Override
        public void run() {
            dismissControlView();
        }
    }
}
