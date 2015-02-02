package cn.com.broadlink.blirdaconlib;

public class BLIrdaConLib
{
  static
  {
    System.loadLibrary("BLIrdaConLib");
  }

  public native BLIrdaConProduct irda_con_get_info(String paramString);

  public native BLIrdaConMatchClass irda_con_match(byte[] paramArrayOfByte);

  public native byte[] irda_low_data_output(String paramString, int paramInt1, int paramInt2, BLIrdaConState paramBLIrdaConState);
}
