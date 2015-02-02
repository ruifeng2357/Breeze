package com.airapp.breeze;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.airapp.utils.Common;
import com.airapp.utils.Logger;
import com.airapp.utils.SQLiteDBHelper;

public class SigninActivity extends BaseActivity {
    private static long back_pressed;

    String mSSID = "";

    SQLiteDBHelper m_db = null;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.buttonSignin:
                    int nRows = m_db.getRows();

                    if (nRows == 0) {
                        Intent intent = new Intent(SigninActivity.this, AddRoomActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(SigninActivity.this, RoomActivity.class);
                        startActivity(intent);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        m_db = new SQLiteDBHelper(SigninActivity.this);

        initControl();
    }

    @Override
    protected int getParentLayoutID() {
        return R.id.parent_layout;
    }

    private void initControl()
    {
        Button buttonSignin = (Button) findViewById(R.id.buttonSignin);
        buttonSignin.setOnClickListener(onClickListener);

        WifiManager wifiManager = (WifiManager) getSystemService("wifi");
        mSSID = "";

        try
        {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String szWifiInfo = wifiInfo.toString();

            if (szWifiInfo.contains(szWifiInfo))
                mSSID = szWifiInfo;
            else
                mSSID = "";

            Logger.logError("Wifi Detect", mSSID);

        } catch (Exception ex) {}

        return;
    }

    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Common.showToast(SigninActivity.this, getString(R.string.exitapp));
            back_pressed = System.currentTimeMillis();
        }
    }
}
