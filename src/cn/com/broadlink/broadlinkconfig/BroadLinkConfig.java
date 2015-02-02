// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package cn.com.broadlink.broadlinkconfig;

import android.app.Activity;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import com.airapp.utils.Logger;

public class BroadLinkConfig
{
    private static BroadLinkConfigResultListener mBroadLinkConfigResultListener;
    private static BroadLinkAPScanResultListener mbroadliApScanResultListener;
    private String mBroadcastString;
    private Activity mConfigActivity;
    private String mGatewayString;
    private Context mconContext;

    static
    {
        System.loadLibrary("BroadLinkConfig");
    }

    private BroadLinkConfig()
    {
    }

    public BroadLinkConfig(Context context)
    {
        mconContext = context;
        DhcpInfo dhcpinfo = ((WifiManager)context.getSystemService("wifi")).getDhcpInfo();
        mGatewayString = Formatter.formatIpAddress(dhcpinfo.gateway);
        mBroadcastString = Formatter.formatIpAddress(dhcpinfo.ipAddress | -1 ^ dhcpinfo.netmask);
        Log.d("BroadLinkConfig--BroadLink", (new StringBuilder("broadcast: ")).append(mBroadcastString).toString());
        mConfigActivity = (Activity)mconContext;
    }

    private native int bl_advanced_smartconfig_v2(byte abyte0[], byte abyte1[], String s, int i, String s1, String s2, String s3, 
            int j);

    private native int bl_ap_config(byte abyte0[], byte abyte1[], int i, int j, String s, String s1, String s2, 
            String s3, String s4, int k, int l);

    private native int bl_smartconfig(byte abyte0[], byte abyte1[], int i, String s, String s1, String s2, String s3, 
            String s4, String s5, int j);

    private native int bl_smartconfig_v2(byte abyte0[], byte abyte1[], String s, int i);

    private native APScanResult get_nearby_ap_list(int i, int j);

    private native void smartconfig_cancel();

    private native int ti_smartconfig(byte abyte0[], byte abyte1[], String s, int i);

