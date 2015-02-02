package com.airapp.utils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.airapp.breeze.SelectPhotoActivity;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Common
{
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
	          "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
	          "\\@" +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
	          "(" +
	          "\\." +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
	          ")+"
	      );

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^[+]?[0-9]{10,13}$");
	
	private static Toast g_Toast = null;
	public static void showToast(Context context, String toastStr)
	{
		if ((g_Toast == null) || (g_Toast.getView().getWindowVisibility() != View.VISIBLE))
		{
			g_Toast = Toast.makeText(context, toastStr, Toast.LENGTH_SHORT);
			g_Toast.show();
		}

		return;
	}

	public static boolean isValidEmail(String strEmail)
	{
		return EMAIL_ADDRESS_PATTERN.matcher(strEmail).matches();
	}

    public static boolean isValidPhone(String strPhone)
    {
        return PHONE_NUMBER_PATTERN.matcher(strPhone).matches();
    }

    public static boolean isOnline(Context ctx)
    {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static int getHighQualityValue() { return 0; }
    public static int getLowQualityValue() { return 1; }

    public static void getRoundedCornerBitmap()
    {
        Double allocated = new Double(Debug.getNativeHeapAllocatedSize())/new Double((1048576));
        Double available = new Double(Debug.getNativeHeapSize())/1048576.0;
        Double free = new Double(Debug.getNativeHeapFreeSize())/1048576.0;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
    }

    public static long getAvailableMemorySize(Context app_context)
    {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)app_context.getSystemService(Service.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        return mi.availMem;
    }

    public static boolean isLowMemory(Context app_context)
    {
        if (getAvailableMemorySize(app_context) < 2 * 1024 * 1024)
            return true;

        return false;
    }

    public static void setImageOnButton(Button button, Drawable drawable, int nWidth, int nHeight)
    {
        drawable.setBounds(0, 0, nWidth, nHeight);
        button.setBackgroundDrawable(drawable);
    }

    public static long clearCachedFiles(Context appContext)
    {
        long bytesDeleted = 0;

        File dir = getExternalCacheDir(appContext);
        if (dir == null)
            return 0;

        File[] files = dir.listFiles();
        if (files == null)
            return 0;

        for (File file : files)
        {
            bytesDeleted += file.length();
            file.delete();
        }

        return bytesDeleted;
    }

    public static void openBrowser(Context context, String url)
    {
        try
        {
            if (!url.contains("http://") && !url.contains("https://"))
                url = "http://" + url;

            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browser);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static byte[] Bitmap2Jpeg(Bitmap src)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 50, os);

        byte[] array = os.toByteArray();
        return array;
    }

    public static boolean isExternalStorageRemovable()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
        {
            return Environment.isExternalStorageRemovable();
        }

        return true;
    }

    public static File getExternalCacheDir(Context context) {
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        }

        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";

        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static boolean hasExternalCacheDir()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static int getApiVersion()
    {
        return Build.VERSION.SDK_INT;
    }

    public static int getScrWidth(Context context)
    {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScrHeight(Context context)
    {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
    }

    public static Bitmap loadBmpFromUrl(String szUrl)
    {
        URL url = null;
        Bitmap bmp = null;
        ByteArrayOutputStream stream = null;
        byte[] arrImgData = null;

        try
        {
            url = new URL(szUrl);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return bmp;
    }

    public static int getLineCount(TextView view, String szText, int nWidth)
    {
        int nCharCount = view.getPaint().breakText(szText, 0, szText.length(), true, nWidth, null);
        int nLineCount = szText.length() / nCharCount;
        if (szText.length() > nCharCount * nLineCount)
            nLineCount++;

        return nLineCount;
    }

    public static int fitLineCount(TextView view, String szText, int nWidth)
    {
        int nCharCount = view.getPaint().breakText(szText, 0, szText.length(), true, nWidth, null);
        int nLineCount = szText.length() / nCharCount;
        if (szText.length() > nCharCount * nLineCount)
            nLineCount++;
        view.setLines(nLineCount);
        return nLineCount;
    }

    public static String encodePostBody(Bundle parameters, String boundary) {
        if (parameters == null) return "";
        StringBuilder sb = new StringBuilder();

        for (String key : parameters.keySet())
        {
            sb.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n" + parameters.get(key));
            sb.append("\r\n" + "--" + boundary + "\r\n");
        }

        return sb.toString();
    }

    public static String encodeUrl(Bundle parameters)
    {
        if (parameters == null)
            return "";

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String key : parameters.keySet())
        {
            if (first) first = false; else sb.append("&");
            sb.append(URLEncoder.encode(key) + "=" + URLEncoder.encode(parameters.getString(key)));
        }

        return sb.toString();
    }

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if (s != null)
        {
            String array[] = s.split("&");
            for (String parameter : array)
            {
                String v[] = parameter.split("=");
                // YG: in case param has no value
                if (v.length==2)
                {
                    params.putString(URLDecoder.decode(v[0]), URLDecoder.decode(v[1]));
                }
                else
                {
                    params.putString(URLDecoder.decode(v[0])," ");
                }
            }
        }
        return params;
    }

    private static String read(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

    public static void showKeyboardFromText(EditText editText, Context context)
    {
        InputMethodManager mgr = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboardFromText(EditText editText, Context context)
    {
        InputMethodManager mgr = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels)
    {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static void WriteLog(String logString)
    {
        String strFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        File f = new File(Environment.getExternalStorageDirectory(), "Download/Breeze.log");
        String strLog = sdf.format(new Date()) + "   "  + logString + "\r\n";

        try {
            OutputStream os = new FileOutputStream(f, true);
            os.write(strLog.getBytes());
            os.close();
        } catch (Exception e) {
        }
    }

    public static Bitmap getBitmapFromPath(String szPath) {
        Bitmap bmpPhoto = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(szPath, options);

            if (bitmap != null) {
                int nWidth = bitmap.getWidth(), nHeight = bitmap.getHeight();
                int nScaledWidth = 0, nScaledHeight = 0;
                if (nWidth > nHeight) {
                    nScaledWidth = SelectPhotoActivity.IMAGE_WIDTH;
                    nScaledHeight = nScaledWidth * nHeight / nWidth;
                } else {
                    nScaledHeight = SelectPhotoActivity.IMAGE_HEIGHT;
                    nScaledWidth = nScaledHeight * nWidth / nHeight;
                }

                bmpPhoto = Bitmap.createScaledBitmap(bitmap, nScaledWidth, nScaledHeight, false);

                return bmpPhoto;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
