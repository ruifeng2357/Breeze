package com.airapp.breeze;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.airapp.utils.Common;

public class WellComeActivity extends BaseActivity {

    public Handler handler= new Handler() {
        public void handleMessage(Message msg){
            startActivity(new Intent(WellComeActivity.this, SigninActivity.class));
            WellComeActivity.this.finish();
            overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected int getParentLayoutID() {
        return R.id.parent_layout;
    }
}
