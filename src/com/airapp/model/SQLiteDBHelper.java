package com.airapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.airapp.model.STRoomInfo;
import java.util.ArrayList;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    /*
    * Database Name Definition
    */
    private final static String DATABASE_NAME = "Breeze";
    private final static int DATABASE_VERSION = 1;

    /*
    * Table Name Definition
    */
    private final static String TABLE_ROOMINFO = "tbl_room";
        public final static String FIELD_ID = "uid";
        public final static String FIELD_ROOMNAME = "roomname";
        public final static String FIELD_IMAGEPATH = "imagepath";;

    public SQLiteDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql="Create table " + TABLE_ROOMINFO + "( " + FIELD_ID + " integer primary key autoincrement,"
                + FIELD_ROOMNAME + " text, "
                + FIELD_IMAGEPATH + " text"
                + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String sqlQuery = "DROP TABLE IF EXISTS " + DATABASE_NAME;
        db.execSQL(sqlQuery);

        onCreate(db);
    }

    public int getRows()
    {
        ArrayList<STRoomInfo> arrData = new ArrayList<STRoomInfo>();
        arrData = getDataList();

        return arrData.size();
    }

    public ArrayList<STRoomInfo> getDataList(  )
    {
        Cursor cursor = null;
        ArrayList<STRoomInfo> arrData = new ArrayList<STRoomInfo>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.query(TABLE_ROOMINFO,
                new String[] { FIELD_ID, FIELD_ROOMNAME, FIELD_IMAGEPATH},
                "1=1",
                null,
                null,
                null,
                FIELD_ID + " desc",
                null);

            if (cursor != null)
            {
                cursor.moveToFirst();

                do
                {
                    try {
                        STRoomInfo stRoomInfo = new STRoomInfo();
                        stRoomInfo.uid = cursor.getInt(cursor.getColumnIndex(SQLiteDBHelper.FIELD_ID));
                        stRoomInfo.roomName = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.FIELD_ROOMNAME));
                        stRoomInfo.imagePath = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.FIELD_IMAGEPATH));

                        arrData.add(stRoomInfo);

                    } catch (Exception ex) { }
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            arrData = new ArrayList<STRoomInfo>();
        }

        return arrData;
    }

    public long saveRoomInfo(String roomName, String imagePath)
    {
        long row = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(FIELD_ROOMNAME, roomName);
            cv.put(FIELD_IMAGEPATH, imagePath);
            row = db.insert(TABLE_ROOMINFO, null, cv);
        } catch (Exception ex) {
            row = 0;
        }

        return row;
    }

    /*
    public long updatePdfData(int pdfid, String iconpath, String localpath, String remotepath)
    {
        long row = 0;

        SQLiteDatabase db=this.getWritableDatabase();

        try {
            String where = FIELD_PDFID + "=?";
            String[] whereValue={Integer.toString(pdfid)};
            ContentValues cv=new ContentValues();
            cv.put(FIELD_LOCALPATH, localpath);
            cv.put(FIELD_ICONPATH, iconpath);
            cv.put(FIELD_REMOTEPATH, remotepath);
            db.update(TABLE_PDFINFO, cv, where, whereValue);
        } catch(Exception ex) {}

        return row;
    }
    */
    /*
    public boolean deletePdfInfo(int pdfid)
    {
        boolean bRet = true;
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            String where = FIELD_PDFID + "=?";
            String[] where1 = {Integer.toString(pdfid)};
            db.delete(TABLE_PDFINFO, where, where1);
        } catch (Exception ex) {
            bRet = false;
        }

        return bRet;
    }
    */

    public STRoomInfo getRoomInfo(int uid)
    {
        STRoomInfo stRoomInfo = new STRoomInfo();

        Cursor cursor = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.query(TABLE_ROOMINFO,
                    new String[] { FIELD_ID, FIELD_ROOMNAME, FIELD_IMAGEPATH},
                    FIELD_ID + "=?",
                    new String[]{String.valueOf(uid)},
                    null,
                    null,
                    null,
                    null);

            if (cursor != null) {
                cursor.moveToFirst();

                stRoomInfo.uid = uid;
                stRoomInfo.roomName = cursor.getString(cursor.getColumnIndex(FIELD_ROOMNAME));
                stRoomInfo.imagePath = cursor.getString(cursor.getColumnIndex(FIELD_IMAGEPATH));
            }

        } catch (Exception ex) {
            stRoomInfo = new STRoomInfo();
        }

        return stRoomInfo;
    }
}
