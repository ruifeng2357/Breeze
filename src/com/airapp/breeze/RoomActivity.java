package com.airapp.breeze;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.airapp.model.STRoomInfo;
import com.airapp.utils.Common;
import com.airapp.utils.ResolutionSet;
import com.airapp.utils.SQLiteDBHelper;

import java.util.ArrayList;

public class RoomActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener{
    boolean isShowLeft = false;
    boolean isShowData = false;

    SQLiteDBHelper m_db = null;
    ArrayList<STRoomInfo> arrDatas = new ArrayList<STRoomInfo>();

    RelativeLayout rlTitle;
    RelativeLayout rlLeft;
    RelativeLayout rlData;
    ImageView imageBack;
    ListView listRooms;
    TextView textTitle;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        m_db = new SQLiteDBHelper(RoomActivity.this);
    }
    @Override
    public void onResume()
    {
        initControl();
        super.onResume();
    }

    @Override
    protected int getParentLayoutID() {
        return R.id.parent_layout;
    }

    private void initControl()
    {
        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnTouchListener(this);

        ImageView imageAddRoom = (ImageView) findViewById(R.id.imageAddRoom);
        imageAddRoom.setOnClickListener(this);

        rlTitle = (RelativeLayout) findViewById(R.id.rlTitle);
        rlLeft = (RelativeLayout) findViewById(R.id.rlLeft);
        rlData = (RelativeLayout) findViewById(R.id.rlData);
        rlData.setOnClickListener(this);

        listRooms = (ListView) findViewById(R.id.listRooms);
        textTitle = (TextView) findViewById(R.id.textTitle);

        ImageView imageSetting = (ImageView) findViewById(R.id.imageSetting);
        imageSetting.setOnClickListener(this);

        ImageView imageData = (ImageView) findViewById(R.id.imageAnalysis);
        imageData.setOnClickListener(this);

        arrDatas = m_db.getDataList();
        if (arrDatas.size() > 0) {
            STRoomInfo roomInfo = arrDatas.get(0);
            if (roomInfo != null) {
                imageBack.setBackgroundDrawable(new BitmapDrawable(Common.getBitmapFromPath(roomInfo.imagePath)));
                textTitle.setText(roomInfo.roomName);
            }
        }

        listRooms.setAdapter(new CustomBaseAdapter(this, arrDatas));

		return;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageAddRoom:
                Intent intent = new Intent(RoomActivity.this, AddRoomActivity.class);
                startActivity(intent);
                break;
            case R.id.imageSetting:
                if (isShowLeft == false) {
                    if (isShowData) {
                        isShowData = false;
                        hideData();
                    }
                    isShowLeft = true;
                    showAnimation();
                }
                break;
            case R.id.imageAnalysis:
                if (isShowData == false) {
                    if (isShowLeft) {
                        isShowLeft = false;
                        hideAnimation();
                    }
                    isShowData = true;
                    showData();
                }
                break;
            case R.id.rlData:
                if (isShowData == true){
                    isShowData = false;
                    hideData();
                }
                break;
            default:
                break;
        }
    }

    private void showAnimation()
    {
        Animation animation = AnimationUtils.loadAnimation(RoomActivity.this, R.anim.left_in);
        animation.setFillAfter(true);
        rlLeft.startAnimation(animation);
    }

    private void hideAnimation()
    {
        Animation animation = AnimationUtils.loadAnimation(RoomActivity.this, R.anim.left_out);
        animation.setFillAfter(true);
        rlLeft.startAnimation(animation);
    }

    private void showData()
    {
        Animation animation = AnimationUtils.loadAnimation(RoomActivity.this, R.anim.slide_in_from_bottom);
        animation.setFillAfter(true);
        rlData.startAnimation(animation);
    }

    private void hideData()
    {
        Animation animation = AnimationUtils.loadAnimation(RoomActivity.this, R.anim.slide_out_to_bottom);
        animation.setFillAfter(false);
        rlData.startAnimation(animation);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.imageBack) {
            if (isShowLeft == true) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int nWidth = Common.getScrWidth(RoomActivity.this);
                    float fX = event.getX();
                    if (fX <= nWidth / 2)
                        return false;

                    isShowLeft = false;
                    hideAnimation();
                }
            }
        }

        return false;
    }

    public class CustomBaseAdapter extends BaseAdapter {

        private LayoutInflater inflater = null;
        private ArrayList<STRoomInfo> infoList = null;
        private ViewHolder viewHolder = null;
        private Context mContext = null;

        public CustomBaseAdapter(Context context , ArrayList<STRoomInfo> arrays){
            this.mContext = context;
            this.inflater = LayoutInflater.from(context);
            this.infoList = arrays;
        }

        @Override
        public int getCount() {
            return infoList.size();
        }

        @Override
        public STRoomInfo getItem(int position) {
            return infoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup parent) {

            View v = convertview;

            if(v == null){
                viewHolder = new ViewHolder();
                v = inflater.inflate(R.layout.view_roomitem, null);
                if (RoomActivity.this.bInitialized == false)
                    ResolutionSet._instance.iterateChild(v);
                viewHolder.textRoomName = (TextView)v.findViewById(R.id.textRoomName);
                viewHolder.textRoomName.setTag(infoList.get(position));

                v.setTag(viewHolder);

            }else {
                viewHolder = (ViewHolder)v.getTag();
            }

            viewHolder.textRoomName.setText(infoList.get(position).roomName);
            viewHolder.textRoomName.setOnClickListener(textClickListener);

            return v;
        }

        public void setArrayList(ArrayList<STRoomInfo> arrays){
            this.infoList = arrays;
        }

        public ArrayList<STRoomInfo> getArrayList(){
            return infoList;
        }

        private View.OnClickListener textClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.textRoomName:
                        STRoomInfo roomInfo = (STRoomInfo)v.getTag();
                        if (roomInfo != null)
                        {
                            isShowLeft = false;
                            hideAnimation();

                            textTitle.setText(roomInfo.roomName);
                            imageBack.setBackgroundDrawable(new BitmapDrawable(Common.getBitmapFromPath(roomInfo.imagePath)));
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        class ViewHolder{
            public TextView textRoomName = null;
        }

        @Override
        protected void finalize() throws Throwable {
            free();
            super.finalize();
        }

        private void free(){
            inflater = null;
            infoList = null;
            viewHolder = null;
            mContext = null;
        }
    }
}
