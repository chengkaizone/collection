package com.cheng.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;

public class PictureUtil {
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
		// 该矩阵对象不会缩放主要是颠倒位图
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		// 根据原始位图创建位图对象；我们仅仅只要原始位图底部的一半---这一步是将颠倒的位图去一半出来
		// 这一步是先裁剪在颠倒从x0坐标开始裁剪width个像素点；从yheight/2开始裁剪height/2个像素点
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);
		// 创建一个新的位图（但是是原始位图的1.5倍高）
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);
		// 根据原始位图+间隙+反射位图创建一个足够大的画布
		Canvas canvas = new Canvas(bitmapWithReflection);
		// 画原始位图
		canvas.drawBitmap(originalImage, 0, 0, null);
		// 画间隙
		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
		// 画倒影
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		Paint paint = new Paint();
		// 为线性渐变设置平铺模式
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		// 为画笔设置线性渐变
		paint.setShader(shader);
		// 为画笔设置滤色效果
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		// 在一个矩形区域中使用渐变效果
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		// 返回含有倒影的位图
		return bitmapWithReflection;
	}
}
