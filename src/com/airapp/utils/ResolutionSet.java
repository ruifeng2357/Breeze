package com.airapp.utils;

import android.app.Activity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResolutionSet {

	public static float fXpro = 1;
	public static float fYpro = 1;
	public static float fPro  = 1;
	public static int nDesignWidth = 1280;
	public static int nDesignHeight = 720;

	public static ResolutionSet _instance = new ResolutionSet();

	public ResolutionSet() {}

	public void setResolution(int x, int y, boolean isPortrate)
	{
		if (!isPortrate)
			fXpro = (float)x / nDesignWidth;
		else
			fXpro = (float)x / nDesignHeight;
		if (!isPortrate)
			fYpro = (float)y / nDesignHeight;
		else
			fYpro = (float)y / nDesignWidth;
		fPro = Math.min(fXpro, fYpro);
	}

	public void iterateChild(View view) {
		if (view instanceof ViewGroup)
		{
			ViewGroup container = (ViewGroup)view;
			int nCount = container.getChildCount();
			for (int i=0; i<nCount; i++)
			{
				iterateChild(container.getChildAt(i));
			}
		}
		UpdateLayout(view);
	}

	void UpdateLayout(View view)
	{
		LayoutParams lp;
		lp = (LayoutParams) view.getLayoutParams();
		if ( lp == null )
			return;
		if(lp.width > 0)
			lp.width = (int)(lp.width * fXpro + 0.50001);
		if(lp.height > 0)
			lp.height = (int)(lp.height * fYpro + 0.50001);

		int leftPadding = (int)( fXpro * view.getPaddingLeft() );
		int rightPadding = (int)(fXpro * view.getPaddingRight());
		int bottomPadding = (int)(fYpro * view.getPaddingBottom());
		int topPadding = (int)(fYpro * view.getPaddingTop());

		view.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);

		if(lp instanceof ViewGroup.MarginLayoutParams)
		{
			ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams)lp;

			mlp.leftMargin = (int)(mlp.leftMargin * fXpro + 0.50001 );
			mlp.rightMargin = (int)(mlp.rightMargin * fXpro+ 0.50001);
			mlp.topMargin = (int)(mlp.topMargin * fYpro+ 0.50001);
			mlp.bottomMargin = (int)(mlp.bottomMargin * fYpro+ 0.50001);
		}

		if(view instanceof TextView)
		{
			TextView lblView = (TextView)view;
			float txtSize = fPro * lblView.getTextSize();
			lblView.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);
		}
	}

	public static Point getScreenSize(Activity context, boolean isContainNavBar, boolean isPortrait)
	{
		int width = 0, height = 0;
		DisplayMetrics metrics = new DisplayMetrics();
		Display display = context.getWindowManager().getDefaultDisplay();
		Method mGetRawH = null, mGetRawW = null;

		if (isContainNavBar == false)
		{
			int nWidth = display.getWidth(), nHeight = display.getHeight();
			if (!isPortrait)
			{
				if (nWidth > nHeight)
					return new Point(nWidth, nHeight);
				else
					return new Point(nHeight, nWidth);
			}
			else
			{
				if (nWidth > nHeight)
					return new Point(nHeight, nWidth);
				else
					return new Point(nWidth, nHeight);
			}
		}

		try {
			mGetRawH = Display.class.getMethod("getRawHeight");
			mGetRawW = Display.class.getMethod("getRawWidth");

			try {
				width = (Integer) mGetRawW.invoke(display);
				height = (Integer) mGetRawH.invoke(display);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e3) {
			e3.printStackTrace();
		}

		if (width != 0 || height != 0)
		{
			if (!isPortrait)
			{
				if (width > height)
					return new Point(width, height);
				else
					return new Point(height, width);
			}
			else
			{
				if (width > height)
					return new Point(height, width);
				else
					return new Point(width, height);
			}
		}
		else
		{
			WindowManager winManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
			context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int nWidth = metrics.widthPixels, nHeight = metrics.heightPixels;
			if (!isPortrait)
			{
				if (nWidth > nHeight)
					return new Point(nWidth, nHeight);
				else
					return new Point(nHeight, nWidth);
			}
			else
			{
				if (nWidth > nHeight)
					return new Point(nHeight, nWidth);
				else
					return new Point(nWidth, nHeight);
			}
		}
	}
}
