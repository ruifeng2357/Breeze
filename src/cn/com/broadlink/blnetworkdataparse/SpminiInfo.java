package cn.com.broadlink.blnetworkdataparse;

import java.util.ArrayList;

public class SpminiInfo extends BLDeviceInfo
{
  public AntiTheftTimeInfo antiTheftTimeInfo;
  public SpminiCycleTimer cycleTimerInfo;
  public int eleProtect;
  public int eleProtectTime;
  public TimeInfo moduleTime;
  public ArrayList<SpminiPeriodicTaskInfo> periodicTaskList = new ArrayList();
  public int switchState;
  public ArrayList<BLSP2TimerTaskInfo> timerTaskList = new ArrayList();
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.SpminiInfo
 * JD-Core Version:    0.6.0
 */