    public void BroadLinkAPConfig(final byte ssid[], final byte password[], final int security_type, final int isDHCP, final String static_ip, final String static_mask, final String static_gateway, 
            final String static_dns1, final String static_dns2, final int timeout, final int repeat)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int ret = bl_ap_config(ssid, password, security_type, isDHCP, static_ip, static_mask, static_gateway, static_dns1, static_dns2, timeout, repeat);
                BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (BroadLinkConfig.mBroadLinkConfigResultListener != null)
                            BroadLinkConfig.mBroadLinkConfigResultListener.configResultCallBack(ret);
                    }
                });
            }
        }).start();
    }

    public void BroadLinkAdvancedSmartconfigV2(final byte ssid[], final byte password[], final String ip, final int mask, final String gateway, final String dns, final String dst, final int timeout)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WifiManager.MulticastLock localMulticastLock = ((WifiManager)BroadLinkConfig.this.mconContext.getSystemService("wifi")).createMulticastLock("BroadLinkMulticastLock");
                localMulticastLock.acquire();
                final int ret = BroadLinkConfig.this.bl_advanced_smartconfig_v2(ssid, password, ip, mask, gateway, dns, dst, timeout);
                localMulticastLock.release();
                BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((ret >= 0) && (ret <= 1) && (BroadLinkConfig.mBroadLinkConfigResultListener != null))
                            BroadLinkConfig.mBroadLinkConfigResultListener.configResultCallBack(ret);
                    }
                });
            }
        }).start();
    }

    public void BroadLinkSmartConfig(byte abyte0[], byte abyte1[])
    {
        BroadLinkSmartConfig(abyte0, abyte1, 60);
    }

    public void BroadLinkSmartConfig(byte abyte0[], byte abyte1[], int i)
    {
        BroadLinkSmartConfig(abyte0, abyte1, 0, null, null, null, null, null, i);
    }

    public void BroadLinkSmartConfig(final byte ssid[], final byte password[], final int channel, final String staticMask, final String staticIP, final String staticGW, final String staticDNS1, 
            final String staticDNS2, final int timeout)
    {
        new Thread(new Runnable() {
            public void run (){
                WifiManager.MulticastLock localMulticastLock = ((WifiManager)BroadLinkConfig.this.mconContext.getSystemService("wifi")).createMulticastLock("BroadLinkMulticastLock");
                localMulticastLock.acquire();
                final int ret = BroadLinkConfig.this.bl_smartconfig(ssid, password, channel, staticMask, staticIP, staticGW, staticDNS1, staticDNS2, BroadLinkConfig.this.mGatewayString, timeout);
                localMulticastLock.release();
                BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((ret >= 0) && (ret <= 1) && (BroadLinkConfig.mBroadLinkConfigResultListener != null))
                            BroadLinkConfig.mBroadLinkConfigResultListener.configResultCallBack(ret);
                    }
                });
            }
        }).start();
    }

    public void BroadLinkSmartconfigV2(final byte ssid[], final byte password[], final int timeout)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                    Log.d("BroadLinkConfig--BroadLink", "BroadLinkSmartconfigV2 run!");
                    WifiManager.MulticastLock localMulticastLock = ((WifiManager) BroadLinkConfig.this.mconContext.getSystemService("wifi")).createMulticastLock("BroadLinkMulticastLock");
                    localMulticastLock.acquire();
                    Log.d("BroadLinkConfig--BroadLink", "gateway:" + BroadLinkConfig.this.mGatewayString);
                    final int ret = BroadLinkConfig.this.bl_smartconfig_v2(ssid, password, BroadLinkConfig.this.mGatewayString, timeout);
                    Log.d("BroadLinkConfig--BroadLink", "ret = " + ret);
                    localMulticastLock.release();
                    BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            if ((ret >= 0) && (ret <= 1) && (BroadLinkConfig.mBroadLinkConfigResultListener != null)) {
                                BroadLinkConfig.mBroadLinkConfigResultListener.configResultCallBack(ret);
                                Log.d("BroadLinkConfig--BroadLink", "Callback");
                            }
                        }
                    });
            }
        }).start();
    }

    public void CC3000SmartConfig(byte abyte0[], byte abyte1[])
    {
        CC3000SmartConfig(abyte0, abyte1, 60);
    }

    public void CC3000SmartConfig(final byte ssid[], final byte password[], final int timeout)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WifiManager.MulticastLock localMulticastLock = ((WifiManager)BroadLinkConfig.this.mconContext.getSystemService("wifi")).createMulticastLock("BroadLinkMulticastLock");
                localMulticastLock.acquire();
                final int ret = BroadLinkConfig.this.ti_smartconfig(ssid, password, BroadLinkConfig.this.mGatewayString, timeout);
                localMulticastLock.release();
                BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        if ((ret >= 0) && (ret <= 1) && (BroadLinkConfig.mBroadLinkConfigResultListener != null))
                            BroadLinkConfig.mBroadLinkConfigResultListener.configResultCallBack(ret);
                    }
                });
            }
        }).start();
    }

    public void SmartConfigCancel()
    {
        smartconfig_cancel();
    }

    public void getNearbyAPList(final int timeout, final int repeat)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final APScanResult localAPScanResult = BroadLinkConfig.this.get_nearby_ap_list(timeout, repeat);
                BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        if (BroadLinkConfig.mbroadliApScanResultListener != null)
                            BroadLinkConfig.mbroadliApScanResultListener.apScanResultCallback(localAPScanResult);
                    }
                });
            }
        }).start();
    }

    public void setAPScanResultListener(BroadLinkAPScanResultListener broadlinkapscanresultlistener)
    {
        mbroadliApScanResultListener = broadlinkapscanresultlistener;
    }

    public void setSmartConfgiCallbackListener(BroadLinkConfigResultListener broadlinkconfigresultlistener)
    {
        mBroadLinkConfigResultListener = broadlinkconfigresultlistener;
    }



}
