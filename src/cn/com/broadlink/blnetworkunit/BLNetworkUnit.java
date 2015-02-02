package cn.com.broadlink.blnetworkunit;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Build;
import android.telephony.TelephonyManager;
import java.util.List;

public class BLNetworkUnit
{
  private static DeviceStatusChangeListener mDeviceStatusChangeListener;
  private static String mIMEIString;
  private static MulticastMsgListener mMulticastMsgListener;
  private static String mNameString;
  private static ScanDeviceListener mScanDeviceListener;
  private static Context mconContext;
  private static BLNetworkUnit singleton;

  static
  {
    System.loadLibrary("BLNetworkUnit3");
  }

  private native int broadlink_netwokr_update_terminal(String paramString1, int paramInt1, String paramString2, int paramInt2);

  private native void broadlink_network_add_device_init(ScanDevice paramScanDevice);

  private native AuthDeviceInfo broadlink_network_add_iot(String paramString1, String paramString2, String paramString3);

  private static native void broadlink_network_advanced_init(String paramString1, String paramString2, String paramString3, int paramInt1, String paramString4, int paramInt2, String paramString5, int paramInt3);

  private native void broadlink_network_del_device(String paramString);

  private native int broadlink_network_del_iot(String paramString, int paramInt);

  private native int broadlink_network_destory();

  private native GatewayInfo broadlink_network_gateway_info(String paramString);

  private native String broadlink_network_get_device_local_ip_address(String paramString);

  private native int broadlink_network_get_device_net_state(String paramString);

  private native String broadlink_network_get_server_time();

