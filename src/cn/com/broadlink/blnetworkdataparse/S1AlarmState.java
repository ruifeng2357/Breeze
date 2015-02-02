package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;
import java.util.ArrayList;

public class S1AlarmState
  implements Serializable
{
  private static final long serialVersionUID = -6212811451590648743L;
  private int count;
  private ArrayList<S1SensorAlarmState> statusList = new ArrayList();

  public int getCount()
  {
    return this.count;
  }

  public ArrayList<S1SensorAlarmState> getStatusList()
  {
    return this.statusList;
  }

  public void setCount(int paramInt)
  {
    this.count = paramInt;
  }

  public void setStatusList(ArrayList<S1SensorAlarmState> paramArrayList)
  {
    this.statusList = paramArrayList;
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.S1AlarmState
 * JD-Core Version:    0.6.0
 */