package cn.com.broadlink.blnetworkdataparse;

import java.util.ArrayList;

public class S1SensorProtectMap
{
  private int delayMin;
  private int delaySec;
  private ArrayList<S1SensorProtect> map = new ArrayList();
  private int type;

  public int getDelayMin()
  {
    return this.delayMin;
  }

  public int getDelaySec()
  {
    return this.delaySec;
  }

  public ArrayList<S1SensorProtect> getMap()
  {
    return this.map;
  }

  public int getType()
  {
    return this.type;
  }

  public void setDelayMin(int paramInt)
  {
    this.delayMin = paramInt;
  }

  public void setDelaySec(int paramInt)
  {
    this.delaySec = paramInt;
  }

  public void setMap(ArrayList<S1SensorProtect> paramArrayList)
  {
    this.map = paramArrayList;
  }

  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.S1SensorProtectMap
 * JD-Core Version:    0.6.0
 */