package com.airapp.breeze;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.*;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.FloatMath;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.airapp.utils.ClipView;
import com.airapp.utils.Common;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectPhotoActivity extends BaseActivity
{
	private RelativeLayout maskLayout = null;
	private ClipView clipView = null;
	private ImageView imgSel = null;
	private Button btnConfirm = null;

	private static Uri fileUri = null;

	public static int IMAGE_WIDTH = 500;
	public static int IMAGE_HEIGHT = 500;

	public static int REQCODE_TAKE_PHOTO = 0;
	public static int REQCODE_SELECT_GALLERY = 1;

	public static String szRetCode = "RET";
	public static String szRetPath = "PATH";

	public static int nRetSuccess = 1;
	public static int nRetCancelled = 0;
	public static int nRetFail = -1;

	private String photo_path = "";
	private Bitmap bmpPhoto = null;

	private String resPath = "";
	private boolean needCrop = false;

	private int statusBarHeight = 0;
	private int titleBarHeight = 0;

	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();

	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;

	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_selphoto);

		initVariables();
		initControls();

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = getOutputPhotoFile();
		if (file == null) {
			//Global.showAdvancedToast(SelectPhotoActivity.this, getResources().getString(R.string.STR_CANNOT_TAKEPHOTO), Gravity.CENTER);
		} else {
			fileUri = Uri.fromFile(file);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			startActivityForResult(intent, REQCODE_TAKE_PHOTO);
		}
	}

	@Override
	protected int getParentLayoutID() {
		return R.id.parent_layout;
	}

	private void initVariables()
	{
		REQCODE_TAKE_PHOTO = 0;
		REQCODE_SELECT_GALLERY = 1;

		szRetCode = "RET";
		szRetPath = "PATH";

		nRetSuccess = 1;
		nRetCancelled = 0;
		nRetFail = -1;

		photo_path = "";
	}

	public void initControls()
	{
		maskLayout = (RelativeLayout)findViewById(R.id.crop_layout);
		maskLayout.setVisibility(View.GONE);

		imgSel = (ImageView)findViewById(R.id.img_sel);
		imgSel.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView view = (ImageView) v;

				// Handle touch events here...
				switch (event.getAction() & MotionEvent.ACTION_MASK)
				{
					case MotionEvent.ACTION_DOWN:
						savedMatrix.set(matrix);
						start.set(event.getX(), event.getY());

						mode = DRAG;
						break;
					case MotionEvent.ACTION_POINTER_DOWN:
						oldDist = spacing(event);

						if (oldDist > 10f)
						{
							savedMatrix.set(matrix);
							midPoint(mid, event);
							mode = ZOOM;
						}
						break;
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_POINTER_UP:
						mode = NONE;

						break;
					case MotionEvent.ACTION_MOVE:
						if (mode == DRAG) {
							matrix.set(savedMatrix);
							matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
						} else if (mode == ZOOM) {
							float newDist = spacing(event);
							if (newDist > 10f)
							{
								matrix.set(savedMatrix);
								float scale = newDist / oldDist;
								matrix.postScale(scale, scale, mid.x, mid.y);
							}
						}
						break;
				}

				view.setImageMatrix(matrix);
				return true;
			}
		});

		clipView = (ClipView)findViewById(R.id.clipview);

		btnConfirm = (Button)findViewById(R.id.btn_confirm);
		btnConfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finishCrop();
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		if (resPath == null || resPath.equals(""))
			return;

		correctBitmap(resPath);

		if (!needCrop) {
			finishActivityWithImage();
		} else {
			try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inDither = true;

				bmpPhoto = BitmapFactory.decodeFile(resPath, options);
                float scale = 0.75F*(float) Common.getScrWidth(SelectPhotoActivity.this)/(float)bmpPhoto.getWidth();
                matrix.setScale(1F, 1F);
                matrix.postScale(scale, scale);
                matrix.postTranslate((float)Common.getScrWidth(SelectPhotoActivity.this)/8F,
                        (float)Common.getScrHeight(SelectPhotoActivity.this)*0.25F - (float)bmpPhoto.getHeight()*scale*0.25F );
                imgSel.setImageMatrix(matrix);
                imgSel.setImageBitmap(bmpPhoto);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			maskLayout.setVisibility(View.VISIBLE);
		}
	}


	private void finishActivityWithImage()
	{
		Intent retIntent = new Intent();
		retIntent.putExtra(szRetCode, nRetSuccess);
		retIntent.putExtra(szRetPath, resPath);
		setResult(RESULT_OK, retIntent);
		finish();
	}


	/** Determine the space between the first two fingers */
	private float spacing(MotionEvent event)
	{
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event)
	{
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	private void finishCrop()
	{
		Bitmap finalBmp = getBitmap();

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(resPath);
			finalBmp.compress(Bitmap.CompressFormat.PNG, 100, out);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		finishActivityWithImage();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			cancelWithData();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK)
			return;

		if (requestCode == REQCODE_TAKE_PHOTO)
		{
			Uri photoUri = null;

			if (data == null)
				photoUri = fileUri;
			else
				photoUri = data.getData();

			try
			{
				if (photoUri != null)
				{
					String szPath = photoUri.getPath();
					if (szPath == null || szPath.equals(""))
					{
						//Global.showAdvancedToast(SelectPhotoActivity.this, getResources().getString(R.string.STR_LOADING_IMAGE_FAILED), Gravity.CENTER);
					}
					else
					{
						photo_path = szPath;
					}
				}
				else
				{
					photo_path = fileUri.getPath();
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				//Global.showAdvancedToast(SelectPhotoActivity.this, getResources().getString(R.string.STR_TAKING_PHOTO_FAILED), Gravity.CENTER);
			}
		}

		if (photo_path != null && !photo_path.equals(""))
		{
			resPath = photo_path;
		}
	}

	private File getOutputPhotoFile()
	{
		File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getPackageName());
		if (!directory.exists())
		{
			if (!directory.mkdirs()) {		  // No sd card.
				directory = null;
			}
		}

		if (directory == null)
		{
			directory = getDir(getPackageName(), Context.MODE_PRIVATE);
			if (!directory.exists())
			{
				if (!directory.mkdir())
					directory = null;
			}
		}

		if (directory == null)
			return null;

		return new File(directory.getPath() + File.separator + "IMG_" + Long.toString(System.currentTimeMillis()) + ".jpg");
	}


	private void cancelWithData()
	{
		Intent returnIntent = new Intent();
		setResult(RESULT_CANCELED, returnIntent);
		SelectPhotoActivity.this.finish();
	}

	public static int getImageOrientation(String imagePath){
		int nAngle = 0;
		try {
			File imageFile = new File(imagePath);
			ExifInterface exif = new ExifInterface(
					imageFile.getAbsolutePath());
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_270:
					nAngle = 270;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					nAngle = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_90:
					nAngle = 90;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return nAngle;
	}


	// Rotate image clockwise
	public static Bitmap rotateImage(String pathToImage, int nAngle) {
		// 2. rotate matrix by post concatination
		Matrix matrix = new Matrix();
		matrix.postRotate(nAngle);

		// 3. create Bitmap from rotated matrix
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        Bitmap sourceBitmap = BitmapFactory.decodeFile(pathToImage, options);
		return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
	}


	private void correctBitmap(String szPath)
	{
		int nAngle = getImageOrientation(szPath);
		if (nAngle == 0)				// Image is correct. No need to rotate
			return;

		Bitmap bmpRot = rotateImage(szPath, nAngle);
		FileOutputStream ostream = null;

		try {
			File file = new File(szPath);
			file.deleteOnExit();

			ostream = new FileOutputStream(file);
			bmpRot.compress(Bitmap.CompressFormat.JPEG, 50, ostream);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (ostream != null) {
				try { ostream.close(); } catch (Exception ex) { ex.printStackTrace(); }
			}
		}
	}

	private Bitmap getBitmap()
	{
		getBarHeight();
		Bitmap screenShoot = takeScreenShot();

		int width = clipView.getWidth();
		int height = clipView.getHeight();

		Bitmap finalBitmap = Bitmap.createBitmap(screenShoot,
				(width - height / 2) / 2, height / 4 + titleBarHeight + statusBarHeight,
				height / 2,
				height / 2);

		return finalBitmap;
	}

	private void getBarHeight()
	{
		Rect frame = new Rect();
		this.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		statusBarHeight = frame.top;

		int contenttop = this.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
		titleBarHeight = contenttop - statusBarHeight;
	}

	private Bitmap takeScreenShot()
	{
		View view = this.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		return view.getDrawingCache();
	}


	public enum RealPathUtil {
		INSTANCE;

		public static String getRealPathFromURI(Context context, Uri uri)
		{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
			{
				return getRealPathFromURI_BelowAPI11(context, uri);
			}
			else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
			{
				return getRealPathFromURI_API11to18(context, uri);
			}
			else
			{
				return getRealPathFromURI_API19(context, uri);
			}
		}

		@SuppressLint("NewApi")
		public static String getRealPathFromURI_API19(Context context, Uri uri) {
			String filePath = "";
			String wholeID = "";

			try {
				wholeID = DocumentsContract.getDocumentId(uri);
			} catch (Exception ex) {
				ex.printStackTrace();           // Android 4.4.2 can occur this exception.

				return getRealPathFromURI_API11to18(context, uri);
			}

			// Split at colon, use second item in the array
			String id = wholeID.split(":")[1];

			String[] column = { MediaStore.Images.Media.DATA };

			// where id is equal to
			String sel = MediaStore.Images.Media._ID + "=?";

			Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					column, sel, new String[]{ id }, null);

			int columnIndex = cursor.getColumnIndex(column[0]);

			if (cursor.moveToFirst()) {
				filePath = cursor.getString(columnIndex);
			}

			cursor.close();
			return filePath;
		}


		@SuppressLint("NewApi")
		public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
			String[] proj = { MediaStore.Images.Media.DATA };
			String result = null;

			CursorLoader cursorLoader = new CursorLoader(
					context,
					contentUri, proj, null, null, null);
			Cursor cursor = cursorLoader.loadInBackground();

			if(cursor != null){
				int column_index =
						cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				result = cursor.getString(column_index);
			}
			return result;
		}

		public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
			int column_index
					= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
	}
}