  private native List<SubDeviceInfo> broadlink_network_get_subdevice_list(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  private native TerminalInfoList broadlink_network_get_terminal_list(String paramString);

  private static native void broadlink_network_init(String paramString1, String paramString2);

  private native SendDataResultInfo broadlink_network_old_send(String paramString, byte[] paramArrayOfByte, short paramShort, int paramInt1, int paramInt2, int paramInt3);

  private native int broadlink_network_pause();

  private native int broadlink_network_probe_pause(int paramInt);

  private native int broadlink_network_restart();

  private native SendDataResultInfo broadlink_network_send_data(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3);

  private native int broadlink_network_update_subdevice_info(String paramString, SubDeviceInfo paramSubDeviceInfo, int paramInt1, int paramInt2, int paramInt3);

  private native String broadlink_network_version();

  private static void getDeviceDataFromNetwork(DataInfo paramDataInfo)
  {
    if (mDeviceStatusChangeListener == null)
      return;
    final DataInfo localDataInfo = new DataInfo();
    localDataInfo.data = paramDataInfo.data;
    localDataInfo.mac = paramDataInfo.mac;
    new Thread(new Runnable()
    {
      public void run()
      {
        WifiManager.MulticastLock localMulticastLock = ((WifiManager)BLNetworkUnit.mconContext.getSystemService("wifi")).createMulticastLock("multicastLock");
        localMulticastLock.acquire();
        BLNetworkUnit.mDeviceStatusChangeListener.doCallBack(localDataInfo);
        localMulticastLock.release();
      }
    }).start();
  }

  private static void getDeviceInfoFromNetwork(ScanDevice paramScanDevice)
  {
    if (mScanDeviceListener == null)
      return;
    WifiManager.MulticastLock localMulticastLock = ((WifiManager)mconContext.getSystemService("wifi")).createMulticastLock("multicastLock");
    localMulticastLock.acquire();
    mScanDeviceListener.deviceInfoCallBack(paramScanDevice);
    localMulticastLock.release();
  }

  public static BLNetworkUnit getInstanceBlNetworkUnit(Object paramObject)
  {
    if (singleton != null)
      return singleton;
    try
    {
      if (singleton == null)
      {
        mconContext = (Context)paramObject;
        singleton = new BLNetworkUnit();
        mIMEIString = ((TelephonyManager)((Context)paramObject).getSystemService("phone")).getDeviceId();
        mNameString = Build.MODEL;
        broadlink_network_init(mNameString, mIMEIString);
      }
      return singleton;
    } catch (Exception ex){}

    return null;
  }

  public static BLNetworkUnit getInstanceBlNetworkUnit(Object paramObject, String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3, int paramInt3)
  {
    if (singleton != null)
      return singleton;
    try
    {
      if (singleton == null)
      {
        mconContext = (Context)paramObject;
        singleton = new BLNetworkUnit();
        mIMEIString = ((TelephonyManager)((Context)paramObject).getSystemService("phone")).getDeviceId();
        mNameString = Build.MODEL;
        broadlink_network_advanced_init(mNameString, mIMEIString, paramString1, paramInt1, paramString2, paramInt2, paramString3, paramInt3);
      }
      return singleton;
    } catch (Exception ex) {}

    return null;
  }

  private static void getMulticastMsg(MulticastMsg paramMulticastMsg)
  {
    if (mMulticastMsgListener == null)
      return;
    WifiManager.MulticastLock localMulticastLock = ((WifiManager)mconContext.getSystemService("wifi")).createMulticastLock("multicastLock");
    localMulticastLock.acquire();
    mMulticastMsgListener.multicastMsgCallBack(paramMulticastMsg);
    localMulticastLock.release();
  }

  private native String get_backup_udp_address(String paramString);

  private native String get_backup_udp_server();

  private native String get_main_udp_address(String paramString);

  private native String get_main_udp_server();

  public void addDevice(ScanDevice paramScanDevice)
  {
    broadlink_network_add_device_init(paramScanDevice);
  }

  public AuthDeviceInfo addIOTTerminal(String paramString1, String paramString2, String paramString3)
  {
    return broadlink_network_add_iot(paramString1, paramString2, paramString3);
  }

  public String getBackupUDPAddress(String paramString)
  {
    return get_backup_udp_address(paramString);
  }

  public String getBackupUDPServer()
  {
    return get_backup_udp_server();
  }

  public String getDeivceLocalIP(String paramString)
  {
    return broadlink_network_get_device_local_ip_address(paramString);
  }

  public int getDeviceNetState(String paramString)
  {
    return broadlink_network_get_device_net_state(paramString);
  }

  public String getMainUDPAddress(String paramString)
  {
    return get_main_udp_address(paramString);
  }

  public String getMainUDPServer()
  {
    return get_main_udp_server();
  }

  public String getServerTime()
  {
    return broadlink_network_get_server_time();
  }

  public TerminalInfoList getTerminalList(String paramString)
  {
    return broadlink_network_get_terminal_list(paramString);
  }

  public int networkDestory()
  {
    singleton = null;
    return broadlink_network_destory();
  }

  public GatewayInfo networkGatewayInfo(String paramString)
  {
    return broadlink_network_gateway_info(paramString);
  }

  public int networkPause()
  {
    return broadlink_network_pause();
  }

  public int networkRestart()
  {
    return broadlink_network_restart();
  }

  public String networkUnitVersion()
  {
    return broadlink_network_version();
  }

  public SendDataResultInfo oldModuleAuth(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {

    byte abyte0[] = new byte[4];
    int i1 = 0;
    do
    {
      if (i1 >= 4)
      {
        return oldSendData(paramString, abyte0, (short)100, paramInt2, paramInt3, paramInt4);
      }
      abyte0[i1] = (byte)(paramInt1 >> i1 * 8);
      i1++;
    } while (true);

//    byte[] arrayOfByte = new byte[4];
//    for (int i = 0; ; i++)
//    {
//      if (i >= 4)
//        return oldSendData(paramString, arrayOfByte, 100, paramInt2, paramInt3, paramInt4);
//      arrayOfByte[i] = (byte)(paramInt1 >> i * 8);
//    }
  }

  public SendDataResultInfo oldSendData(String paramString, byte[] paramArrayOfByte, short paramShort, int paramInt1, int paramInt2, int paramInt3)
  {
    return broadlink_network_old_send(paramString, paramArrayOfByte, paramShort, paramInt1, paramInt2, paramInt3);
  }

  public int probePause()
  {
    return broadlink_network_probe_pause(1);
  }

  public int probeRestart()
  {
    return broadlink_network_probe_pause(0);
  }

  public void removeDevice(String paramString)
  {
    broadlink_network_del_device(paramString);
  }

  public int removeIOTTerminal(String paramString, int paramInt)
  {
    return broadlink_network_del_iot(paramString, paramInt);
  }

  public SendDataResultInfo sendData(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    return broadlink_network_send_data(paramString, paramArrayOfByte, paramInt1, paramInt2, paramInt3);
  }

  public void setDeviceStatusChangeListener(DeviceStatusChangeListener paramDeviceStatusChangeListener)
  {
    mDeviceStatusChangeListener = paramDeviceStatusChangeListener;
  }

  public void setMulticastMsgListener(MulticastMsgListener paramMulticastMsgListener)
  {
    mMulticastMsgListener = paramMulticastMsgListener;
  }

  public void setScanDeviceListener(ScanDeviceListener paramScanDeviceListener)
  {
    mScanDeviceListener = paramScanDeviceListener;
  }

  public List<SubDeviceInfo> subDeviceList(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    return broadlink_network_get_subdevice_list(paramString, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  public int updateSubDeviceInfo(String paramString, SubDeviceInfo paramSubDeviceInfo, int paramInt1, int paramInt2, int paramInt3)
  {
    return broadlink_network_update_subdevice_info(paramString, paramSubDeviceInfo, paramInt1, paramInt2, paramInt3);
  }

  public int updateTerminalInfo(String paramString1, int paramInt1, String paramString2, int paramInt2)
  {
    return broadlink_netwokr_update_terminal(paramString1, paramInt1, paramString2, paramInt2);
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkunit.BLNetworkUnit
 * JD-Core Version:    0.6.0
 */