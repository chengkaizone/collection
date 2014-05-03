package com.cheng.demo;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 需要添加一个权限---录制声音
 * 
 * @author Administrator
 * 
 */
public class RecorderTest extends CommonActivity implements OnClickListener {
	private Button start, stop;
	File soundFile;
	MediaRecorder recorder;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.recorder_main);
		start = (Button) findViewById(R.id.recorder_start);
		stop = (Button) findViewById(R.id.recorder_stop);
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		recorder = new MediaRecorder();
		stop.setEnabled(false);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.recorder_start:
			if (!Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED)) {
				Toast.makeText(RecorderTest.this, "sd卡不存在", 2000).show();
				return;
			}
			try {
				soundFile = new File(Environment.getExternalStorageDirectory()
						.getCanonicalFile()
						+ "/"
						+ System.currentTimeMillis()
						+ ".amr");
				// 设置声音来源
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				// 设置声音输出格式
				recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				// 设置声音编码
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				// 设置保存路径---使用绝对路径
				recorder.setOutputFile(soundFile.getAbsolutePath());
				// 准备录音
				recorder.prepare();
				// 开始录音
				recorder.start();
				start.setEnabled(false);
				stop.setEnabled(true);
				Toast.makeText(RecorderTest.this, "正在录音...", 2000).show();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case R.id.recorder_stop:
			if (soundFile != null && soundFile.exists()) {
				// 停止录音
				recorder.stop();
				// 释放资源
				recorder.release();
				// 方便回收资源
				recorder = null;
				stop.setEnabled(false);
				start.setEnabled(true);
				Toast.makeText(RecorderTest.this, "结束录音!已保存录音!", 2000).show();
			}
			break;
		}
	}
}
