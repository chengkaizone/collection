package com.cheng.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.cheng.demo.R;

public class SkinManager {
	/**
	 * Ƥ������skin:1 �̹� 2���� 3��ָ 4ǳ�� 5������
	 * 
	 * @param context
	 * @param skin
	 * @return
	 */
	public static Drawable getDefDrawable(Context context, int skin) {
		if (skin > 4) {
			skin = 4;
		} else if (skin < 0) {
			skin = 0;
		}
		Drawable draw = null;
		switch (skin) {
		case 0:
			draw = context.getResources().getDrawable(R.drawable.skin5);
			break;
		case 1:
			draw = context.getResources().getDrawable(R.drawable.skin2);
			break;
		case 2:
			draw = context.getResources().getDrawable(R.drawable.skin3);
			break;
		case 3:
			draw = context.getResources().getDrawable(R.drawable.skin4);
			break;
		case 4:
			draw = context.getResources().getDrawable(R.drawable.skin1);
			break;
		}
		return draw;
	}
}
