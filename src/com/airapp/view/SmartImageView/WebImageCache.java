package com.airapp.view.SmartImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;

public class WebImageCache {
	private ConcurrentHashMap<String, SoftReference<Bitmap>> memoryCache;
	private String diskCachePath;
	private boolean diskCacheEnabled = false;
	private ExecutorService writeThread;

	public WebImageCache(Context context) {
		// Set up in-memory cache store
		memoryCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>();

		Context appContext = context.getApplicationContext();
		diskCachePath = getExternalCacheDir(context).getPath();

		File outFile = new File(diskCachePath);
		outFile.mkdirs();

		diskCacheEnabled = outFile.exists();

		// Set up threadpool for image fetching tasks
		writeThread = Executors.newSingleThreadExecutor();
	}

	public static boolean hasExternalCacheDir()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static File getExternalCacheDir(Context context) {
		if (hasExternalCacheDir()) {
			return context.getExternalCacheDir();
		}

		final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";

		return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
	}

	public Bitmap get(final String url) {
		Bitmap bitmap = null;

		// Check for image in memory
		bitmap = getBitmapFromMemory(url);

		// Check for image on disk cache
		if(bitmap == null) {
			bitmap = getBitmapFromDisk(url);

			// Write bitmap back into memory cache
			if(bitmap != null) {
				cacheBitmapToMemory(url, bitmap);
			}
		}

		return bitmap;
	}

	public void put(String url, Bitmap bitmap) {
		cacheBitmapToMemory(url, bitmap);
		cacheBitmapToDisk(url, bitmap);
	}

	public void remove(String url) {
		if(url == null){
			return;
		}

		// Remove from memory cache
		memoryCache.remove(getCacheKey(url));

		// Remove from file cache
		File f = new File(diskCachePath, getCacheKey(url));
		if(f.exists() && f.isFile()) {
			f.delete();
		}
	}

	public void clear() {
		// Remove everything from memory cache
		memoryCache.clear();

		// Remove everything from file cache
		File cachedFileDir = new File(diskCachePath);
		if(cachedFileDir.exists() && cachedFileDir.isDirectory()) {
			File[] cachedFiles = cachedFileDir.listFiles();
			for(File f : cachedFiles) {
				if(f.exists() && f.isFile()) {
					f.delete();
				}
			}
		}
	}

	private void cacheBitmapToMemory(final String url, final Bitmap bitmap) {
		memoryCache.put(getCacheKey(url), new SoftReference<Bitmap>(bitmap));
	}

	private void cacheBitmapToDisk(final String url, final Bitmap bitmap) {
		writeThread.execute(new Runnable() {
			@Override
			public void run() {
				if(diskCacheEnabled) {
					BufferedOutputStream ostream = null;
					try {
						ostream = new BufferedOutputStream(new FileOutputStream(new File(diskCachePath, getCacheKey(url))), 2*1024);
						bitmap.compress(CompressFormat.PNG, 100, ostream);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} finally {
						try {
							if(ostream != null) {
								ostream.flush();
								ostream.close();
							}
						} catch (IOException e) {}
					}
				}
			}
		});
	}

	private Bitmap getBitmapFromMemory(String url) {
		Bitmap bitmap = null;
		SoftReference<Bitmap> softRef = memoryCache.get(getCacheKey(url));
		if(softRef != null){
			bitmap = softRef.get();
		}

		return bitmap;
	}

	private Bitmap getBitmapFromDisk(String url) {
		Bitmap bitmap = null;
		if(diskCacheEnabled){
			String filePath = getFilePath(url);
			File file = new File(filePath);
			if(file.exists()) {
				bitmap = BitmapFactory.decodeFile(filePath);
			}
		}
		return bitmap;
	}

	private String getFilePath(String url) {
		return diskCachePath + getCacheKey(url);
	}

	private String getCacheKey(String url) {
		if(url == null){
			throw new RuntimeException("Null url passed in");
		} else {
			return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
		}
	}
}
