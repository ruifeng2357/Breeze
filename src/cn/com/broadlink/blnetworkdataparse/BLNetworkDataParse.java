package cn.com.broadlink.blnetworkdataparse;

import java.util.ArrayList;

public class BLNetworkDataParse
{
  private static BLNetworkDataParse mBlNetworkDataParse;

  static
  {
    System.loadLibrary("BLNetworkDataParse");
  }

  private native byte[] bl_study_data_fix(byte[] paramArrayOfByte);

  private native byte[] check_cloud_condition_bytes(byte[] paramArrayOfByte);

  private native byte[] eAir1_refresh_bytes();

  private native BLeAir1Info eAir1_refresh_result_parse(byte[] paramArrayOfByte);

  public static BLNetworkDataParse getInstance()
  {
    if (mBlNetworkDataParse == null)
      mBlNetworkDataParse = new BLNetworkDataParse();
    return mBlNetworkDataParse;
  }

  private native byte[] rm1_auth_bytes(int paramInt);

  private native BLRM1Info rm1_auth_result_parse(byte[] paramArrayOfByte);

  private native byte[] rm1_check_study_result_bytes();

  private native byte[] rm1_enter_study_mode_bytes();

  private native byte[] rm1_send_irda_bytes(BLRM1Irda paramBLRM1Irda);

  private native BLRM1Irda rm1_study_result_parse(byte[] paramArrayOfByte);

  private native byte[] rm2_add_timer_task_bytes(BLRM2TimerTaskData paramBLRM2TimerTaskData);

  private native byte[] rm2_check_study_result_bytes();

  private native byte[] rm2_delete_timer_task_bytes(int paramInt);

  private native byte[] rm2_enter_study_mode_bytes();

  private native byte[] rm2_get_timer_task_list_bytes();

  private native byte[] rm2_refresh_bytes();

  private native BLRM2Info rm2_refresh_result_parse(byte[] paramArrayOfByte);

  private native byte[] rm2_send_irda_bytes(BLRM2Irda paramBLRM2Irda);

  private native BLRM2Irda rm2_study_result_parse(byte[] paramArrayOfByte);

  private native BLRM2TimerConfig rm2_timer_task_list_parse(byte[] paramArrayOfByte);

  private native byte[] set_v1_device_info_bytes(BLDeviceInfo paramBLDeviceInfo);

  private native byte[] set_v2_device_info_bytes(BLDeviceInfo paramBLDeviceInfo);

  private native byte[] sp1_auth_bytes(int paramInt);

  private native BLSP1Info sp1_auth_result_parse(byte[] paramArrayOfByte);

  private native byte[] sp1_refresh_bytes();

  private native byte[] sp1_set_timer_bytes(BLSP1TimerConfig paramBLSP1TimerConfig);

  private native byte[] sp1_switch_control_bytes(int paramInt);

  private native byte[] sp2_get_current_power_bytes();

  private native BLSP2CurrentPower sp2_get_current_power_parse(byte[] paramArrayOfByte);

  private native byte[] sp2_get_day_energy_bytes();

  private native BLSP2DetailEnergy sp2_get_day_energy_parse(byte[] paramArrayOfByte);

  private native BLSP2EnergyList sp2_get_energy_list_parse(byte[] paramArrayOfByte);

  private native byte[] sp2_get_month_energy_bytes(int paramInt1, int paramInt2);

  private native byte[] sp2_get_standby_power_bytes();

  private native BLSP2AutoSaveInfo sp2_get_standby_power_parse(byte[] paramArrayOfByte);

  private native byte[] sp2_get_week_energy_bytes(int paramInt);

  private native byte[] sp2_get_year_energy_bytes(int paramInt);

  private native byte[] sp2_refresh_bytes();

  private native BLSP2Info sp2_refresh_result_parse(byte[] paramArrayOfByte);

