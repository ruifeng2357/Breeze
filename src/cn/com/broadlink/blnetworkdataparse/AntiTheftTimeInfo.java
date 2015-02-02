package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class AntiTheftTimeInfo
  implements Serializable
{
  private static final long serialVersionUID = 6839758561258212779L;
  public int enable;
  public int endHour;
  public int endtMin;
  public int runTime;
  public int startHour;
  public int startMin;
  public int[] weeks = new int[7];
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.AntiTheftTimeInfo
 * JD-Core Version:    0.6.0
 */