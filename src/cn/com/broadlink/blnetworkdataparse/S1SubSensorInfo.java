package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class S1SubSensorInfo
  implements Serializable
{
  private static final long serialVersionUID = 1848181152972186367L;
  private int alarm;
  private int alarm_tread;
  private int alarm_value;
  private int delay;
  private int len;
  private int master;
  private int offset;
  private int value;

  public int getAlarm()
  {
    return this.alarm;
  }

  public int getAlarm_tread()
  {
    return this.alarm_tread;
  }

  public int getAlarm_value()
  {
    return this.alarm_value;
  }

  public int getDelay()
  {
    return this.delay;
  }

  public int getLen()
  {
    return this.len;
  }

  public int getMaster()
  {
    return this.master;
  }

  public int getOffset()
  {
    return this.offset;
  }

  public int getValue()
  {
    return this.value;
  }

  public void setAlarm(int paramInt)
  {
    this.alarm = paramInt;
  }

  public void setAlarm_tread(int paramInt)
  {
    this.alarm_tread = paramInt;
  }

  public void setAlarm_value(int paramInt)
  {
    this.alarm_value = paramInt;
  }

  public void setDelay(int paramInt)
  {
    this.delay = paramInt;
  }

  public void setLen(int paramInt)
  {
    this.len = paramInt;
  }

  public void setMaster(int paramInt)
  {
    this.master = paramInt;
  }

  public void setOffset(int paramInt)
  {
    this.offset = paramInt;
  }

  public void setValue(int paramInt)
  {
    this.value = paramInt;
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.S1SubSensorInfo
 * JD-Core Version:    0.6.0
 */