  private native byte[] sp2_set_peak_time_bytes(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  private native byte[] sp2_set_standby_power_bytes(int paramInt1, int paramInt2);

  private native byte[] sp2_set_timer_bytes(BLSP2TimerConfig paramBLSP2TimerConfig);

  private native byte[] sp2_switch_control_bytes(int paramInt);

  private native V2VersionInfo v1_device_version_parse(byte[] paramArrayOfByte);

  private native byte[] v1_set_auto_update(int paramInt);

  private native byte[] v1_set_force_update(byte[] paramArrayOfByte);

  private native V2VersionInfo v2_device_version_parse(byte[] paramArrayOfByte);

  private native byte[] v2_get_version_info();

  private native byte[] v2_set_auto_update(int paramInt);

  private native byte[] v2_set_force_update(byte[] paramArrayOfByte);

  public byte[] BLDataFix(byte[] paramArrayOfByte)
  {
    return bl_study_data_fix(paramArrayOfByte);
  }

  public byte[] BLRM1AuthBytes(int paramInt)
  {
    return rm1_auth_bytes(paramInt);
  }

  public BLRM1Info BLRM1AuthResultParse(byte[] paramArrayOfByte)
  {
    return rm1_auth_result_parse(paramArrayOfByte);
  }

  public byte[] BLRM1CheckStudyResultBytes()
  {
    return rm1_check_study_result_bytes();
  }

  public byte[] BLRM1EnterStudyModeBytes()
  {
    return rm1_enter_study_mode_bytes();
  }

  public byte[] BLRM1IrdaSendBytes(BLRM1Irda paramBLRM1Irda)
  {
    return rm1_send_irda_bytes(paramBLRM1Irda);
  }

  public BLRM1Irda BLRM1StudyResultParse(byte[] paramArrayOfByte)
  {
    return rm1_study_result_parse(paramArrayOfByte);
  }

  public byte[] BLRM2AddTimerTask(BLRM2TimerTaskData paramBLRM2TimerTaskData)
  {
    return rm2_add_timer_task_bytes(paramBLRM2TimerTaskData);
  }

  public byte[] BLRM2CheckStudyResultBytes()
  {
    return rm2_check_study_result_bytes();
  }

  public byte[] BLRM2DeleteTimerTask(int paramInt)
  {
    return rm2_delete_timer_task_bytes(paramInt);
  }

  public byte[] BLRM2EnterStudyModeBytes()
  {
    return rm2_enter_study_mode_bytes();
  }

  public byte[] BLRM2GetTimerTaskListBytes()
  {
    return rm2_get_timer_task_list_bytes();
  }

  public byte[] BLRM2IrdaSendBytes(BLRM2Irda paramBLRM2Irda)
  {
    return rm2_send_irda_bytes(paramBLRM2Irda);
  }

  public byte[] BLRM2RefreshBytes()
  {
    return rm2_refresh_bytes();
  }

  public BLRM2Info BLRM2RefreshResultParse(byte[] paramArrayOfByte)
  {
    return rm2_refresh_result_parse(paramArrayOfByte);
  }

  public BLRM2Irda BLRM2StudyResultParse(byte[] paramArrayOfByte)
  {
    return rm2_study_result_parse(paramArrayOfByte);
  }

  public BLRM2TimerConfig BLRM2TimerTaskListResultParse(byte[] paramArrayOfByte)
  {
    return rm2_timer_task_list_parse(paramArrayOfByte);
  }

  public byte[] BLSP1AuthBytes(int paramInt)
  {
    return sp1_auth_bytes(paramInt);
  }

  public BLSP1Info BLSP1AuthResultParse(byte[] paramArrayOfByte)
  {
    return sp1_auth_result_parse(paramArrayOfByte);
  }

  public byte[] BLSP1RefreshBytes()
  {
    return sp1_refresh_bytes();
  }

  public BLSP1Info BLSP1RefreshResultParse(byte[] paramArrayOfByte)
  {
    return sp1_auth_result_parse(paramArrayOfByte);
  }

  public byte[] BLSP1SetTimerBytes(BLSP1TimerConfig paramBLSP1TimerConfig)
  {
    return sp1_set_timer_bytes(paramBLSP1TimerConfig);
  }

  public byte[] BLSP1SwitchControlBytes(int paramInt)
  {
    return sp1_switch_control_bytes(paramInt);
  }

  public byte[] BLSP2GetCurrentPowerBytes()
  {
    return sp2_get_current_power_bytes();
  }

  public BLSP2CurrentPower BLSP2GetCurrentPowerResultParse(byte[] paramArrayOfByte)
  {
    return sp2_get_current_power_parse(paramArrayOfByte);
  }

  public byte[] BLSP2GetDayEnergyBytes()
  {
    return sp2_get_day_energy_bytes();
  }

  public BLSP2DetailEnergy BLSP2GetDayEnergyResultParse(byte[] paramArrayOfByte)
  {
    return sp2_get_day_energy_parse(paramArrayOfByte);
  }

  public BLSP2EnergyList BLSP2GetEnergyListResultParse(byte[] paramArrayOfByte)
  {
    return sp2_get_energy_list_parse(paramArrayOfByte);
  }

  public byte[] BLSP2GetMonthEnergyBytes(int paramInt1, int paramInt2)
  {
    return sp2_get_month_energy_bytes(paramInt1, paramInt2);
  }

  public byte[] BLSP2GetStandbyPowerBytes()
  {
    return sp2_get_standby_power_bytes();
  }

  public BLSP2AutoSaveInfo BLSP2GetStandbyPowerResultParse(byte[] paramArrayOfByte)
  {
    return sp2_get_standby_power_parse(paramArrayOfByte);
  }

  public byte[] BLSP2GetWeekEnergyBytes(int paramInt)
  {
    return sp2_get_week_energy_bytes(paramInt);
  }

  public byte[] BLSP2GetYearEnergyBytes(int paramInt)
  {
    return sp2_get_year_energy_bytes(paramInt);
  }

  public byte[] BLSP2RefreshBytes()
  {
    return sp2_refresh_bytes();
  }

  public BLSP2Info BLSP2RefreshResultParse(byte[] paramArrayOfByte)
  {
    return sp2_refresh_result_parse(paramArrayOfByte);
  }

  public byte[] BLSP2SetPeakTimeBytes(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return sp2_set_peak_time_bytes(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public byte[] BLSP2SetStandbyPowerBytes(int paramInt1, int paramInt2)
  {
    return sp2_set_standby_power_bytes(paramInt1, paramInt2);
  }

  public byte[] BLSP2SetTimerBytes(BLSP2TimerConfig paramBLSP2TimerConfig)
  {
    return sp2_set_timer_bytes(paramBLSP2TimerConfig);
  }

  public byte[] BLSP2SwitchControlBytes(int paramInt)
  {
    return sp2_switch_control_bytes(paramInt);
  }

  public byte[] BLSetV1DeviceInfoBytes(BLDeviceInfo paramBLDeviceInfo)
  {
    return set_v1_device_info_bytes(paramBLDeviceInfo);
  }

  public byte[] BLSetV2DeviceInfoBytes(BLDeviceInfo paramBLDeviceInfo)
  {
    return set_v2_device_info_bytes(paramBLDeviceInfo);
  }

  public byte[] BLeAirRefreshBytes()
  {
    return eAir1_refresh_bytes();
  }

  public BLeAir1Info BLeAirRefreshResultParse(byte[] paramArrayOfByte)
  {
    return eAir1_refresh_result_parse(paramArrayOfByte);
  }

  public byte[] CheckCloudConditionBytes(byte[] paramArrayOfByte)
  {
    return check_cloud_condition_bytes(paramArrayOfByte);
  }

  public native byte[] deleteIFTTT(int paramInt);

  public native byte[] dooyaControl(int paramInt1, int paramInt2);

  public native DooyaInfo dooyaParseResultInfo(byte[] paramArrayOfByte);

  public native byte[] eairGroudButtonByte(BLRM2TimerTaskData paramBLRM2TimerTaskData);

  public native EfergyEnergyList efergyEnergyListParse(byte[] paramArrayOfByte);

  public native byte[] efergyGetEnergyYear(int paramInt);

  public native EfergySPInfo efergySPInfoParse(byte[] paramArrayOfByte);

  public native byte[] efergySetAntiTheftTimeInfo(AntiTheftTimeInfo paramAntiTheftTimeInfo);

  public native byte[] efergySetStandbyMode(EfergyStandbyMode paramEfergyStandbyMode);

  public native EfergyStandbyMode efergySetStandbyModeParse(byte[] paramArrayOfByte);

  public native AntiTheftTimeList enoAntiTimerListPasre(byte[] paramArrayOfByte);

  public native byte[] enoDeviceReset();

  public EfergyEnergyList enoEnergyListParse(byte[] paramArrayOfByte)
  {
    return efergyEnergyListParse(paramArrayOfByte);
  }

  public native byte[] enoGetAntiTimerListBytes();

  public byte[] enoGetEnergyYear(int paramInt)
  {
    return efergyGetEnergyYear(paramInt);
  }

  public native byte[] enoGetRealTimeBytes();

  public native RealTime enoParseRealTime(byte[] paramArrayOfByte);

  public native byte[] enoQueryDST();

  public native int enoQueryDSTResultParse(byte[] paramArrayOfByte);

  public EfergySPInfo enoSPInfoParse(byte[] paramArrayOfByte)
  {
    return efergySPInfoParse(paramArrayOfByte);
  }

  public byte[] enoSetAntiTheftTimeInfo(AntiTheftTimeInfo paramAntiTheftTimeInfo)
  {
    return efergySetAntiTheftTimeInfo(paramAntiTheftTimeInfo);
  }

  public native byte[] enoSetAntiTimerList(AntiTheftTimeList paramAntiTheftTimeList);

  public native byte[] enoSetDST(int paramInt);

  public native byte[] getIFTTT();

  public native IFTTTResult parseIFTTTList(byte[] paramArrayOfByte);

  public native byte[] s1AddIfttt(byte[] paramArrayOfByte);

  public native byte[] s1AddNewSensor(S1SensorInfo paramS1SensorInfo);

  public native byte[] s1DeleteIfttt(int paramInt);

  public native byte[] s1DeleteOldSensor(long paramLong);

  public native byte[] s1EditSensorName(int paramInt, byte[] paramArrayOfByte);

  public byte[] s1EliminateSystemAlarm()
  {
    return setMsgType(25);
  }

  public byte[] s1GetAlarmNoticeTime()
  {
    return setMsgType(22);
  }

  public native byte[] s1GetIfttt();

  public native byte[] s1GetSensorAlarmState();

  public native byte[] s1GetSensorFortify(int paramInt);

  public native byte[] s1GetSensorList();

  public native byte[] s1GetSensorUseInfo(int paramInt);

  public native byte[] s1GetSystemConfig();

  public native byte[] s1GetWorkState();

  public native int s1ParseAddIftttResult(byte[] paramArrayOfByte);

  public native int s1ParseAddIftttStatus(byte[] paramArrayOfByte);

  public native S1AlarmNoticeTime s1ParseGetAlarmNoticeTime(byte[] paramArrayOfByte);

  public native S1AlarmState s1ParseGetSensorAlarmState(byte[] paramArrayOfByte);

  public native S1SensorUseInfo s1ParseGetSensorUseInfo(byte[] paramArrayOfByte);

  public native ArrayList<S1IFTTT> s1ParseIftttList(byte[] paramArrayOfByte);

  public native S1SensorProtectMap s1ParseQuerySensorPritectMap(byte[] paramArrayOfByte);

  public native S1SensorFortifyInfo s1ParseSensorFortify(byte[] paramArrayOfByte);

  public native ArrayList<S1SensorInfo> s1ParseSensorList(byte[] paramArrayOfByte);

  public native S1SensorProtect s1ParseSensorProtect(byte[] paramArrayOfByte);

  public native byte[] s1QueryAddIftttStatus(int paramInt);

  public native byte[] s1QuerySensorPritectMap();

  public native byte[] s1QuerySensorProtect(int paramInt);

  public native byte[] s1SetAlarmNoticeTime(int paramInt1, int paramInt2);

  public native byte[] s1SetQuerySensorPritectMap(S1SensorProtectMap paramS1SensorProtectMap);

  public native byte[] s1SetSensorFortify(S1SensorFortifyInfo paramS1SensorFortifyInfo);

  public native byte[] s1SetSensorProtect(int paramInt1, int paramInt2);

  public native byte[] s1SetSensorUseInfo(S1SensorUseInfo paramS1SensorUseInfo);

  public native byte[] s1SetSystemConfig(int[] paramArrayOfInt);

  public native byte[] s1UpdateIfttt(int paramInt, byte[] paramArrayOfByte);

  public native int[] s1parseSystemConfig(byte[] paramArrayOfByte);

  public native byte[] sendV1Device(int paramInt1, int paramInt2, int paramInt3, String paramString, byte[] paramArrayOfByte);

  public native byte[] setIFTTT(IFTTT paramIFTTT);

  public native byte[] setMsgType(int paramInt);

  public native SpminiInfo spminiRefreshInfo(byte[] paramArrayOfByte);

  public native byte[] spminiSetEleProtect(int paramInt1, int paramInt2);

  public native byte[] spminiSetTimerInfo(SpminiInfo paramSpminiInfo);

  public V2VersionInfo v1DeviceVersionParse(byte[] paramArrayOfByte)
  {
    return v1_device_version_parse(paramArrayOfByte);
  }

  public byte[] v1SetAutoUpdate(int paramInt)
  {
    return v1_set_auto_update(paramInt);
  }

  public byte[] v1SetForceUpdate(byte[] paramArrayOfByte)
  {
    return v1_set_force_update(paramArrayOfByte);
  }

  public V2VersionInfo v2DeviceVersionParse(byte[] paramArrayOfByte)
  {
    return v2_device_version_parse(paramArrayOfByte);
  }

  public byte[] v2GetDeviceVersionInfo()
  {
    return v2_get_version_info();
  }

  public byte[] v2SetAutoUpdate(int paramInt)
  {
    return v2_set_auto_update(paramInt);
  }

  public byte[] v2SetForceUpdate(byte[] paramArrayOfByte)
  {
    return v2_set_force_update(paramArrayOfByte);
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.BLNetworkDataParse
 * JD-Core Version:    0.6.0
 */