package com.lynpo.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lynpo.video.util.SPUtil;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nathen on 16/7/30.
 */
public abstract class JZVideoPlayer extends FrameLayout implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {

    public static final String TAG = "JiaoZiVideoPlayer";
    public static final int THRESHOLD = 80;
    public static final int FULL_SCREEN_NORMAL_DELAY = 300;

    public static final int SCREEN_WINDOW_NORMAL = 0;
    public static final int SCREEN_WINDOW_LIST = 1;
    public static final int SCREEN_WINDOW_FULLSCREEN = 2;

    public static final int CURRENT_STATE_NORMAL = 0;
    public static final int CURRENT_STATE_PREPARING = 1;
    public static final int CURRENT_STATE_PREPARING_CHANGING_URL = 2;
    public static final int CURRENT_STATE_PLAYING = 3;
    public static final int CURRENT_STATE_PAUSE = 5;
    public static final int CURRENT_STATE_AUTO_COMPLETE = 6;
    public static final int CURRENT_STATE_ERROR = 7;

    public static final String URL_KEY_DEFAULT = "URL_KEY_DEFAULT";//当播放的地址只有一个的时候的key
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT = 1;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP = 2;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL = 3;
    public static boolean ACTION_BAR_EXIST = true;
    public static boolean TOOL_BAR_EXIST = true;
    public static int FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
    public static int NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    public static int LANDSCAPE_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    public static boolean SAVE_PROGRESS = true;
    public static int VIDEO_IMAGE_DISPLAY_TYPE = 0;
    public static long CLICK_QUIT_FULLSCREEN_TIME = 0;
    public static AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {//是否新建个class，代码更规矩，并且变量的位置也很尴尬
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseAllVideos();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    try {
                        JZVideoPlayer player = JZVideoPlayerManager.getCurrentJzvd();
                        if (player != null && player.currentState == JZVideoPlayer.CURRENT_STATE_PLAYING) {
                            player.startButton.performClick();
                        }
                    } catch (IllegalStateException ignored) {
                        
                    }
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    break;
            }
        }
    };
    protected static JZUserAction JZ_USER_EVENT;
    protected static Timer UPDATE_PROGRESS_TIMER;
    public int currentState = -1;
    public int currentScreen = -1;
    public Object[] objects = null;
    public long seekToInAdvance = 0;
    public ImageView startButton;
    public SeekBar progressBar;
    public ImageView fullscreenButton;
    public ImageView smallStart;
    public TextView currentTimeTextView, totalTimeTextView;
    public ViewGroup textureViewContainer;
    public ViewGroup /*topContainer, */bottomContainer;

    public int widthRatio = 0;
    public int heightRatio = 0;
    public Object[] dataSourceObjects;//这个参数原封不动直接通过JZMeidaManager传给JZMediaInterface。
    public int currentUrlMapIndex = 0;
    public int positionInList = -1;
    public int videoRotation = 0;
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected AudioManager mAudioManager;
    protected static int mCachedVolume;
    protected ProgressTimerTask mProgressTimerTask;
    protected boolean mTouchingProgressBar;
    protected float mDownX;
    protected float mDownY;
    protected boolean mChangeVolume;
    protected boolean mChangePosition;
    protected long mGestureDownPosition;
    protected int mGestureDownVolume;
    protected float mGestureDownBrightness;
    protected long mSeekTimePosition;

    protected Context mContext;
    protected static ScreenMode mScreenMode = ScreenMode.LIST;
    private VideoType mVideoType;
    protected PlayStatus mPlayStatus = PlayStatus.NORMAL;
    protected String mVideoSize;
    protected String mOpenCourseId;
    protected String mOpenCourseUrl;
    private static boolean mEnableAutoPlay = true;

    public JZVideoPlayer(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public JZVideoPlayer(Context context, String openCourseUrl, ScreenMode mode) {
        super(context);
        mContext = context;
        mOpenCourseUrl = openCourseUrl;
        mScreenMode = mode;
        init(context);
    }

    public JZVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mScreenMode == ScreenMode.LIST) {
            return;
        }

        ScreenMode mode = ScreenMode.LIST;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mode = ScreenMode.LANDSCAPE;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            mode = ScreenMode.PORTRAIT;
        }

        setScreenMode(mode);
    }

    public static void releaseAllVideos() {
        if ((System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME) > FULL_SCREEN_NORMAL_DELAY) {
            JZVideoPlayerManager.completeAll();
            JZMediaManager.instance().positionInList = -1;
            JZMediaManager.instance().releaseMediaPlayer();
            setScreenMode(ScreenMode.LIST);
        }
    }

    public static void enableAutoPlay(boolean enable) {
        mEnableAutoPlay = enable;
    }

    public void setVideoSize(String size) {
        mVideoSize = size;
    }

    public void setOpenCourseUrl(String openCourseId, String openCourseUrl) {
        mOpenCourseId = openCourseId;
        mOpenCourseUrl = openCourseUrl;
    }

    public void setPlayStatus(PlayStatus playStatus) {
        mPlayStatus = playStatus;
    }

    public PlayStatus getPlayStatus() {
        return mPlayStatus;
    }

    public static void setScreenMode(ScreenMode mode) {
        mScreenMode = mode;
    }

    public static ScreenMode getScreenMode() {
        return mScreenMode;
    }

    public void pause() {
        if (currentState == CURRENT_STATE_PLAYING) {
            try {
                onEvent(JZUserAction.ON_CLICK_PAUSE);
                JZMediaManager.pause();
                onStatePause();
            } catch (IllegalStateException ignored) {
                setPlayStatus(PlayStatus.NORMAL);
                releaseAllVideos();
            }
        }
    }

    public void resume() {
        if (currentState == CURRENT_STATE_PAUSE) {
            try {
                onEvent(JZUserAction.ON_CLICK_RESUME);
                JZMediaManager.start();
                onStatePlaying();
            } catch (IllegalStateException e) {
                setPlayStatus(PlayStatus.NORMAL);
                releaseAllVideos();
            }
        }
    }

    public void mute() {
        int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        int volume = mCachedVolume;
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        if (volume <= 0) {
            volume = SPUtil.getInt(mContext, PlayerConst.VIDEO_VOLUME, max * 3 / 10);
        }
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        mCachedVolume = volume;
        SPUtil.putInt(mContext, PlayerConst.VIDEO_VOLUME, mCachedVolume);
    }

    public void cancelMute() {
        if (mCachedVolume <= 0) {
            int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            mCachedVolume = SPUtil.getInt(mContext, PlayerConst.VIDEO_VOLUME, max * 3 / 10);
        }
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCachedVolume, 0);
    }

    protected void setFullScreenImage(boolean full) {
        if (full) {
            fullscreenButton.setImageResource(R.drawable.full_screen);
        } else {
            fullscreenButton.setImageResource(R.drawable.half_screen);
        }
    }

    public static boolean backPress() {
        setScreenMode(ScreenMode.LIST);
        pauseCurrentByAutoPlay();

        if ((System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME) < FULL_SCREEN_NORMAL_DELAY)
            return false;

        if (JZVideoPlayerManager.getSecondFloor() != null && JZVideoPlayerManager.getFirstFloor() != null) {
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            if (JZUtils.dataSourceObjectsContainsUri(JZVideoPlayerManager.getFirstFloor().dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                JZVideoPlayer jzVideoPlayer = JZVideoPlayerManager.getSecondFloor();
                jzVideoPlayer.onEvent(jzVideoPlayer.currentScreen == JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN ?
                        JZUserAction.ON_QUIT_FULLSCREEN :
                        JZUserAction.ON_QUIT_TINYSCREEN);
                JZVideoPlayerManager.getFirstFloor().playOnThisJzvd();
            } else {
                quitFullscreenOrTinyWindow();
            }

            return true;
        } else if (JZVideoPlayerManager.getFirstFloor() != null &&
                (JZVideoPlayerManager.getFirstFloor().currentScreen == SCREEN_WINDOW_FULLSCREEN)) {//以前我总想把这两个判断写到一起，这分明是两个独立是逻辑
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            quitFullscreenOrTinyWindow();

            return true;
        }
        return false;
    }

    private static void pauseCurrentByAutoPlay() {
        if (mEnableAutoPlay) {
            return;
        }
        JZVideoPlayer player = JZVideoPlayerManager.getCurrentJzvd();
        if (player != null) {
            if (player.currentState == CURRENT_STATE_PREPARING) {
                releaseAllVideos();
            } else {
                player.pause();
            }
        }
    }

    public static void quitFullscreenOrTinyWindow() {
        //直接退出全屏和小窗
        JZVideoPlayerManager.getFirstFloor().clearFloatScreen();
        JZMediaManager.instance().releaseMediaPlayer();
        JZVideoPlayerManager.completeAll();
    }

    @SuppressLint("RestrictedApi")
    public static void showSupportActionBar(Context context) {
        if (ACTION_BAR_EXIST && JZUtils.getAppCompActivity(context) != null) {
            ActionBar ab = JZUtils.getAppCompActivity(context).getSupportActionBar();
            if (ab != null) {
                ab.setShowHideAnimationEnabled(false);
                ab.show();
            }
        }
        if (TOOL_BAR_EXIST) {
            JZUtils.getWindow(context).clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @SuppressLint("RestrictedApi")
    public static void hideSupportActionBar(Context context) {
        if (ACTION_BAR_EXIST && JZUtils.getAppCompActivity(context) != null) {
            ActionBar ab = JZUtils.getAppCompActivity(context).getSupportActionBar();
            if (ab != null) {
                ab.setShowHideAnimationEnabled(false);
                ab.hide();
            }
        }
        if (TOOL_BAR_EXIST) {
            JZUtils.getWindow(context).setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public abstract int getLayoutId();

    protected View mVideoView;

    public void init(Context context) {
        mVideoView = View.inflate(context, getLayoutId(), this);

        textureViewContainer = mVideoView.findViewById(R.id.surface_container);
        initOperationView(mVideoView);

        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        try {
            if (isCurrentPlay()) {
                NORMAL_ORIENTATION = ((AppCompatActivity) context).getRequestedOrientation();
            }
        } catch (Exception ignored) {

        }
    }

    private void initOperationView(View mOperationView) {
//        mOperationView = LayoutInflater.from(mContext).inflate(R.layout.layout_player_operation, null);
        startButton = mOperationView.findViewById(R.id.start);
        fullscreenButton = mOperationView.findViewById(R.id.fullscreen);
        smallStart = mOperationView.findViewById(R.id.smallStart);
        progressBar = mOperationView.findViewById(R.id.bottom_seek_progress);
        currentTimeTextView = mOperationView.findViewById(R.id.current);
        totalTimeTextView = mOperationView.findViewById(R.id.total);
        bottomContainer = mOperationView.findViewById(R.id.layout_bottom);

        startButton.setOnClickListener(this);
        smallStart.setOnClickListener(this);
        fullscreenButton.setOnClickListener(this);
        progressBar.setOnSeekBarChangeListener(this);
        bottomContainer.setOnClickListener(this);
        textureViewContainer.setOnClickListener(this);
        textureViewContainer.setOnTouchListener(this);
    }

    public void dispatchOpenCourseButtonShowOrHide() {}

    public void showOperationViewByScreenMode() {
        switch (mScreenMode) {
            case LIST:
                if (mPlayStatus == PlayStatus.PAUSE || mPlayStatus == PlayStatus.COMPLETE) {
                    startButton.setVisibility(VISIBLE);
                } else {
                    startButton.setVisibility(GONE);
                }
                bottomContainer.setVisibility(GONE);
                break;
            case PORTRAIT:
            case LANDSCAPE:
                startButton.setVisibility(VISIBLE);
                bottomContainer.setVisibility(VISIBLE);
                break;
        }
    }

    public void setUp(String url, int screen, Object... objects) {
        LinkedHashMap map = new LinkedHashMap();
        map.put(URL_KEY_DEFAULT, url);
        Object[] dataSourceObjects = new Object[1];
        dataSourceObjects[0] = map;
        setUp(dataSourceObjects, 0, screen, objects);
    }

    public void setUp(Object[] dataSourceObjects, int defaultUrlMapIndex, int screen, Object... objects) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (this.dataSourceObjects != null && JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex) != null &&
                    JZUtils.getCurrentFromDataSource(this.dataSourceObjects, currentUrlMapIndex) != null &&
                    Objects.equals(JZUtils.getCurrentFromDataSource(this.dataSourceObjects, currentUrlMapIndex),
                            JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex))) {
                return;
            }
        } else {
            if (this.dataSourceObjects != null && JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex) != null &&
                    JZUtils.getCurrentFromDataSource(this.dataSourceObjects, currentUrlMapIndex) != null &&
                    JZUtils.getCurrentFromDataSource(this.dataSourceObjects, currentUrlMapIndex)
                            .equals(JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex))) {
                return;
            }
        }

        if (isCurrentJZVD() && JZUtils.dataSourceObjectsContainsUri(dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
            long position = 0;
            try {
                position = JZMediaManager.getCurrentPosition();
            } catch (IllegalStateException ignored) {

            }
            if (position != 0) {
                JZUtils.saveProgress(mContext, JZMediaManager.getCurrentDataSource(), position);
            }
            JZMediaManager.instance().releaseMediaPlayer();
        }

        this.dataSourceObjects = dataSourceObjects;
        this.currentUrlMapIndex = defaultUrlMapIndex;
        this.currentScreen = screen;
        this.objects = objects;
        onStateNormal();

    }

    public void fullScreenPlayAndCheckingWifi() {}

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (getScreenMode() != ScreenMode.LIST && currentState == CURRENT_STATE_PREPARING) {
            return;
        }

        if (i == R.id.start || i == R.id.smallStart) {
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
            判断是否是自动播放触发：
            1. 如果是自动播放，直接调用播放开始
            2. 如果是列表点击行为，跳入全屏界面
            3. 如果全屏时点击行为，正常播控逻辑
             */
            Object tag = startButton.getTag();
            if ((tag != null)) {
                startButton.setTag(null);
                if (tag == PlayerConst.TAG_SOFT_CLICK_PLAY) {
                    if (currentState == CURRENT_STATE_NORMAL) {
                        startVideo();
                        onEvent(JZUserAction.ON_CLICK_START_ICON);
                    } else if (currentState == CURRENT_STATE_PAUSE) {
                        resume();
                    }
                } else {
                    pause();
                }
            } else if (mScreenMode != ScreenMode.LIST) {
                if (currentState == CURRENT_STATE_NORMAL) {
                    startVideo();
                    onEvent(JZUserAction.ON_CLICK_START_ICON);
                } else if (currentState == CURRENT_STATE_PLAYING) {
                    pause();
                } else if (currentState == CURRENT_STATE_PAUSE) {
                    resume();
                } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
                    onEvent(JZUserAction.ON_CLICK_START_AUTO_COMPLETE);
                    startVideo();
                }
            } else {
                fullScreenPlayAndCheckingWifi();
            }
        } else if (i == R.id.fullscreen) {
            if (currentState == CURRENT_STATE_AUTO_COMPLETE) return;
            if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                if (mVideoType == VideoType.SLIM) {
                    //quit fullscreen
                    backPress();
                    setFullScreenImage(false);
                    mute();
                } else {
                    if (mScreenMode == ScreenMode.PORTRAIT) {
                        setScreenMode(ScreenMode.LANDSCAPE);
                    } else {
                        setScreenMode(ScreenMode.PORTRAIT);
                    }
                    forceFullscreen(mScreenMode);
                }
            } else {
                if (mScreenMode != ScreenMode.LIST) {
                    onEvent(JZUserAction.ON_ENTER_FULLSCREEN);
                    setScreenMode(ScreenMode.PORTRAIT);
                    startWindowFullscreen(true);
                } else {
                    fullScreenPlayAndCheckingWifi();
                }
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int id = v.getId();
        if (id == R.id.surface_container || id == R.id.cover) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mTouchingProgressBar = true;

                    mDownX = x;
                    mDownY = y;
                    mChangeVolume = false;
                    mChangePosition = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float deltaX = x - mDownX;
                    float deltaY = y - mDownY;
                    float absDeltaX = Math.abs(deltaX);
                    float absDeltaY = Math.abs(deltaY);
                    if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                        if (!mChangePosition && !mChangeVolume/* && !mChangeBrightness*/) {
                            if (absDeltaX > THRESHOLD || absDeltaY > THRESHOLD) {
                                cancelProgressTimer();
                                if (absDeltaX >= THRESHOLD) {
                                    // 全屏模式下的CURRENT_STATE_ERROR状态下,不响应进度拖动事件.
                                    // 否则会因为mediaplayer的状态非法导致App Crash
                                    if (currentState != CURRENT_STATE_ERROR) {
                                        mChangePosition = true;
                                        mGestureDownPosition = getCurrentPositionWhenPlaying();
                                    }
                                } else {
                                    //如果y轴滑动距离超过设置的处理范围，那么进行滑动事件处理
                                    if (mDownX < mScreenWidth * 0.5f) {//左侧改变亮度
//                                        mChangeBrightness = true;
                                        WindowManager.LayoutParams lp = JZUtils.getWindow(mContext).getAttributes();
                                        if (lp.screenBrightness < 0) {
                                            try {
                                                mGestureDownBrightness = Settings.System.getInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                                            } catch (Settings.SettingNotFoundException ignored) {
                                                
                                            }
                                        } else {
                                            mGestureDownBrightness = lp.screenBrightness * 255;
                                        }
                                    } else {//右侧改变声音
                                        mChangeVolume = true;
                                        mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                                    }
                                }
                            }
                        }
                    }
                    if (mChangePosition) {
                        long totalTimeDuration = getDuration();
                        mSeekTimePosition = (int) (mGestureDownPosition + deltaX * totalTimeDuration / mScreenWidth);
                        if (mSeekTimePosition > totalTimeDuration)
                            mSeekTimePosition = totalTimeDuration;
                        String seekTime = JZUtils.stringForTime(mSeekTimePosition);
                        String totalTime = JZUtils.stringForTime(totalTimeDuration);

                        showProgressDialog(deltaX, seekTime, mSeekTimePosition, totalTime, totalTimeDuration);
                    }
                    if (mChangeVolume) {
                        deltaY = -deltaY;
                        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        int deltaV = (int) (max * deltaY * 3 / mScreenHeight);
                        int newVolume = mGestureDownVolume + deltaV;
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
                        mCachedVolume = newVolume;
                        //dialog中显示百分比
                        int volumePercent = (int) (mGestureDownVolume * 100 / max + deltaY * 3 * 100 / mScreenHeight);
                        showVolumeDialog(-deltaY, volumePercent);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mTouchingProgressBar = false;
                    dismissProgressDialog();
                    dismissVolumeDialog();
                    if (mChangePosition) {
                        onEvent(JZUserAction.ON_TOUCH_SCREEN_SEEK_POSITION);
                        JZMediaManager.seekTo(mSeekTimePosition);
                        long duration = getDuration();
                        int progress = (int) (mSeekTimePosition * 100 / (duration == 0 ? 1 : duration));
                        progressBar.setProgress(progress);
                    }
                    if (mChangeVolume) {
                        onEvent(JZUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME);
                    }
                    startProgressTimer();
                    break;
            }
        }
        return false;
    }

    public void startVideo() {
        JZVideoPlayerManager.completeAll();

        initTextureView();
        addTextureView();
        AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager != null) {
            mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }
        JZUtils.scanForActivity(mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        JZMediaManager.setDataSource(dataSourceObjects);
        JZMediaManager.setCurrentDataSource(JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex));
        JZMediaManager.instance().positionInList = positionInList;
        onStatePreparing();
        JZVideoPlayerManager.setFirstFloor(this);
    }

    public void restartVideo() {
        initTextureView();
        addTextureView();
        AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager != null) {
            mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }
        JZUtils.scanForActivity(mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        JZMediaManager.setDataSource(dataSourceObjects);
        JZMediaManager.setCurrentDataSource(JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex));
        onStatePreparing();
    }

    public void onPrepared() {
        onStatePrepared();
        onStatePlaying();
    }

    public void setState(int state) {
        setState(state, 0, 0);
    }

    public void setState(int state, int urlMapIndex, int seekToInAdvance) {
        switch (state) {
            case CURRENT_STATE_NORMAL:
                onStateNormal();
                break;
            case CURRENT_STATE_PREPARING:
                onStatePreparing();
                break;
            case CURRENT_STATE_PREPARING_CHANGING_URL:
                onStatePreparingChangingUrl(urlMapIndex, seekToInAdvance);
                break;
            case CURRENT_STATE_PLAYING:
                onStatePlaying();
                break;
            case CURRENT_STATE_PAUSE:
                onStatePause();
                break;
            case CURRENT_STATE_ERROR:
                onStateError();
                break;
            case CURRENT_STATE_AUTO_COMPLETE:
                onStateAutoComplete();
                break;
        }
    }

    public void onStateNormal() {
        currentState = CURRENT_STATE_NORMAL;
        cancelProgressTimer();
    }

    public void onStatePreparing() {
        currentState = CURRENT_STATE_PREPARING;
        resetProgressAndTime();
    }

    public void onStatePreparingChangingUrl(int urlMapIndex, long seekToInAdvance) {
        currentState = CURRENT_STATE_PREPARING_CHANGING_URL;
        this.currentUrlMapIndex = urlMapIndex;
        this.seekToInAdvance = seekToInAdvance;
        JZMediaManager.setDataSource(dataSourceObjects);
        JZMediaManager.setCurrentDataSource(JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex));
        JZMediaManager.instance().prepare();
    }

    public void onStatePrepared() {//因为这个紧接着就会进入播放状态，所以不设置state
        if (seekToInAdvance != 0) {
            JZMediaManager.seekTo(seekToInAdvance);
            seekToInAdvance = 0;
        } else {
            long position = JZUtils.getSavedProgress(mContext, JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex));
            if (position != 0) {
                JZMediaManager.seekTo(position);
            }
        }
    }

    public void onStatePlaying() {
        currentState = CURRENT_STATE_PLAYING;
        startProgressTimer();
    }

    public void onStatePause() {
        currentState = CURRENT_STATE_PAUSE;
        startProgressTimer();
    }

    public void onStateError() {
        currentState = CURRENT_STATE_ERROR;
        cancelProgressTimer();
    }

    public void onStateAutoComplete() {
        currentState = CURRENT_STATE_AUTO_COMPLETE;
        cancelProgressTimer();
        progressBar.setProgress(100);
        currentTimeTextView.setText(totalTimeTextView.getText());
    }

    public void onInfo(int what, int extra) {
    }

    public void onError(int what, int extra) {
        if (what != 38 && extra != -38 && what != -38 && extra != 38 && extra != -19) {
            onStateError();
            if (isCurrentPlay()) {
                JZMediaManager.instance().releaseMediaPlayer();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if (widthRatio != 0 && heightRatio != 0) {
            int specWidth = MeasureSpec.getSize(widthMeasureSpec);
            int specHeight = (int) ((specWidth * (float) heightRatio) / widthRatio);
            setMeasuredDimension(specWidth, specHeight);

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(specWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(specHeight, MeasureSpec.EXACTLY);
            getChildAt(0).measure(childWidthMeasureSpec, childHeightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    public void onAutoCompletion() {
        Runtime.getRuntime().gc();
        onEvent(JZUserAction.ON_AUTO_COMPLETE);
        dismissVolumeDialog();
        dismissProgressDialog();
        onStateAutoComplete();

        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            backPress();
        }
        JZMediaManager.instance().releaseMediaPlayer();
        JZUtils.saveProgress(mContext, JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex), 0);
    }

    public void onCompletion() {
        if (currentState == CURRENT_STATE_PLAYING || currentState == CURRENT_STATE_PAUSE) {
            long position = getCurrentPositionWhenPlaying();
            JZUtils.saveProgress(mContext, JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex), position);
        }
        cancelProgressTimer();
        dismissProgressDialog();
        dismissVolumeDialog();
        onStateNormal();
        textureViewContainer.removeView(JZMediaManager.textureView);
        JZMediaManager.instance().currentVideoWidth = 0;
        JZMediaManager.instance().currentVideoHeight = 0;

        AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager != null) {
            mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
        JZUtils.scanForActivity(mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        clearFullscreenLayout();
        JZUtils.setRequestedOrientation(mContext, NORMAL_ORIENTATION);

        if (JZMediaManager.surface != null) JZMediaManager.surface.release();
        if (JZMediaManager.savedSurfaceTexture != null)
            JZMediaManager.savedSurfaceTexture.release();
        JZMediaManager.textureView = null;
        JZMediaManager.savedSurfaceTexture = null;
    }

    public void initTextureView() {
        removeTextureView();
        JZMediaManager.textureView = new JZResizeTextureView(mContext);
        JZMediaManager.textureView.setSurfaceTextureListener(JZMediaManager.instance());
    }

    public void addTextureView() {
        LayoutParams layoutParams =
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER);

        if (JZMediaManager.textureView.getParent() != null) {
            ((ViewGroup) JZMediaManager.textureView.getParent()).removeView(JZMediaManager.textureView);
        }
        textureViewContainer.addView(JZMediaManager.textureView, layoutParams);
    }

    public void removeTextureView() {
        JZMediaManager.savedSurfaceTexture = null;
        if (JZMediaManager.textureView != null && JZMediaManager.textureView.getParent() != null) {
            ((ViewGroup) JZMediaManager.textureView.getParent()).removeView(JZMediaManager.textureView);
        }
    }

    public void clearFullscreenLayout() {
        ViewGroup vp = (JZUtils.scanForActivity(mContext))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View oldF = vp.findViewById(R.id.jz_fullscreen_id);
        if (oldF != null) {
            vp.removeView(oldF);
        }
        showSupportActionBar(mContext);
    }

    public void clearFloatScreen() {
        JZUtils.setRequestedOrientation(mContext, NORMAL_ORIENTATION);
        showSupportActionBar(mContext);
        ViewGroup vp = (JZUtils.scanForActivity(mContext))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        JZVideoPlayer fullJzvd = vp.findViewById(R.id.jz_fullscreen_id);

        if (fullJzvd != null) {
            vp.removeView(fullJzvd);
            if (fullJzvd.textureViewContainer != null)
                fullJzvd.textureViewContainer.removeView(JZMediaManager.textureView);
        }
        JZVideoPlayerManager.setSecondFloor(null);
    }

    public void onVideoSizeChanged() {
        if (JZMediaManager.textureView != null) {
            if (videoRotation != 0) {
                JZMediaManager.textureView.setRotation(videoRotation);
            }
            JZMediaManager.textureView.setVideoSize(JZMediaManager.instance().currentVideoWidth, JZMediaManager.instance().currentVideoHeight);
        }
    }

    public void onSurfaceTextureAvailable(int width, int height) {
        float videoWidthHeightRatio = (float)width / height;
        mVideoType = videoWidthHeightRatio >= 1 ? VideoType.WIDE : VideoType.SLIM;

        setFullscreenImageByVideoType();
    }

    public void setFullscreenImageByVideoType() {
        if (mScreenMode == ScreenMode.PORTRAIT) {
            if (mVideoType == VideoType.WIDE) {
                setFullScreenImage(false);
            } else {
                setFullScreenImage(true);
            }
        } else if (mScreenMode == ScreenMode.LANDSCAPE) {
            if (mVideoType == VideoType.WIDE) {
                setFullScreenImage(true);
            } else {
                setFullScreenImage(false);
            }
        } else {
            setFullScreenImage(false);
        }
    }

    public void startProgressTimer() {
        cancelProgressTimer();
        UPDATE_PROGRESS_TIMER = new Timer();
        mProgressTimerTask = new ProgressTimerTask();
        UPDATE_PROGRESS_TIMER.schedule(mProgressTimerTask, 0, 300);
    }

    public void cancelProgressTimer() {
        if (UPDATE_PROGRESS_TIMER != null) {
            UPDATE_PROGRESS_TIMER.cancel();
        }
        if (mProgressTimerTask != null) {
            mProgressTimerTask.cancel();
        }
    }

    public void setProgressAndText(int progress, long position, long duration) {
        if (!mTouchingProgressBar) {
            if (progress != 0) progressBar.setProgress(progress);
        }
        if (position != 0) currentTimeTextView.setText(JZUtils.stringForTime(position));
        totalTimeTextView.setText(JZUtils.stringForTime(duration));
    }

    public void setBufferProgress(int bufferProgress) {
        if (bufferProgress != 0) progressBar.setSecondaryProgress(bufferProgress);
    }

    public void resetProgressAndTime() {
        progressBar.setProgress(0);
        progressBar.setSecondaryProgress(0);
        currentTimeTextView.setText(JZUtils.stringForTime(0));
        totalTimeTextView.setText(JZUtils.stringForTime(0));
    }

    public long getCurrentPositionWhenPlaying() {
        long position = 0;
        if (currentState == CURRENT_STATE_PLAYING ||
                currentState == CURRENT_STATE_PAUSE) {
            try {
                position = JZMediaManager.getCurrentPosition();
            } catch (IllegalStateException e) {
                
                return position;
            }
        }
        return position;
    }

    public long getDuration() {
        long duration = 0;
        try {
            duration = JZMediaManager.getDuration();
        } catch (IllegalStateException e) {
            
            return duration;
        }
        return duration;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        cancelProgressTimer();
        ViewParent vpdown = getParent();
        while (vpdown != null) {
            vpdown.requestDisallowInterceptTouchEvent(true);
            vpdown = vpdown.getParent();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        onEvent(JZUserAction.ON_SEEK_POSITION);
        startProgressTimer();
        ViewParent vpup = getParent();
        while (vpup != null) {
            vpup.requestDisallowInterceptTouchEvent(false);
            vpup = vpup.getParent();
        }
        if (currentState != CURRENT_STATE_PLAYING &&
                currentState != CURRENT_STATE_PAUSE) return;
        long time = seekBar.getProgress() * getDuration() / 100;
        JZMediaManager.seekTo(time);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            //设置这个progres对应的时间，给textview
            long duration = getDuration();
            currentTimeTextView.setText(JZUtils.stringForTime(progress * duration / 100));
        }
    }

    public JZVideoPlayer startWindowFullscreen(boolean withSensor) {
        hideSupportActionBar(mContext);

        ViewGroup vp = (JZUtils.scanForActivity(mContext))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View old = vp.findViewById(R.id.jz_fullscreen_id);
        if (old != null) {
            vp.removeView(old);
        }
        textureViewContainer.removeView(JZMediaManager.textureView);
        try {
            JZVideoPlayer jzVideoPlayer = new JZVideoPlayerStandard(mContext, mOpenCourseUrl, mScreenMode);
            jzVideoPlayer.setId(R.id.jz_fullscreen_id);
            LayoutParams lp = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            vp.addView(jzVideoPlayer, lp);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                jzVideoPlayer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
            } else {
                jzVideoPlayer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
            jzVideoPlayer.setUp(dataSourceObjects, currentUrlMapIndex, JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, objects);
            jzVideoPlayer.setState(currentState);
            jzVideoPlayer.setVideoSize(mVideoSize);
            if (JZMediaManager.textureView == null) {
                jzVideoPlayer.initTextureView();
            }
            jzVideoPlayer.addTextureView();
            JZVideoPlayerManager.setSecondFloor(jzVideoPlayer);

            if (withSensor) {
                JZUtils.setRequestedOrientation(mContext, FULLSCREEN_ORIENTATION);
            }

            onStateNormal();
            jzVideoPlayer.progressBar.setSecondaryProgress(progressBar.getSecondaryProgress());
            jzVideoPlayer.startProgressTimer();
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            return jzVideoPlayer;
        } catch (Exception ignored) {
            return null;
        }
    }

    public boolean isCurrentPlay() {
        return isCurrentJZVD()
                && JZUtils.dataSourceObjectsContainsUri(dataSourceObjects, JZMediaManager.getCurrentDataSource());//不仅正在播放的url不能一样，并且各个清晰度也不能一样
    }

    public boolean isCurrentJZVD() {
        return JZVideoPlayerManager.getCurrentJzvd() != null
                && JZVideoPlayerManager.getCurrentJzvd() == this;
    }

    //退出全屏和小窗的方法
    public void playOnThisJzvd() {
        //1.清空全屏和小窗的jzvd
        currentState = JZVideoPlayerManager.getSecondFloor().currentState;
        currentUrlMapIndex = JZVideoPlayerManager.getSecondFloor().currentUrlMapIndex;
        clearFloatScreen();
        //2.在本jzvd上播放
        setState(currentState);
//        removeTextureView();
        addTextureView();
        dispatchOpenCourseButtonShowOrHide();
        showOperationViewByScreenMode();
    }

    public void forceFullscreen(ScreenMode screenMode) {
        if (screenMode == ScreenMode.PORTRAIT) {
            JZUtils.setRequestedOrientation(mContext, NORMAL_ORIENTATION);
        } else {
            JZUtils.setRequestedOrientation(mContext, LANDSCAPE_ORIENTATION);
        }
        onEvent(JZUserAction.ON_ENTER_FULLSCREEN);
        startWindowFullscreen(false);
    }

    public void onEvent(int type) {
        if (JZ_USER_EVENT != null && isCurrentPlay() && dataSourceObjects != null) {
            JZ_USER_EVENT.onEvent(type, JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex), currentScreen, objects);
        }
    }

    public void onSeekComplete() {

    }

    public void showProgressDialog(float deltaX,
                                   String seekTime, long seekTimePosition,
                                   String totalTime, long totalTimeDuration) {
    }

    public void dismissProgressDialog() {

    }

    public void showVolumeDialog(float deltaY, int volumePercent) {

    }

    public void dismissVolumeDialog() {

    }

    public class ProgressTimerTask extends TimerTask {
        @Override
        public void run() {
            if (currentState == CURRENT_STATE_PLAYING || currentState == CURRENT_STATE_PAUSE) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        long position = getCurrentPositionWhenPlaying();
                        long duration = getDuration();
                        int progress = (int) (position * 100 / (duration == 0 ? 1 : duration));
                        setProgressAndText(progress, position, duration);
                    }
                });
            }
        }
    }

}
