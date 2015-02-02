package com.airapp.utils.SmartImageView;

import android.content.Context;
import android.graphics.*;
import android.os.Debug;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmartImageView extends ImageView {
	private static final int LOADING_THREADS = 4;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(LOADING_THREADS);

	private SmartImageTask currentTask;

	public boolean isRoundRect = false;
	public int roundSize = 0;

	public SmartImageView(Context context) {
		super(context);
	}

	public SmartImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SmartImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	// Helpers to set image by URL
	public void setImageUrl(String url) {
		setImage(new WebImage(url));
	}

	public void setImageUrl(String url, SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url), completeListener);
	}

	public void setImageUrl(String url, final Integer fallbackResource) {
		setImage(new WebImage(url), fallbackResource);
	}

	public void setImageUrl(String url, final Integer fallbackResource, SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url), fallbackResource, completeListener);
	}

	public void setImageUrl(String url, final Integer fallbackResource, final Integer loadingResource) {
		setImage(new WebImage(url), fallbackResource, loadingResource);
	}

	public void setImageUrl(String url, final Integer fallbackResource, final Integer loadingResource, SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url), fallbackResource, loadingResource, completeListener);
	}


	// Helpers to set image by contact address book id
	public void setImageContact(long contactId) {
		setImage(new ContactImage(contactId));
	}

	public void setImageContact(long contactId, final Integer fallbackResource) {
		setImage(new ContactImage(contactId), fallbackResource);
	}

	public void setImageContact(long contactId, final Integer fallbackResource, final Integer loadingResource) {
		setImage(new ContactImage(contactId), fallbackResource, fallbackResource);
	}


	// Set image using SmartImage object
	public void setImage(final SmartImage image) {
		setImage(image, null, null, null);
	}

	public void setImage(final SmartImage image, final SmartImageTask.OnCompleteListener completeListener) {
		setImage(image, null, null, completeListener);
	}

	public void setImage(final SmartImage image, final Integer fallbackResource) {
		setImage(image, fallbackResource, fallbackResource, null);
	}

	public void setImage(final SmartImage image, final Integer fallbackResource, SmartImageTask.OnCompleteListener completeListener) {
		setImage(image, fallbackResource, fallbackResource, completeListener);
	}

	public void setImage(final SmartImage image, final Integer fallbackResource, final Integer loadingResource) {
		setImage(image, fallbackResource, loadingResource, null);
	}

	public static void logHeap()
	{
		Double allocated = new Double(Debug.getNativeHeapAllocatedSize())/new Double((1048576));
		Double available = new Double(Debug.getNativeHeapSize())/1048576.0;
		Double free = new Double(Debug.getNativeHeapFreeSize())/1048576.0;
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
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

	public void setImage(final SmartImage image, final Integer fallbackResource, final Integer loadingResource, final SmartImageTask.OnCompleteListener completeListener) {
		logHeap();
		// Set a loading resource
		if(loadingResource != null){
			setImageResource(loadingResource);
		}

		// Cancel any existing tasks for this image view
		if(currentTask != null)
		{
			currentTask.cancel();
			currentTask = null;
		}

		// Set up the new task
		currentTask = new SmartImageTask(getContext(), image);
		currentTask.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler()
		{
			@Override
			public void onComplete(Bitmap bitmap) {
				if(bitmap != null)
				{
					if (isRoundRect)
						setImageBitmap(getRoundedCornerBitmap(bitmap, roundSize));
					else
						setImageBitmap(bitmap);
				}
				else
				{
					// Set fallback resource
					if(fallbackResource != null) {
						setImageResource(fallbackResource);
					}
				}

				setScaleType(ScaleType.FIT_XY);

				if(completeListener != null){
					completeListener.onComplete();
				}
			}
		});

		// Run the task in a threadpool
		threadPool.execute(currentTask);
	}

	public static void cancelAllTasks() {
		threadPool.shutdownNow();
		threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
	}
}