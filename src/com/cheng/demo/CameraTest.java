package com.cheng.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * ʹ���������Ҫ����Ȩ��
 * 
 * @author Administrator
 * 
 */
public class CameraTest extends CommonActivity implements
		SurfaceHolder.Callback {
	private SurfaceView surface;
	private SurfaceHolder surfaceHolder;
	int screenWidth, screenHeight;
	Camera camera;
	WindowManager wm;
	Display dis;
	boolean isPlaying = false;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		// ����������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ����ȫ��
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.camera_main);
		// ���󴰿ڹ������
		wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		dis = wm.getDefaultDisplay();
		screenWidth = dis.getWidth();
		screenHeight = dis.getHeight();
		surface = (SurfaceView) findViewById(R.id.camera_surface);
		// ��ȡsurfaceView��surfaceHolder
		surfaceHolder = surface.getHolder();
		// ���һ���ص���
		surfaceHolder.addCallback(this);
		// ���ò����Լ�ά������
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	private void initCamera() {
		if (!isPlaying) {
			camera = Camera.open();
		}
		if (camera != null && !isPlaying) {
			try {
				Camera.Parameters params = camera.getParameters();
				// ����Ԥ����С
				params.setPreviewSize(screenWidth, screenHeight);
				// ����Ԥ��Ƶ��ÿ�벥��4֡
				params.setPreviewFrameRate(15);
				// ������Ƭ��ʽ
				params.setPictureFormat(PixelFormat.JPEG);
				// ������Ƭ����
				params.set("jpeg-quality", 100);
				// ������Ƭ��С
				params.setPictureSize(screenWidth, screenHeight);
				// �������������2.3.3���������ô˾�
				// camera.setParameters(params);//��ȡ�ķ�������Ҫ������--�������쳣
				// ���ûص�
				camera.setPreviewDisplay(surfaceHolder);
				// ��ʼԤ��
				camera.startPreview();
				// �Զ���
				camera.autoFocus(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			isPlaying = true;
		}
	}

	// ��д��ť�¼�
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_CENTER:
		case KeyEvent.KEYCODE_CAMERA:
			if (camera != null & event.getRepeatCount() == 0) {
				camera.takePicture(null, null, pCallback);
				return true;
			}
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	// ͼƬ�ص�
	PictureCallback pCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
					data.length);
			View saveDialog = getLayoutInflater().inflate(R.layout.camera_save,
					null);
			final EditText et = (EditText) saveDialog
					.findViewById(R.id.camera_phone_name);
			final ImageView img = (ImageView) saveDialog
					.findViewById(R.id.camera_show);
			img.setImageBitmap(bitmap);
			new AlertDialog.Builder(CameraTest.this).setTitle("����ͼƬ")
					.setView(saveDialog)
					.setPositiveButton("����", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							try {
								if (et.getText().toString().trim().equals("")) {
									Toast.makeText(CameraTest.this, "������ͼƬ����",
											2000).show();
								}
								File file = new File(Environment
										.getExternalStorageDirectory(), et
										.getText().toString().trim()
										+ "jpg");
								FileOutputStream out = new FileOutputStream(
										file);
								bitmap.compress(CompressFormat.JPEG, 100, out);
								out.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).setNegativeButton("ȡ��", null).create().show();
			// ����Ԥ��
			camera.stopPreview();
			camera.startPreview();
			isPlaying = true;
		}
	};

	public void surfaceCreated(SurfaceHolder holder) {
		initCamera();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		if (camera != null) {
			if (isPlaying) {
				// ֹͣԤ��
				camera.stopPreview();
				isPlaying = false;
			}
			camera.release();
			camera = null;
		}
	}
}
