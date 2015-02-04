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
        initBLNetwork();

        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected int getParentLayoutID() {
        return R.id.parent_layout;
    }

    public void initBLNetwork()
    {
        if (BreezeApplication.mBlNetworkUnit == null)
            BreezeApplication.mBlNetworkUnit = BLNetworkUnit.getInstanceBlNetworkUnit(this);
        if (BreezeApplication.mNetworkAPI == null)
        {
            BreezeApplication.mNetworkAPI = NetworkAPI.getInstanceBlNetworkUnit(this);
            BreezeApplication.mNetworkAPI.SDKInit(null);
        }
        BreezeApplication.mBlNetworkUnit.setScanDeviceListener(new ScanDeviceListener()
        {
            public void deviceInfoCallBack(ScanDevice paramScanDevice)
            {
                if ((paramScanDevice != null) && (paramScanDevice.deviceName == null))
                    paramScanDevice.deviceName = "";
                //if ((paramScanDevice != null) && ((paramScanDevice.deviceType == 10000) || (paramScanDevice.deviceType == 10002) || (paramScanDevice.deviceType == 0) || (paramScanDevice.deviceType == 10001) || (paramScanDevice.deviceType == 10004) || (paramScanDevice.deviceType == 10016) || (paramScanDevice.deviceType == 10024) || (paramScanDevice.deviceType == 10019) || (paramScanDevice.deviceType == 10017) || (paramScanDevice.deviceType == 10018) || (paramScanDevice.deviceType == 15) || (paramScanDevice.deviceType == 45) || (paramScanDevice.deviceType == 20045) || (paramScanDevice.deviceType == 10015) || (paramScanDevice.deviceType == 10014) || (paramScanDevice.deviceType == 10054) || (paramScanDevice.deviceType == 10009) || (paramScanDevice.deviceType == 10010) || (paramScanDevice.deviceType == 10011) || (paramScanDevice.deviceType == 10012) || (paramScanDevice.deviceType < 10000) || (paramScanDevice.deviceType == 10022) || (paramScanDevice.deviceType == 10023) || (paramScanDevice.deviceType == 10020) || (paramScanDevice.deviceType == 10021) || ((paramScanDevice.deviceType >= 10050) && (paramScanDevice.deviceType <= 10100)) || ((paramScanDevice.deviceType >= 10050) && (paramScanDevice.deviceType <= 10100)) || ((paramScanDevice.deviceType > 20000) && (paramScanDevice.deviceType < 30000))))
                if ((paramScanDevice != null) && (paramScanDevice.deviceType == 10004) )
                {
                    Logger.logError("scanDevice", paramScanDevice.deviceName);
                    checkCreateOrUpdateScanDevice(paramScanDevice);
                }
            }
        });
    }

    public void checkCreateOrUpdateScanDevice(ScanDevice scanDevice)
    {
        ManageDevice manageDevice = new ManageDevice();
        manageDevice.setDeviceLock(0);
        manageDevice.setDeviceMac(scanDevice.mac);
        manageDevice.setDeviceName(scanDevice.deviceName);
        manageDevice.setDevicePassword(scanDevice.password);
        manageDevice.setDeviceType(scanDevice.deviceType);
        manageDevice.setPublicKey(scanDevice.publicKey);

        BreezeApplication.allDeviceList.add(manageDevice);
        BreezeApplication.mBlNetworkUnit.addDevice(scanDevice);

        return;
    }
}
