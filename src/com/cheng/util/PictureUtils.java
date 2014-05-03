package com.cheng.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public class PictureUtils {
	/**
	 * 添加倒影，原理，先翻转图片，由上到下放大透明度
	 * 
	 * @param originalImage
	 * @return
	 */
	public static Bitmap createReflectedImage(Bitmap originalImage) {
		// 指定原始位图与反射位图之间的间隙
		final int reflectionGap = 4;
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		// 该矩阵对象不会缩放但是会在y轴上裁剪
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		// 根据原始位图创建位图对象；我们仅仅只要原始位图底部的一半
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);
		// 创建一个新的位图（但是是原始位图的1.5倍高）
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);
		// 创建一个足够大的画布；图片附加间隙在反射位图上
		Canvas canvas = new Canvas(bitmapWithReflection);
		// 画原始位图
		canvas.drawBitmap(originalImage, 0, 0, null);
		// 画间隙
		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
		// 画倒影
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		// 创建线性渐变在倒影上
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		// 为画笔设置线性渐变
		paint.setShader(shader);
		// 设置平移在目标位置
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// 用线性渐变画一个矩形
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		// 返回含有倒影的位图
		return bitmapWithReflection;
	}
}
