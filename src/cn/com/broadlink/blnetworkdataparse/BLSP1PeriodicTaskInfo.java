package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class BLSP1PeriodicTaskInfo
  implements Serializable
{
  private static final long serialVersionUID = 7728634023951832472L;
  public int enable;
  public int offHour;
  public int offMin;
  public int offTimeDone;
  public int onHour;
  public int onMin;
  public int onTimeDone;
  public int[] weeks;
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.BLSP1PeriodicTaskInfo
 * JD-Core Version:    0.6.0
 */