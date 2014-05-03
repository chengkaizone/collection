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
	 * Ϊԭʼλͼ��ӵ�ӰЧ��
	 * 
	 * @param src
	 * @return
	 */
	public static Bitmap createReflectImage(Bitmap src) {
		// ָ��ԭʼλͼ�뷴��λͼ֮��ļ�϶
		final int GAP = 5;
		int width = src.getWidth();
		int height = src.getHeight();
		// �þ�����󲻻�������Ҫ�ǵߵ�λͼ
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		// ����ԭʼλͼ����λͼ�������ǽ���ֻҪԭʼλͼ�ײ���һ��---��һ���ǽ��ߵ���λͼȥһ�����
		// ��һ�����Ȳü��ڵߵ���x0���꿪ʼ�ü�width�����ص㣻��yheight/2��ʼ�ü�height/2�����ص�
		Bitmap reflect = Bitmap.createBitmap(src, 0, height / 2, width,
				height / 2, matrix, false);
		// ����һ���µ�λͼ��������ԭʼλͼ��1.5����+��϶�߶ȣ�
		Bitmap reflectBitmap = Bitmap.createBitmap(width,
				height + reflect.getHeight() + GAP, Config.ARGB_8888);
		// ����ԭʼλͼ+��϶+����λͼ����һ���㹻��Ļ���---������ɺ��λͼ������Ǵ���Ĳ���
		Canvas canvas = new Canvas(reflectBitmap);
		// ��ԭʼλͼ
		canvas.drawBitmap(src, 0, 0, null);
		// �����μ�϶
		canvas.drawRect(0, height, width, height + GAP, new Paint());
		// ����Ӱ
		canvas.drawBitmap(reflect, 0, height + GAP, null);
		Paint shaderPaint = new Paint();
		// Ϊ���Խ�������ƽ��ģʽ
		LinearGradient shader = new LinearGradient(0, height, 0,
				reflectBitmap.getHeight(), 0xccffffff, 0x00ffffff,
				TileMode.CLAMP);
		// Ϊ�����������Խ���
		shaderPaint.setShader(shader);
		// Ϊ����������ɫЧ��
		shaderPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// ��һ������������ʹ�ý���Ч��---��������ڵ�Ӱͼ��
		canvas.drawRect(0, height, width, reflectBitmap.getHeight(),
				shaderPaint);
		// ���غ��е�Ӱ��λͼ
		return reflectBitmap;
	}
}