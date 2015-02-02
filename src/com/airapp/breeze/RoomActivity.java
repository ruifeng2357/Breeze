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
import cn.com.broadlink.blnetworkdataparse.BLNetworkDataParse;
import cn.com.broadlink.blnetworkdataparse.BLeAir1Info;
import cn.com.broadlink.blnetworkunit.SendDataResultInfo;
import com.airapp.dataclass.ManageDevice;
import com.airapp.model.STRoomInfo;
import com.airapp.utils.Common;
import com.airapp.utils.ResolutionSet;
import com.airapp.utils.SQLiteDBHelper;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RoomActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener{
    boolean isShowLeft = false;
    boolean isShowData = false;

    protected String[] mAirValues;
    protected String[] mLightValues;
    protected String[] mVoicesValues;

    private ManageDevice mControlDevice;
    private BLNetworkDataParse mBroadLinkNetworkData;

    SQLiteDBHelper m_db = null;
    ArrayList<STRoomInfo> arrDatas = new ArrayList<STRoomInfo>();

    RelativeLayout rlTitle;
    RelativeLayout rlLeft;
    RelativeLayout rlData;
    RelativeLayout rlBack;
    ListView listRooms;
    TextView textTitle;

    private Timer mGetA1InfoTimer;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        m_db = new SQLiteDBHelper(RoomActivity.this);

        initControl();
    }

    @Override
    protected int getParentLayoutID() {
        return R.id.parent_layout;
    }

    private void initControl()
    {
        mBroadLinkNetworkData = BLNetworkDataParse.getInstance();
        mControlDevice = BreezeApplication.allDeviceList.get(0);

        rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        rlBack.setOnTouchListener(this);

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
                rlBack.setBackgroundDrawable(new BitmapDrawable(Common.getBitmapFromPath(roomInfo.imagePath)));
                textTitle.setText(roomInfo.roomName);
            }
        }

        listRooms.setAdapter(new CustomBaseAdapter(this, arrDatas));

        mAirValues = getResources().getStringArray(R.array.air_array);
        mLightValues = getResources().getStringArray(R.array.light_array);
        mVoicesValues = getResources().getStringArray(R.array.voice_array);

        refreshValue();

		return;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mGetA1InfoTimer != null)
        {
            mGetA1InfoTimer.cancel();
            mGetA1InfoTimer = null;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mGetA1InfoTimer == null)
        {
            final byte[] arrayOfByte = mBroadLinkNetworkData.BLeAirRefreshBytes();
            mGetA1InfoTimer = new Timer();
            mGetA1InfoTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getA1Info(arrayOfByte);
                }
            }, 0L, 3000);
        }
    }

    private void refreshValue()
    {
        if (mControlDevice != null)
        {
            TextView textAir = (TextView) findViewById(R.id.textAir);
            textAir.setText(mAirValues[mControlDevice.getbLeAir1Info().air_condition]);
            TextView textTemp = (TextView) findViewById(R.id.textTemp);
            textTemp.setText(String.format("%.1f", mControlDevice.getbLeAir1Info().temperature));
            TextView textHum = (TextView) findViewById(R.id.textHum);
            textHum.setText(String.format("%.1f%%", mControlDevice.getbLeAir1Info().humidity));
            TextView textLight = (TextView) findViewById(R.id.textLight);
            textLight.setText(mLightValues[mControlDevice.getbLeAir1Info().light]);
            TextView textVoice = (TextView) findViewById(R.id.textVoice);
            textVoice.setText(mVoicesValues[mControlDevice.getbLeAir1Info().voice]);
        }
    }

    private void getA1Info(byte[] arrOfByte)
    {
        try {
            SendDataResultInfo sendDataResultInfo = BreezeApplication.mBlNetworkUnit.sendData(this.mControlDevice.getDeviceMac(), arrOfByte, 1, 2, 1);
            if (sendDataResultInfo != null && sendDataResultInfo.resultCode == 0)
            {
                BLeAir1Info bLeAir1Info = this.mBroadLinkNetworkData.BLeAirRefreshResultParse(sendDataResultInfo.data);
                if (bLeAir1Info != null)
                {
                    mControlDevice.setbLeAir1Info(bLeAir1Info);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshValue();
                        }
                    });
                }
            }
        } catch (Exception ex) {
            ;
        }
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.rlBack) {
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
                            rlBack.setBackgroundDrawable(new BitmapDrawable(Common.getBitmapFromPath(roomInfo.imagePath)));
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
