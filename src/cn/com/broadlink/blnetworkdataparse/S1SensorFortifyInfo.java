package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;
import java.util.ArrayList;

public class S1SensorFortifyInfo
  implements Serializable
{
  private static final long serialVersionUID = 1441469427638231908L;
  private int index;
  private ArrayList<S1SensorSecInfo> secList = new ArrayList();

  public int getIndex()
  {
    return this.index;
  }

  public ArrayList<S1SensorSecInfo> getSecList()
  {
    return this.secList;
  }

  public void setIndex(int paramInt)
  {
    this.index = paramInt;
  }

  public void setSecList(ArrayList<S1SensorSecInfo> paramArrayList)
  {
    this.secList = paramArrayList;
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.S1SensorFortifyInfo
 * JD-Core Version:    0.6.0
 */