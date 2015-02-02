package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class SpminiCycleTimer
  implements Serializable
{
  private static final long serialVersionUID = 8962415172339810968L;
  public int enable;
  public int endHour;
  public int endMinute;
  public int endSecond;
  public int off_level;
  public int on_level;
  public int startHour;
  public int startMinute;
  public int startSecond;
  public int[] weeks = new int[7];
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.SpminiCycleTimer
 * JD-Core Version:    0.6.0
 */