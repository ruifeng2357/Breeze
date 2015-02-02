package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class EairTimeInfo
  implements Serializable
{
  private static final long serialVersionUID = -5325275372753771417L;
  public int day;
  public int hour;
  public int minute;
  public int month;
  public int second;
  public int[] weeks = { 1, 1, 1, 1, 1, 1, 1 };
  public int year;
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.EairTimeInfo
 * JD-Core Version:    0.6.0
 */