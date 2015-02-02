package cn.com.broadlink.blnetworkdataparse;

import java.io.Serializable;
import java.util.ArrayList;

public class S1SensorInfo
  implements Serializable
{
  private static final long serialVersionUID = 3001939314171357374L;
  private int alarm;
  private int apply;
  private long device_id;
  private long index;
  private byte[] name = new byte[21];
  private long private_key;
  private long product_id;
  private int protect;
  private byte[] res = new byte[12];
  private ArrayList<S1SubSensorInfo> sub = new ArrayList();
  private int use;
  private long value;
  private long vendor_id;

  public int getAlarm()
  {
    return this.alarm;
  }

  public int getApply()
  {
    return this.apply;
  }

  public long getDevice_id()
  {
    return this.device_id;
  }

  public long getIndex()
  {
    return this.index;
  }

  public byte[] getName()
  {
    return this.name;
  }

  public long getPrivate_key()
  {
    return this.private_key;
  }

  public long getProduct_id()
  {
    return this.product_id;
  }

  public int getProtect()
  {
    return this.protect;
  }

  public byte[] getRes()
  {
    return this.res;
  }

  public ArrayList<S1SubSensorInfo> getSub()
  {
    return this.sub;
  }

  public int getUse()
  {
    return this.use;
  }

  public long getValue()
  {
    return this.value;
  }

  public long getVendor_id()
  {
    return this.vendor_id;
  }

  public void setAlarm(int paramInt)
  {
    this.alarm = paramInt;
  }

  public void setApply(int paramInt)
  {
    this.apply = paramInt;
  }

  public void setDevice_id(long paramLong)
  {
    this.device_id = paramLong;
  }

  public void setIndex(long paramLong)
  {
    this.index = paramLong;
  }

  public void setName(byte[] paramArrayOfByte)
  {
    this.name = paramArrayOfByte;
  }

  public void setPrivate_key(long paramLong)
  {
    this.private_key = paramLong;
  }

  public void setProduct_id(long paramLong)
  {
    this.product_id = paramLong;
  }

  public void setProtect(int paramInt)
  {
    this.protect = paramInt;
  }

  public void setRes(byte[] paramArrayOfByte)
  {
    this.res = paramArrayOfByte;
  }

  public void setSub(ArrayList<S1SubSensorInfo> paramArrayList)
  {
    this.sub = paramArrayList;
  }

  public void setUse(int paramInt)
  {
    this.use = paramInt;
  }

  public void setValue(long paramLong)
  {
    this.value = paramLong;
  }

  public void setVendor_id(long paramLong)
  {
    this.vendor_id = paramLong;
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.S1SensorInfo
 * JD-Core Version:    0.6.0
 */