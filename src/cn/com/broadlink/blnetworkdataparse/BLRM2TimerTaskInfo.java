package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class BLRM2TimerTaskInfo
  implements Serializable
{
  private static final long serialVersionUID = -499165867026985851L;
  public int enable;
  public int hour;
  public int index;
  public int minute;
  public byte[] name = new byte[60];
  public int[] weeks = new int[7];
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.BLRM2TimerTaskInfo
 * JD-Core Version:    0.6.0
 */