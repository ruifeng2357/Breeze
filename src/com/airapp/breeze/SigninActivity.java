package com.airapp.breeze;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import cn.com.broadlink.broadlinkconfig.BroadLinkConfig;
import com.airapp.utils.Common;
import com.airapp.view.WheelProgress;

public class SigninActivity extends BaseActivity implements View.OnClickListener, WheelProgress.OnCompleteListener {
    public final int WIFI_SEARCHTIME = 60000;

    private boolean isConfigure;
    private static long back_pressed;

    private WheelProgress wheelProgress;
    private Button buttonConfigure;
    private ImageView imagePulse;

    private BroadLinkConfig mBroadLinkConfig;
    private Animation mAnimation;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mBroadLinkConfig = new BroadLinkConfig(this);

        initControl();
    }

    @Override
    protected int getParentLayoutID() {
        return R.id.parent_layout;
    }

    private void initControl()
    {
        isConfigure = false;

        mAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse);

        wheelProgress = (WheelProgress) findViewById(R.id.viewWheel);
        wheelProgress.setOnCompleteListener(this);

        buttonConfigure = (Button) findViewById(R.id.buttonConfigure);
        buttonConfigure.setOnClickListener(this);

        imagePulse = (ImageView) findViewById(R.id.imagePulse);

        return;
    }

    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Common.showToast(SigninActivity.this, getString(R.string.exitapp));
            back_pressed = System.currentTimeMillis();
        }
    }

    private void eventConfigure() {
        if (isConfigure) {
            isConfigure = false;
            buttonConfigure.setText("Configure");
            imagePulse.setVisibility(View.INVISIBLE);
            imagePulse.clearAnimation();
            wheelProgress.setVisibility(View.INVISIBLE);
            wheelProgress.stopDraw();
        }
        else {
            isConfigure = true;
            buttonConfigure.setText("Cancel");
            mAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse);
            imagePulse.setVisibility(View.VISIBLE);
            imagePulse.startAnimation(mAnimation);
            wheelProgress.setVisibility(View.VISIBLE);
            wheelProgress.setPeriod(WIFI_SEARCHTIME);
            wheelProgress.setRepeatMode(WheelProgress.MODE_FILL);
            wheelProgress.startDraw();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonConfigure) {
            eventConfigure();
        }
    }

    @Override
    public void onComplete() {
        eventConfigure();
    }
}
