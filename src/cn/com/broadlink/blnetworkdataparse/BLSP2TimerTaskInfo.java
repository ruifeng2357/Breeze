package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class BLSP2TimerTaskInfo
  implements Serializable
{
  private static final long serialVersionUID = 5367754355410652218L;
  public int offEnable;
  public TimeInfo offTime = new TimeInfo();
  public int onEnable;
  public TimeInfo onTime = new TimeInfo();
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.BLSP2TimerTaskInfo
 * JD-Core Version:    0.6.0
 */