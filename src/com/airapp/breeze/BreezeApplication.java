/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.airapp.breeze;

import android.app.*;
import android.util.Log;
import cn.com.broadlink.blnetworkunit.BLNetworkUnit;
import cn.com.broadlink.blnetworkunit.ScanDevice;
import cn.com.broadlink.blnetworkunit.ScanDeviceListener;
import cn.com.broadlink.networkapi.NetworkAPI;
import com.airapp.dataclass.ManageDevice;
import com.airapp.utils.Logger;
import com.airapp.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class BreezeApplication extends Application {
    public static BLNetworkUnit mBlNetworkUnit;
    public static NetworkAPI mNetworkAPI;
    public static List<ManageDevice> allDeviceList = new ArrayList();

	@Override
	public void onCreate()
    {
		super.onCreate();

        initBLNetwork();
	}

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }

    public void initBLNetwork()
    {
        if (mBlNetworkUnit == null)
            mBlNetworkUnit = BLNetworkUnit.getInstanceBlNetworkUnit(this);
        if (mNetworkAPI == null)
        {
            mNetworkAPI = NetworkAPI.getInstanceBlNetworkUnit(this);
            mNetworkAPI.SDKInit(null);
        }
        mBlNetworkUnit.setScanDeviceListener(new ScanDeviceListener()
        {
            public void deviceInfoCallBack(ScanDevice paramScanDevice)
            {
                if ((paramScanDevice != null) && (paramScanDevice.deviceName == null))
                    paramScanDevice.deviceName = "";
                if ((paramScanDevice != null) && ((paramScanDevice.deviceType == 10000) || (paramScanDevice.deviceType == 10002) || (paramScanDevice.deviceType == 0) || (paramScanDevice.deviceType == 10001) || (paramScanDevice.deviceType == 10004) || (paramScanDevice.deviceType == 10016) || (paramScanDevice.deviceType == 10024) || (paramScanDevice.deviceType == 10019) || (paramScanDevice.deviceType == 10017) || (paramScanDevice.deviceType == 10018) || (paramScanDevice.deviceType == 15) || (paramScanDevice.deviceType == 45) || (paramScanDevice.deviceType == 20045) || (paramScanDevice.deviceType == 10015) || (paramScanDevice.deviceType == 10014) || (paramScanDevice.deviceType == 10054) || (paramScanDevice.deviceType == 10009) || (paramScanDevice.deviceType == 10010) || (paramScanDevice.deviceType == 10011) || (paramScanDevice.deviceType == 10012) || (paramScanDevice.deviceType < 10000) || (paramScanDevice.deviceType == 10022) || (paramScanDevice.deviceType == 10023) || (paramScanDevice.deviceType == 10020) || (paramScanDevice.deviceType == 10021) || ((paramScanDevice.deviceType >= 10050) && (paramScanDevice.deviceType <= 10100)) || ((paramScanDevice.deviceType >= 10050) && (paramScanDevice.deviceType <= 10100)) || ((paramScanDevice.deviceType > 20000) && (paramScanDevice.deviceType < 30000))))
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

        allDeviceList.add(manageDevice);
        mBlNetworkUnit.addDevice(scanDevice);

        return;
    }
}
