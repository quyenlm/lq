package com.tencent.ieg.ntv.ctrl.player;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventGetCDNFile;
import com.tencent.ieg.ntv.event.EventGetCDNFileFinish;
import com.tencent.ieg.ntv.event.EventHideSystemUI;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.view.PlayerView;

public class PopupPlayerActivity extends AppCompatActivity {
    private static final String TAG = PopupPlayerActivity.class.getSimpleName();
    /* access modifiers changed from: private */
    public EventHideSystemUI eventHideSystemUI;
    /* access modifiers changed from: private */
    public String mBackgroundImageUrl;
    private IEventListener mBackgroundUpdater = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            EventGetCDNFileFinish evt = (EventGetCDNFileFinish) event;
            if (PopupPlayerActivity.this.mBackgroundImageUrl.equals(evt.url)) {
                if (evt.data != null) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(evt.data, 0, evt.data.length);
                        if (bitmap != null) {
                            ((ImageView) PopupPlayerActivity.this.findViewById(R.id.background_image)).setImageBitmap(bitmap);
                        }
                    } catch (Exception e) {
                        PopupPlayerActivity.log("mBackgroundUpdater - exception :" + e.toString());
                    }
                }
                EventManager.getInstance().unregister(eventId, this);
            }
        }
    };
    private final Handler mHideHandler = new Handler() {
        public void handleMessage(Message msg) {
            EventManager.getInstance().post(NTVDefine.EVT_HIDE_NAVIGATION_UI, PopupPlayerActivity.this.eventHideSystemUI);
        }
    };
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.button_close || id == R.id.button_close_rt) {
                PopupPlayerActivity.this.finish();
                TVShowManager.getInstance().onCloseView("2");
            } else if (id == R.id.button_jump) {
                PopupPlayerActivity.this.finish();
                TVShowManager.getInstance().onCloseView("3");
            }
        }
    };
    private PlayerController mPlayerController;

    /* access modifiers changed from: private */
    public static void log(String msg) {
        Logger.d(TAG, msg);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ntvs_activity_popup_new);
        log("onCreate");
        this.mBackgroundImageUrl = getIntent().getStringExtra("bgimgurl");
        if (this.mBackgroundImageUrl != null) {
            EventManager.getInstance().register(3002, this.mBackgroundUpdater);
            EventManager.getInstance().post(3001, new EventGetCDNFile(this.mBackgroundImageUrl));
        }
        getWindow().setFlags(1024, 1024);
        getWindow().setFlags(128, 128);
        PlayerView playerView = (PlayerView) findViewById(R.id.player_view);
        playerView.setPopUpMode(true);
        playerView.setFullScreen(true);
        playerView.setCtrlBarVisible(false);
        this.mPlayerController = PlayerController.getInstance();
        this.mPlayerController.init(this, playerView);
        this.mPlayerController.disableImmersive();
        this.mPlayerController.disableVideoViewClick();
        findViewById(R.id.button_close_rt).setOnClickListener(this.mOnClickListener);
        Button btnClose = (Button) findViewById(R.id.button_close);
        btnClose.setOnClickListener(this.mOnClickListener);
        btnClose.setText(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_POPUP_CLOSE_BTN_TEXT));
        Button btnJump = (Button) findViewById(R.id.button_jump);
        btnJump.setOnClickListener(this.mOnClickListener);
        btnJump.setText(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_POPUP_JUMP_BTN_TEXT));
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        PlayerController playerController = this.mPlayerController;
        PlayerController.destroyInstance();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mPlayerController.resume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.mPlayerController.pause();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            this.eventHideSystemUI = new EventHideSystemUI();
            this.eventHideSystemUI.hasFocus = hasFocus;
            delayedHide(1000);
        }
    }

    private void delayedHide(int delayMillis) {
        this.mHideHandler.removeMessages(0);
        this.mHideHandler.sendEmptyMessageDelayed(0, (long) delayMillis);
    }
}
