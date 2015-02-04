package com.airapp.breeze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.airapp.model.SQLiteDBHelper;
import com.airapp.utils.Common;

public class AddRoomActivity extends BaseActivity implements View.OnClickListener {
    String szPath = "";
    SQLiteDBHelper m_db = null;

    private ImageView imageCamera;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addroom);

        m_db = new SQLiteDBHelper(AddRoomActivity.this);

        initControl();
    }

    @Override
    protected int getParentLayoutID() {
        return R.id.parent_layout;
    }

    private void initControl()
    {
        RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        rlBack.setOnClickListener(this);
        if (m_db.getRows() == 0)
            rlBack.setVisibility(View.INVISIBLE);
        else
            rlBack.setVisibility(View.VISIBLE);

        imageCamera = (ImageView) findViewById(R.id.imageCamera);
        imageCamera.setOnClickListener(this);

        Button buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(this);

        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK)
        {
            updateIntentData(data);
        }
    }

    private void updateIntentData(Intent data) {
        if (data.getIntExtra(SelectPhotoActivity.szRetCode, -999) == SelectPhotoActivity.nRetSuccess) {
            Object objPath = data.getExtras().get(SelectPhotoActivity.szRetPath);

            szPath = "";
            if (objPath != null)
                szPath = (String) objPath;

            if (szPath != null && !szPath.equals("")) {
                imageCamera.setImageBitmap(Common.getBitmapFromPath(szPath));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlBack:
                AddRoomActivity.this.finish();
                break;
            case R.id.imageCamera:
                Intent intentCamera = new Intent(AddRoomActivity.this, SelectPhotoActivity.class);
                startActivityForResult(intentCamera, 0);
                break;
            case R.id.buttonOk:
                if (szPath.length() == 0)
                {
                    Common.showToast(AddRoomActivity.this, "Please take a picture!");
                    return;
                }

                EditText editName = (EditText) findViewById(R.id.editRoomName);
                if (editName.getText().toString().length() == 0)
                {
                    Common.showToast(AddRoomActivity.this, "Please insert the room name");
                    return;
                }

                m_db.saveRoomInfo(editName.getText().toString(), szPath);

                Intent intentRoom = new Intent(AddRoomActivity.this, RoomActivity.class);
                startActivity(intentRoom);
                finish();
                break;
            default:
                break;
        }
    }
}
