package com.airapp.breeze;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.com.broadlink.broadlinkconfig.BroadLinkConfig;
import cn.com.broadlink.broadlinkconfig.BroadLinkConfigResultListener;
import com.airapp.breeze.udp.LoginUnit;
import com.airapp.dataclass.ManageDevice;
import com.airapp.model.SQLiteDBHelper;
import com.airapp.utils.Common;
import com.airapp.utils.SharedPref;
import com.airapp.view.WheelProgress;

public class ConfigureActivity extends BaseActivity implements View.OnClickListener, WheelProgress.OnCompleteListener {
    public final int WIFI_SEARCHTIME = 60000;

    private boolean isConfigure;
    private static long back_pressed;

    private WheelProgress wheelProgress;
    private Button buttonConfigure;
    private ImageView imagePulse;
    private EditText editSSID;
    private EditText editPassword;

    private SQLiteDBHelper m_db;
    private Animation mAnimation;
    private BroadLinkConfig mBroadLinkConfig;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        m_db = new SQLiteDBHelper(this);
        mBroadLinkConfig = new BroadLinkConfig(this);

        initControl();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        if (mBroadLinkConfig != null) {
            mBroadLinkConfig.SmartConfigCancel();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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

        editSSID = (EditText) findViewById(R.id.editSSID);
        editSSID.setText(Common.getWifiSSID(this));
        editPassword = (EditText) findViewById(R.id.editPassword);
        editPassword.setText(SharedPref.getWifiInfo(this, Common.getWifiSSID(this)));

        return;
    }

    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Common.showToast(this, getString(R.string.exitapp));
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
            if (isConfigure == false) {
                String szSSID = editSSID.getText().toString();
                if (szSSID.length() == 0) {
                    Common.showToast(this, getString(R.string.err_inputssid));
                    return;
                }

                String szPassword = editPassword.getText().toString();
                if (szPassword.length() == 0) {
                    Common.showToast(this, getString(R.string.err_inputpassword));
                    return;
                }
            }
            if (Common.isWifiConnect(this)) {
                eventConfigure();
                startSmartConfig();
            }
            else {
                Common.showToast(this, getString(R.string.err_wificonnect));
            }
        }
    }

    private void startSmartConfig() {
        Common.closeInputMethod(this);
        newDeviceConfigWiFi();
    }

    private void newDeviceConfigWiFi() {
        byte []arrSSID = editSSID.getText().toString().getBytes();
        byte []arrPassword = editPassword.getText().toString().getBytes();

        SharedPref.setWifiInfo(this, editSSID.getText().toString(), editPassword.getText().toString());

        mBroadLinkConfig.BroadLinkSmartconfigV2(arrSSID, arrPassword, 60);
        mBroadLinkConfig.setSmartConfgiCallbackListener(new BroadLinkConfigResultListener() {
            @Override
            public void configResultCallBack(int retVal) {
                switch (retVal)
                {
                    case 0:
                        scanNewDevice();
                        break;
                    case 1:
                        //eventConfigure();
                        break;
                    default:
                        return;
                }
            }
        });
    }

    private void scanNewDevice() {
        ManageDevice manageDevice = new ManageDevice();
        LoginUnit localLoginUnit = new LoginUnit(this);
        manageDevice = BreezeApplication.allDeviceList.get(0);
        if (manageDevice != null) {
            localLoginUnit.a1Login(manageDevice, new LoginUnit.LoginCallBack() {
                public void success(ManageDevice paramManageDevice) {
                    eventConfigure();
                    if (m_db.getRows() == 0) {
                        Intent intent = new Intent(ConfigureActivity.this, AddRoomActivity.class);
                        startActivity(intent);
                        ConfigureActivity.this.finish();
                    }
                    else
                    {
                        Intent intent = new Intent(ConfigureActivity.this, RoomActivity.class);
                        startActivity(intent);
                        ConfigureActivity.this.finish();
                    }
                }
            });
        }
    }

    @Override
    public void onComplete() {
        eventConfigure();
        Dialog dialog = showAlert();
        dialog.show();
    }

    public Dialog showAlert() {
        final Dialog dialog = new Dialog(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_wificonfigfailure, null);

        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(view);

        Button btnOk = (Button) view.findViewById(R.id.buttonOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }
}
