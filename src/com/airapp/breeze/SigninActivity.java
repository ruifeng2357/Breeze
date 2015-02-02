package com.airapp.breeze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import cn.com.broadlink.broadlinkconfig.BroadLinkConfig;
import com.airapp.breeze.udp.LoginUnit;
import com.airapp.dataclass.ManageDevice;
import com.airapp.utils.Common;
import com.airapp.utils.SQLiteDBHelper;

public class SigninActivity extends BaseActivity {
    private static long back_pressed;

    private BroadLinkConfig mBroadLinkConfig;

    SQLiteDBHelper m_db = null;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.buttonSignin:
                    if (BreezeApplication.allDeviceList == null || BreezeApplication.allDeviceList.size() == 0)
                        return;
                    ManageDevice manageDevice = new ManageDevice();
                    LoginUnit localLoginUnit = new LoginUnit(SigninActivity.this);
                    manageDevice = BreezeApplication.allDeviceList.get(0);

                    if ((manageDevice != null) && (manageDevice.getDeviceType() == 10004)) {
                        localLoginUnit.a1Login(manageDevice, new LoginUnit.LoginCallBack() {
                            public void success(ManageDevice paramManageDevice) {
                                Intent intent = new Intent(SigninActivity.this, RoomActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                    /*
                    int nRows = m_db.getRows();

                    if (nRows == 0) {
                        Intent intent = new Intent(SigninActivity.this, AddRoomActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(SigninActivity.this, RoomActivity.class);
                        startActivity(intent);
                    }
                    */
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        m_db = new SQLiteDBHelper(SigninActivity.this);
        mBroadLinkConfig = new BroadLinkConfig(this);

        initControl();
    }

    @Override
    protected int getParentLayoutID() {
        return R.id.parent_layout;
    }

    private void initControl()
    {
        Button buttonSignin = (Button) findViewById(R.id.buttonSignin);
        buttonSignin.setOnClickListener(onClickListener);

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
}
