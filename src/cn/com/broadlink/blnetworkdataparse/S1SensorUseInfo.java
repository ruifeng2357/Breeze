package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;

public class S1SensorUseInfo
  implements Serializable
{
  private static final long serialVersionUID = 8772747831985649221L;
  private int apply;
  private int index;
  private int use;

  public int getApply()
  {
    return this.apply;
  }

  public int getIndex()
  {
    return this.index;
  }

  public int getUse()
  {
    return this.use;
  }

  public void setApply(int paramInt)
  {
    this.apply = paramInt;
  }

  public void setIndex(int paramInt)
  {
    this.index = paramInt;
  }

  public void setUse(int paramInt)
  {
    this.use = paramInt;
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.S1SensorUseInfo
 * JD-Core Version:    0.6.0
 */