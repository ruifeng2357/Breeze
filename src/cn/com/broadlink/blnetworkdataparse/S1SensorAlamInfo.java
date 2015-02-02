package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class S1SensorAlamInfo
  implements Serializable
{
  private static final long serialVersionUID = 8772747831985649221L;
  private int enable;
  private int trend;
  private int value;

  public int getEnable()
  {
    return this.enable;
  }

  public int getTrend()
  {
    return this.trend;
  }

  public int getValue()
  {
    return this.value;
  }

  public void setEnable(int paramInt)
  {
    this.enable = paramInt;
  }

  public void setTrend(int paramInt)
  {
    this.trend = paramInt;
  }

  public void setValue(int paramInt)
  {
    this.value = paramInt;
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.S1SensorAlamInfo
 * JD-Core Version:    0.6.0
 */