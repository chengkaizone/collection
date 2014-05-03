package com.cheng.util;

import android.app.Activity;
import android.content.Context;
import android.view.Display;

public class DisplayUtil {

	public static Display getDisplay(Context context) {
		return ((Activity) context).getWindowManager().getDefaultDisplay();
	}

	public static int getDisWidth(Context context) {
		return getDisplay(context).getWidth();
	}

	public static int getDisHeight(Context context) {
		return getDisplay(context).getHeight();
	}
}
