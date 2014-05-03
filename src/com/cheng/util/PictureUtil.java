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
	 * ��ӵ�Ӱ��ԭ���ȷ�תͼƬ�����ϵ��·Ŵ�͸����
	 * 
	 * @param originalImage
	 * @return
	 */
	public static Bitmap createReflectedImage(Bitmap originalImage) {
		// ָ��ԭʼλͼ�뷴��λͼ֮��ļ�϶
		final int reflectionGap = 4;
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		// �þ�����󲻻�������Ҫ�ǵߵ�λͼ
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		// ����ԭʼλͼ����λͼ�������ǽ���ֻҪԭʼλͼ�ײ���һ��---��һ���ǽ��ߵ���λͼȥһ�����
		// ��һ�����Ȳü��ڵߵ���x0���꿪ʼ�ü�width�����ص㣻��yheight/2��ʼ�ü�height/2�����ص�
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);
		// ����һ���µ�λͼ��������ԭʼλͼ��1.5���ߣ�
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);
		// ����ԭʼλͼ+��϶+����λͼ����һ���㹻��Ļ���
		Canvas canvas = new Canvas(bitmapWithReflection);
		// ��ԭʼλͼ
		canvas.drawBitmap(originalImage, 0, 0, null);
		// ����϶
		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
		// ����Ӱ
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		Paint paint = new Paint();
		// Ϊ���Խ�������ƽ��ģʽ
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		// Ϊ�����������Խ���
		paint.setShader(shader);
		// Ϊ����������ɫЧ��
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		// ��һ������������ʹ�ý���Ч��
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		// ���غ��е�Ӱ��λͼ
		return bitmapWithReflection;
	}
}
