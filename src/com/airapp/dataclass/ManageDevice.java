package com.airapp.dataclass;

import cn.com.broadlink.blnetworkdataparse.BLeAir1Info;

public class ManageDevice
{
  private BLeAir1Info bLeAir1Info;
  private int deviceLock;
  private String deviceMac;
  private String deviceName;
  private int devicePassword;
  private short deviceType;
  private byte[] publicKey;
  private boolean news = true;

  public int getDeviceLock()
  {
    return this.deviceLock;
  }

  public String getDeviceMac()
  {
    return this.deviceMac;
  }

  public String getDeviceName()
  {
    return this.deviceName;
  }

  public int getDevicePassword()
  {
    return this.devicePassword;
  }

  public short getDeviceType()
  {
    return this.deviceType;
  }

  public byte[] getPublicKey()
  {
    return this.publicKey;
  }

  public boolean isNews()
  {
    return this.news;
  }

  public BLeAir1Info getbLeAir1Info()
  {
    return this.bLeAir1Info;
  }


  public void setDeviceLock(int paramInt)
  {
    this.deviceLock = paramInt;
  }

  public void setDeviceMac(String paramString)
  {
    this.deviceMac = paramString;
  }

  public void setDeviceName(String paramString)
  {
    this.deviceName = paramString;
  }

  public void setDevicePassword(int paramInt)
  {
    this.devicePassword = paramInt;
  }

  public void setDeviceType(short paramShort)
  {
    this.deviceType = paramShort;
  }

  public void setPublicKey(byte[] paramArrayOfByte)
  {
    this.publicKey = paramArrayOfByte;
  }

  public void setNews(boolean paramBoolean)
  {
    this.news = paramBoolean;
  }

  public void setbLeAir1Info(BLeAir1Info paramBLeAir1Info)
  {
    this.bLeAir1Info = paramBLeAir1Info;
  }
}