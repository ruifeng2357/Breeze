package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class S1IFTTT
  implements Serializable
{
  private static final long serialVersionUID = 7331586906198642705L;
  private byte[] id;
  private int index;

  public byte[] getId()
  {
    return this.id;
  }

  public int getIndex()
  {
    return this.index;
  }

  public void setId(byte[] paramArrayOfByte)
  {
    this.id = paramArrayOfByte;
  }

  public void setIndex(int paramInt)
  {
    this.index = paramInt;
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.S1IFTTT
 * JD-Core Version:    0.6.0
 */