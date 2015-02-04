package com.airapp.breeze;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.com.broadlink.blnetworkunit.BLNetworkUnit;
import cn.com.broadlink.blnetworkunit.ScanDevice;
import cn.com.broadlink.blnetworkunit.ScanDeviceListener;
import cn.com.broadlink.networkapi.NetworkAPI;
import com.airapp.breeze.udp.LoginUnit;
import com.airapp.dataclass.ManageDevice;
import com.airapp.model.SQLiteDBHelper;
import com.airapp.utils.Common;
import com.airapp.utils.Logger;

public class WellComeActivity extends BaseActivity {

    private SQLiteDBHelper m_db = null;

    public Handler handler= new Handler() {
        public void handleMessage(Message msg){
            if (BreezeApplication.allDeviceList == null || BreezeApplication.allDeviceList.size() == 0) {
                startActivity(new Intent(WellComeActivity.this, SigninActivity.class));
                WellComeActivity.this.finish();
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);

                return;
            }

            ManageDevice manageDevice = new ManageDevice();
            LoginUnit localLoginUnit = new LoginUnit(WellComeActivity.this);
            manageDevice = BreezeApplication.allDeviceList.get(0);

            if (manageDevice != null) {
                localLoginUnit.a1Login(manageDevice, new LoginUnit.LoginCallBack() {
                    public void success(ManageDevice paramManageDevice) {
                        if (m_db.getRows() == 0) {
                            Intent intent = new Intent(WellComeActivity.this, AddRoomActivity.class);
                            startActivity(intent);
                            WellComeActivity.this.finish();
                            overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                        }
                        else
                        {
                            Intent intent = new Intent(WellComeActivity.this, RoomActivity.class);
                            startActivity(intent);
                            WellComeActivity.this.finish();
                            overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                        }
                    }
                });
            }
            else {
                startActivity(new Intent(WellComeActivity.this, SigninActivity.class));
                WellComeActivity.this.finish();
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
            }
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        m_db = new SQLiteDBHelper(WellComeActivity.this);

        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected int getParentLayoutID() {
        return R.id.parent_layout;
    }
}
