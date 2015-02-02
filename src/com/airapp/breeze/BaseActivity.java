package com.airapp.breeze;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import com.airapp.utils.ResolutionSet;

/**
 * Created by ruifeng on 2015/1/20.
 */
public abstract class BaseActivity extends Activity {
    protected RelativeLayout mainLayout;
    protected boolean bInitialized = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    protected abstract int getParentLayoutID();

    @Override
    public void setContentView(int layoutID)
    {
        super.setContentView(layoutID);
        initLayout();

        return;
    }

    public void initLayout()
    {
        mainLayout = (RelativeLayout)findViewById(getParentLayoutID());
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        if (bInitialized == false) {
                            Rect r = new Rect();
                            mainLayout.getLocalVisibleRect(r);
                            ResolutionSet._instance.setResolution(r.width(), r.height(), true);
                            ResolutionSet._instance.iterateChild(mainLayout);
                            bInitialized = true;
                        }
                    }
                }
        );

        return;
    }
}
