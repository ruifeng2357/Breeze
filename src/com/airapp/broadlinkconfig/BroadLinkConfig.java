package com.airapp.broadlinkconfig;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

@SuppressLint({"NewApi"})
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

  public BroadLinkConfig(Context paramContext)
  {
    this.mconContext = paramContext;
    DhcpInfo localDhcpInfo = ((WifiManager)paramContext.getSystemService("wifi")).getDhcpInfo();
    this.mGatewayString = Formatter.formatIpAddress(localDhcpInfo.gateway);
    this.mBroadcastString = Formatter.formatIpAddress(localDhcpInfo.ipAddress | 0xFFFFFFFF ^ localDhcpInfo.netmask);
    Log.d("BroadLinkConfig--BroadLink", "broadcast: " + this.mBroadcastString);
    this.mConfigActivity = ((Activity)this.mconContext);
  }

  private native int bl_advanced_smartconfig_v2(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, int paramInt2);

  private native int bl_ap_config(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt3, int paramInt4);

  private native int bl_smartconfig(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt2);

  private native int bl_smartconfig_v2(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString, int paramInt);

  private native APScanResult get_nearby_ap_list(int paramInt1, int paramInt2);

  private native void smartconfig_cancel();

  private native int ti_smartconfig(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString, int paramInt);

  public void BroadLinkAPConfig(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt3, int paramInt4)
  {
    new Thread(new Runnable(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramInt2, paramString1, paramString2, paramString3, paramString4, paramString5, paramInt3, paramInt4)
    {
      public void run()
      {
        int i = BroadLinkConfig.this.bl_ap_config(this.val$ssid, this.val$password, this.val$security_type, this.val$isDHCP, this.val$static_ip, this.val$static_mask, this.val$static_gateway, this.val$static_dns1, this.val$static_dns2, this.val$timeout, this.val$repeat);
        BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable(i)
        {
          public void run()
          {
            if (BroadLinkConfig.mBroadLinkConfigResultListener != null)
              BroadLinkConfig.mBroadLinkConfigResultListener.configResultCallBack(this.val$ret);
          }
        });
      }
    }).start();
  }

  public void BroadLinkAdvancedSmartconfigV2(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, int paramInt2)
  {
    new Thread(new Runnable(paramArrayOfByte1, paramArrayOfByte2, paramString1, paramInt1, paramString2, paramString3, paramString4, paramInt2)
    {
      public void run()
      {
        WifiManager.MulticastLock localMulticastLock = ((WifiManager)BroadLinkConfig.this.mconContext.getSystemService("wifi")).createMulticastLock("BroadLinkMulticastLock");
        localMulticastLock.acquire();
        int i = BroadLinkConfig.this.bl_advanced_smartconfig_v2(this.val$ssid, this.val$password, this.val$ip, this.val$mask, this.val$gateway, this.val$dns, this.val$dst, this.val$timeout);
        localMulticastLock.release();
        BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable(i)
        {
          public void run()
          {
            if ((this.val$ret >= 0) && (this.val$ret <= 1) && (BroadLinkConfig.mBroadLinkConfigResultListener != null))
              BroadLinkConfig.mBroadLinkConfigResultListener.configResultCallBack(this.val$ret);
          }
        });
      }
    }).start();
  }

  public void BroadLinkSmartConfig(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    BroadLinkSmartConfig(paramArrayOfByte1, paramArrayOfByte2, 60);
  }

  public void BroadLinkSmartConfig(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    BroadLinkSmartConfig(paramArrayOfByte1, paramArrayOfByte2, 0, null, null, null, null, null, paramInt);
  }

  public void BroadLinkSmartConfig(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt2)
  {
    new Thread(new Runnable(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramString1, paramString2, paramString3, paramString4, paramString5, paramInt2)
    {
      public void run()
      {
        WifiManager.MulticastLock localMulticastLock = ((WifiManager)BroadLinkConfig.this.mconContext.getSystemService("wifi")).createMulticastLock("BroadLinkMulticastLock");
        localMulticastLock.acquire();
        int i = BroadLinkConfig.this.bl_smartconfig(this.val$ssid, this.val$password, this.val$channel, this.val$staticMask, this.val$staticIP, this.val$staticGW, this.val$staticDNS1, this.val$staticDNS2, BroadLinkConfig.this.mGatewayString, this.val$timeout);
        localMulticastLock.release();
        BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable(i)
        {
          public void run()
          {
            if ((this.val$ret >= 0) && (this.val$ret <= 1) && (BroadLinkConfig.mBroadLinkConfigResultListener != null))
              BroadLinkConfig.mBroadLinkConfigResultListener.configResultCallBack(this.val$ret);
          }
        });
      }
    }).start();
  }

  public void BroadLinkSmartconfigV2(byte[] arrSSID, byte[] arrPassword, int timeOut)
  {
    final byte[] bytesSSID = arrSSID;
    final byte[] bytesPassword = arrPassword;
    final int nTimeOut = timeOut;
    new Thread(new Runnable(bytesSSID, arrPassword, timeOut)
    {
      public void run()
      {
        Log.d("BroadLinkConfig--BroadLink", "BroadLinkSmartconfigV2 run!");
        WifiManager.MulticastLock localMulticastLock = ((WifiManager)BroadLinkConfig.this.mconContext.getSystemService("wifi")).createMulticastLock("BroadLinkMulticastLock");
        localMulticastLock.acquire();
        Log.d("BroadLinkConfig--BroadLink", "gateway:" + BroadLinkConfig.this.mGatewayString);
        int i = BroadLinkConfig.this.bl_smartconfig_v2(bytesSSID, bytesPassword, BroadLinkConfig.this.mGatewayString, nTimeOut);
        Log.d("BroadLinkConfig--BroadLink", "ret = " + i);
        localMulticastLock.release();
        BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable(i)
        {
          public void run()
          {
            if ((this.val$ret >= 0) && (this.val$ret <= 1) && (BroadLinkConfig.mBroadLinkConfigResultListener != null))
            {
              BroadLinkConfig.mBroadLinkConfigResultListener.configResultCallBack(this.val$ret);
              Log.d("BroadLinkConfig--BroadLink", "Callback");
            }
          }
        });
      }
    }).start();
  }

  public void CC3000SmartConfig(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    CC3000SmartConfig(paramArrayOfByte1, paramArrayOfByte2, 60);
  }

  public void CC3000SmartConfig(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    new Thread(new Runnable(paramArrayOfByte1, paramArrayOfByte2, paramInt)
    {
      public void run()
      {
        WifiManager.MulticastLock localMulticastLock = ((WifiManager)BroadLinkConfig.this.mconContext.getSystemService("wifi")).createMulticastLock("BroadLinkMulticastLock");
        localMulticastLock.acquire();
        int i = BroadLinkConfig.this.ti_smartconfig(this.val$ssid, this.val$password, BroadLinkConfig.this.mGatewayString, this.val$timeout);
        localMulticastLock.release();
        BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable(i)
        {
          public void run()
          {
            if ((this.val$ret >= 0) && (this.val$ret <= 1) && (BroadLinkConfig.mBroadLinkConfigResultListener != null))
              BroadLinkConfig.mBroadLinkConfigResultListener.configResultCallBack(this.val$ret);
          }
        });
      }
    }).start();
  }

  public void SmartConfigCancel()
  {
    smartconfig_cancel();
  }

  public void getNearbyAPList(int paramInt1, int paramInt2)
  {
    new Thread(new Runnable(paramInt1, paramInt2)
    {
      public void run()
      {
        APScanResult localAPScanResult = BroadLinkConfig.this.get_nearby_ap_list(this.val$timeout, this.val$repeat);
        BroadLinkConfig.this.mConfigActivity.runOnUiThread(new Runnable(localAPScanResult)
        {
          public void run()
          {
            if (BroadLinkConfig.mbroadliApScanResultListener != null)
              BroadLinkConfig.mbroadliApScanResultListener.apScanResultCallback(this.val$result);
          }
        });
      }
    }).start();
  }

  public void setAPScanResultListener(BroadLinkAPScanResultListener paramBroadLinkAPScanResultListener)
  {
    mbroadliApScanResultListener = paramBroadLinkAPScanResultListener;
  }

  public void setSmartConfgiCallbackListener(BroadLinkConfigResultListener paramBroadLinkConfigResultListener)
  {
    mBroadLinkConfigResultListener = paramBroadLinkConfigResultListener;
  }
}