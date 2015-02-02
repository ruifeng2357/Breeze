package cn.com.broadlink.broadlinkrfswitch;

public class BLRFSwitch
{
  static
  {
    System.loadLibrary("BroadLinkRFSwitch");
  }

  private native BLRFCustomSwitchLearningCode get_bl_rf_custom_learning_code(int paramInt1, int paramInt2);

  private native byte[] get_bl_rf_custom_switchcontrol_bytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  private native BLRFTCSwitchLearningCode get_bl_rf_tc_learning_code(int paramInt1, int paramInt2);

  private native byte[] get_bl_rf_tc_switchcontrol_bytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public byte[] getBLRFCustomSwtichControlBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return get_bl_rf_custom_switchcontrol_bytes(paramArrayOfByte, paramInt1, paramInt2);
  }

  public BLRFCustomSwitchLearningCode getBLRFCustomSwtichLearningCode(int paramInt1, int paramInt2)
  {
    return get_bl_rf_custom_learning_code(paramInt1, paramInt2);
  }

  public byte[] getBLRFTCSwtichControlBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return get_bl_rf_tc_switchcontrol_bytes(paramArrayOfByte, paramInt1, paramInt2);
  }

  public BLRFTCSwitchLearningCode getBLRFTCSwtichLearningCode(int paramInt1, int paramInt2)
  {
    return get_bl_rf_tc_learning_code(paramInt1, paramInt2);
  }

  public native BLRFTCSwitch1Code getBlrftcSwitch1Code(int paramInt1, int paramInt2);

  public native BLRFTCSwitch3Code getBlrftcSwitch3Code(int paramInt1, int paramInt2);
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.broadlinkrfswitch.BLRFSwitch
 * JD-Core Version:    0.6.0
 */