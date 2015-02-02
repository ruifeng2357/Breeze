package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class S1AlarmNoticeTime
  implements Serializable
{
  private static final long serialVersionUID = 1926553737644477281L;
  private int disable;
  private int period;

  public int getDisable()
  {
    return this.disable;
  }

  public int getPeriod()
  {
    return this.period;
  }

  public void setDisable(int paramInt)
  {
    this.disable = paramInt;
  }

  public void setPeriod(int paramInt)
  {
    this.period = paramInt;
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.S1AlarmNoticeTime
 * JD-Core Version:    0.6.0
 */