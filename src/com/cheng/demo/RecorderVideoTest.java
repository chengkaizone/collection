package com.cheng.demo;

import java.io.File;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 录制视频需要添加4个权限存储卡权限（两个）使用照相机和音频录制权限 这个例子在模拟器上运行没问题； 在真机上总是抛出异常--原因--刷机（非官方的rom）
 * 
 * @author Administrator
 * 
 */
public class RecorderVideoTest extends CommonActivity implements
		OnClickListener {
	SurfaceView surface;
	SurfaceHolder holder;
	ImageButton start, stop;
	MediaRecorder recorder;
	File videoFile;
	Camera camera;
	boolean isRecording = false;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.recordervideo_main);
		surface = (SurfaceView) findViewById(R.id.recordervideo_surface);
		start = (ImageButton) findViewById(R.id.recordervideo_record);
		stop = (ImageButton) findViewById(R.id.recordervideo_stop);
		holder = surface.getHolder();
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		stop.setEnabled(false);
		// holder.addCallback(this);
		// 设置自己不维护缓冲区
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// 设置分辨率大小
		holder.setFixedSize(surface.getWidth(), surface.getHeight());
		// 设置保持屏幕处于开启状态
		holder.setKeepScreenOn(true);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.recordervideo_record:
			// 判断是否有sd
			if (!Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED)) {
				Toast.makeText(RecorderVideoTest.this, "sd卡不存在", 2000).show();
				return;
			}
			if (!isRecording) {
				initRecorder();
			}
			break;
		case R.id.recordervideo_stop:
			// 停止录制视频
			if (isRecording) {
				stopRecorder();
				Toast.makeText(RecorderVideoTest.this, "视频已保存！", 2000).show();
			}
			break;
		}
	}

	private void initRecorder() {
		try {
			videoFile = new File(Environment.getExternalStorageDirectory()
					.getCanonicalFile()
					+ "/"
					+ System.currentTimeMillis()
					+ "_video.mp4");
			// 创建录音器
			recorder = new MediaRecorder();
			recorder.reset();
			// 设置声音源---麦克风为声音源
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			// 设置从摄像头采集录像---调用照相机摄像头
			recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			// 设置视频保存格式(必须在设置视频编码和录像编码之前设置)---mp4格式
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			// 设置声音编码格式
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			// 设置视频编码格式
			recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
			// 设置视频宽高
			recorder.setVideoSize(surface.getWidth(), surface.getHeight());
			// 设置每秒显示的帧数--4帧
			recorder.setVideoFrameRate(15);
			// 设置最大期限--单位毫秒
			recorder.setMaxDuration(100000);
			// 设置输出文件路径
			recorder.setOutputFile(videoFile.getAbsolutePath());
			// 设置使用surface来预览视频
			recorder.setPreviewDisplay(holder.getSurface());
			// 必须在调用start方法之前调用
			recorder.prepare();
			// 必须在prepare之后调用；否则抛出非法参数异常；此方法调用的是本地方法
			recorder.start();
			System.out.println("----recording----");
			start.setEnabled(false);
			stop.setEnabled(true);
			isRecording = true;
		} catch (Exception e) {
			Toast.makeText(RecorderVideoTest.this, "录制视频异常！手机硬件不支持", 2000)
					.show();
			e.printStackTrace();
		}
	}

	private void stopRecorder() {
		if (isRecording) {
			recorder.stop();
			recorder.release();
			recorder = null;
			start.setEnabled(true);
			stop.setEnabled(false);
			isRecording = false;
		}
	}
}
