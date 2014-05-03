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
 * 使用照相机需要三个权限
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
		// 请求无主题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.camera_main);
		// 请求窗口管理服务
		wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		dis = wm.getDefaultDisplay();
		screenWidth = dis.getWidth();
		screenHeight = dis.getHeight();
		surface = (SurfaceView) findViewById(R.id.camera_surface);
		// 获取surfaceView的surfaceHolder
		surfaceHolder = surface.getHolder();
		// 添加一个回调期
		surfaceHolder.addCallback(this);
		// 设置不由自己维护缓冲
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	private void initCamera() {
		if (!isPlaying) {
			camera = Camera.open();
		}
		if (camera != null && !isPlaying) {
			try {
				Camera.Parameters params = camera.getParameters();
				// 设置预览大小
				params.setPreviewSize(screenWidth, screenHeight);
				// 设置预览频率每秒播放4帧
				params.setPreviewFrameRate(15);
				// 设置照片格式
				params.setPictureFormat(PixelFormat.JPEG);
				// 设置照片质量
				params.set("jpeg-quality", 100);
				// 设置照片大小
				params.setPictureSize(screenWidth, screenHeight);
				// 设置照相机参数2.3.3后无须设置此句
				// camera.setParameters(params);//获取的方法不需要在设置--否则会出异常
				// 设置回调
				camera.setPreviewDisplay(surfaceHolder);
				// 开始预览
				camera.startPreview();
				// 自动获焦
				camera.autoFocus(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			isPlaying = true;
		}
	}

	// 重写按钮事件
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

	// 图片回调
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
			new AlertDialog.Builder(CameraTest.this).setTitle("保存图片")
					.setView(saveDialog)
					.setPositiveButton("保存", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							try {
								if (et.getText().toString().trim().equals("")) {
									Toast.makeText(CameraTest.this, "请输入图片名称",
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
					}).setNegativeButton("取消", null).create().show();
			// 重新预览
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
				// 停止预览
				camera.stopPreview();
				isPlaying = false;
			}
			camera.release();
			camera = null;
		}
	}
}
