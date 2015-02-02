package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class S1SensorAlarmState
  implements Serializable
{
  private static final long serialVersionUID = 1946079841476643470L;
  private int index;
  private int status;

  public int getIndex()
  {
    return this.index;
  }

  public int getStatus()
  {
    return this.status;
  }

  public void setIndex(int paramInt)
  {
    this.index = paramInt;
  }

  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.S1SensorAlarmState
 * JD-Core Version:    0.6.0
 */