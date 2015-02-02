package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;
import java.util.ArrayList;

public class IFTTT extends IotCommand
  implements Serializable
{
  private static final long serialVersionUID = 5331059949006397555L;
  public int deviceVersion = 0;
  public int eairSensorType = -1;
  public int eairSensorValueDecimal;
  public int eairSensorValueInteger;
  public int eairTrigger;
  public int enable = 1;
  public int index;
  public byte[] itemName = new byte[0];
  public EairTimeInfo mEndEairTimeInfo = new EairTimeInfo();
  public EairTimeInfo mStartEairTimeInfo = new EairTimeInfo();
  public ArrayList<OtherSensor> otherSensorList = new ArrayList();
  public int timeEnable = 0;
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.IFTTT
 * JD-Core Version:    0.6.0
 */