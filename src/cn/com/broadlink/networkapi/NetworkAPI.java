package cn.com.broadlink.networkapi;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class NetworkAPI
{
  private static String imeiString;
  private static String nameString;
  private static NetworkAPI singleton;

  static
  {
    System.loadLibrary("NetworkAPI");
  }

  public static NetworkAPI getInstanceBlNetworkUnit(Object paramObject)
  {
    if (singleton != null)
      return singleton;
    try
    {
      if (singleton == null)
      {
        singleton = new NetworkAPI();
        imeiString = ((TelephonyManager)((Context)paramObject).getSystemService("phone")).getDeviceId();
        nameString = Build.MODEL;
      }
      return singleton;
    } catch (Exception ex) {}

    return null;
  }

  private native String networkapi_batch_netstate(String paramString, int paramInt);

  private native String networkapi_device_command(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  private native String networkapi_device_config(String paramString1, String paramString2, boolean paramBoolean);

  private native String networkapi_device_control(String paramString1, String paramString2, String paramString3);

  private native String networkapi_device_description(int paramInt);

  private native String networkapi_device_netstate(String paramString, int paramInt);

  private native String networkapi_device_pair(String paramString, int paramInt);

  private native String networkapi_device_probe(String paramString);

  private native String networkapi_device_server_time(String paramString, int paramInt);

  private native String networkapi_device_upgrade(String paramString1, String paramString2, int paramInt);

  private native String networkapi_device_version(String paramString, int paramInt);

  private native String networkapi_sdk_init(String paramString1, String paramString2, String paramString3);

  private native String networkapi_sdk_version();

  public String SDKInit(String paramString)
  {
    return networkapi_sdk_init(paramString, imeiString, nameString);
  }

  public String SDKVersion()
  {
    return networkapi_sdk_version();
  }

  public String batchNetState(String paramString, int paramInt)
  {
    return networkapi_batch_netstate(paramString, paramInt);
  }

  public String deviceCommand(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return networkapi_device_command(paramString1, paramString2, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public String deviceConfig(String paramString1, String paramString2, boolean paramBoolean)
  {
    return networkapi_device_config(paramString1, paramString2, paramBoolean);
  }

  public String deviceControl(String paramString1, String paramString2, String paramString3)
  {
    return networkapi_device_control(paramString1, paramString2, paramString3);
  }

  public String deviceDescription(int paramInt)
  {
    return networkapi_device_description(paramInt);
  }

  public String deviceFirmwareUpgrade(String paramString1, String paramString2, int paramInt)
  {
    return networkapi_device_upgrade(paramString1, paramString2, paramInt);
  }

  public String deviceFirmwareVersion(String paramString, int paramInt)
  {
    return networkapi_device_version(paramString, paramInt);
  }

  public String deviceNetState(String paramString, int paramInt)
  {
    return networkapi_device_netstate(paramString, paramInt);
  }

  public String devicePair(String paramString, int paramInt)
  {
    return networkapi_device_pair(paramString, paramInt);
  }

  public String deviceProbe(String paramString)
  {
    return networkapi_device_probe(paramString);
  }

  public String deviceServerTime(String paramString, int paramInt)
  {
    return networkapi_device_server_time(paramString, paramInt);
  }
}