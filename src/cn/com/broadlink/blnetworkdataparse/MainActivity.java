package cn.com.broadlink.blnetworkdataparse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(new LinearLayout(this));
    BLNetworkDataParse localBLNetworkDataParse = BLNetworkDataParse.getInstance();
    localBLNetworkDataParse.s1GetSensorList();
    int[] arrayOfInt = new int[32];
    arrayOfInt[0] = 1;
    arrayOfInt[1] = 0;
    arrayOfInt[2] = 1;
    arrayOfInt[3] = 0;
    localBLNetworkDataParse.s1parseSystemConfig(localBLNetworkDataParse.s1SetSystemConfig(arrayOfInt));
  }
}

/* Location:           D:\Freelancer\2015\Matthew_USA\1_AirApp\Source\Decompile\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     cn.com.broadlink.blnetworkdataparse.MainActivity
 * JD-Core Version:    0.6.0
 */