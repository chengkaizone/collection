package com.cheng.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;

public class PhotoUtil {
	/**
	 * 为原始位图添加倒影效果
	 * 
	 * @param src
	 * @return
	 */
	public static Bitmap createReflectImage(Bitmap src) {
		// 指定原始位图与反射位图之间的间隙
		final int GAP = 5;
		int width = src.getWidth();
		int height = src.getHeight();
		// 该矩阵对象不会缩放主要是颠倒位图
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		// 根据原始位图创建位图对象；我们仅仅只要原始位图底部的一半---这一步是将颠倒的位图去一半出来
		// 这一步是先裁剪在颠倒从x0坐标开始裁剪width个像素点；从yheight/2开始裁剪height/2个像素点
		Bitmap reflect = Bitmap.createBitmap(src, 0, height / 2, width,
				height / 2, matrix, false);
		// 创建一个新的位图（但是是原始位图的1.5倍高+间隙高度）
		Bitmap reflectBitmap = Bitmap.createBitmap(width,
				height + reflect.getHeight() + GAP, Config.ARGB_8888);
		// 根据原始位图+间隙+反射位图创建一个足够大的画布---画布完成后的位图对象就是传入的参数
		Canvas canvas = new Canvas(reflectBitmap);
		// 画原始位图
		canvas.drawBitmap(src, 0, 0, null);
		// 画矩形间隙
		canvas.drawRect(0, height, width, height + GAP, new Paint());
		// 画倒影
		canvas.drawBitmap(reflect, 0, height + GAP, null);
		Paint shaderPaint = new Paint();
		// 为线性渐变设置平铺模式
		LinearGradient shader = new LinearGradient(0, height, 0,
				reflectBitmap.getHeight(), 0xccffffff, 0x00ffffff,
				TileMode.CLAMP);
		// 为画笔设置线性渐变
		shaderPaint.setShader(shader);
		// 为画笔设置滤色效果
		shaderPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// 在一个矩形区域中使用渐变效果---这会作用在倒影图上
		canvas.drawRect(0, height, width, reflectBitmap.getHeight(),
				shaderPaint);
		// 返回含有倒影的位图
		return reflectBitmap;
	}
}