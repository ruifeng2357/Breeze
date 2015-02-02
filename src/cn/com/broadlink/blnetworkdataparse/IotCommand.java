package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class IotCommand
  implements Serializable
{
  private static final long serialVersionUID = 5140509001656490320L;
  public byte[] data;
  public int id;
  public String mac;
  public byte[] publicKey = new byte[12];
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.IotCommand
 * JD-Core Version:    0.6.0
 */