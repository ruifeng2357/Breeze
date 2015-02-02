package com.airapp.utils;

import java.util.Objects;

public class Logger {
	public static final String TAG = "Breeze";
	public static void logDebug(String tag, String format, Object ...args) {
		android.util.Log.d(TAG + " : " + tag, String.format(format, args));
	}

	public static void logError(String tag, String format, Object ...args) {
		android.util.Log.e(TAG + " : " + tag, String.format(format, args));
	}

	public static void logInfo (String tag, String format, Objects ...args) {
		android.util.Log.i(TAG + " : " + tag, String.format(format, args));
	}
}
