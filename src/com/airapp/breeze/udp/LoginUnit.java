package com.airapp.breeze.udp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import cn.com.broadlink.blnetworkdataparse.BLNetworkDataParse;
import cn.com.broadlink.blnetworkdataparse.BLeAir1Info;
import cn.com.broadlink.blnetworkunit.SendDataResultInfo;
import com.airapp.breeze.BreezeApplication;
import com.airapp.dataclass.ManageDevice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginUnit {
    private static ExecutorService FULL_TASK_EXECUTOR;
    private BLNetworkDataParse mBroadLinkNetworkData;
    private Context mContext;

    public static abstract interface LoginCallBack {
        public abstract void success(ManageDevice paramManageDevice);
    }

    public LoginUnit(Context paramContext) {
        this.mContext = paramContext;
        if (this.mBroadLinkNetworkData == null) {
            this.mBroadLinkNetworkData = BLNetworkDataParse.getInstance();
        }
        FULL_TASK_EXECUTOR = Executors.newCachedThreadPool();
    }

    public void a1Login(ManageDevice paramManageDevice, LoginCallBack paramLoginCallBack) {
        QueryA1StateTask localQueryA1StateTask = new QueryA1StateTask(paramLoginCallBack);
        if (Build.VERSION.SDK_INT >= 11)
            localQueryA1StateTask.executeOnExecutor(FULL_TASK_EXECUTOR, new ManageDevice[]{paramManageDevice});
        else
            localQueryA1StateTask.execute(new ManageDevice[]{paramManageDevice});
    }

    class QueryA1StateTask extends AsyncTask<ManageDevice, Void, SendDataResultInfo> {
        LoginUnit.LoginCallBack loginCallBack;
        ManageDevice manageDevice;

        public QueryA1StateTask(LoginUnit.LoginCallBack logincallback) {
            super();
            this.loginCallBack = logincallback;
        }

        protected SendDataResultInfo doInBackground(ManageDevice[] amanagedevice) {
            this.manageDevice = amanagedevice[0];
            if (this.manageDevice.isNews()) ;
            try {
                this.manageDevice.setNews(false);
                byte[] arrayOfByte = LoginUnit.this.mBroadLinkNetworkData.BLeAirRefreshBytes();
                SendDataResultInfo sendDataResultInfo = BreezeApplication.mBlNetworkUnit.sendData(this.manageDevice.getDeviceMac(), arrayOfByte, 2, 3, 2);
                if ((sendDataResultInfo != null) && (sendDataResultInfo.resultCode == -7) && (BreezeApplication.mBlNetworkUnit.getDeviceNetState(this.manageDevice.getDeviceMac()) != 1))
                    return sendDataResultInfo;
                else {
                    int i = 0;
                    while (i < 5)
                        try {
                            Thread.sleep(2000L);
                            SendDataResultInfo localSendDataResultInfo2 = BreezeApplication.mBlNetworkUnit.sendData(this.manageDevice.getDeviceMac(), arrayOfByte, 2, 3, 2);
                            if ((localSendDataResultInfo2 != null) && (localSendDataResultInfo2.resultCode != -7))
                                return localSendDataResultInfo2;
                        } catch (Exception ex) {
                        }

                    i++;
                }
            } catch (Exception localException) {
            }

            return null;
        }

        protected void onPostExecute(SendDataResultInfo paramSendDataResultInfo) {
            if (paramSendDataResultInfo != null) {
                if (paramSendDataResultInfo.resultCode == 0) {
                    BLeAir1Info localBLeAir1Info = LoginUnit.this.mBroadLinkNetworkData.BLeAirRefreshResultParse(paramSendDataResultInfo.data);
                    if (localBLeAir1Info != null)
                        this.manageDevice.setbLeAir1Info(localBLeAir1Info);
                    try {
                        String str = new String(localBLeAir1Info.deviceName, "utf-8");
                        if ((!str.equals(this.manageDevice.getDeviceName())) || (localBLeAir1Info.deviceLock != this.manageDevice.getDeviceLock()) || (this.manageDevice.isNews())) {
                            this.manageDevice.setNews(false);
                            this.manageDevice.setDeviceName(str);
                            this.manageDevice.setDeviceLock(localBLeAir1Info.deviceLock);
                        }
                        this.loginCallBack.success(this.manageDevice);
                        return;
                    } catch (Exception localException) {
                        while (true)
                            localException.printStackTrace();
                    }
                }
                return;
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}