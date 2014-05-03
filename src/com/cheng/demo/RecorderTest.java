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
 * ��Ҫ���һ��Ȩ��---¼������
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
				Toast.makeText(RecorderTest.this, "sd��������", 2000).show();
				return;
			}
			try {
				soundFile = new File(Environment.getExternalStorageDirectory()
						.getCanonicalFile()
						+ "/"
						+ System.currentTimeMillis()
						+ ".amr");
				// ����������Դ
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				// �������������ʽ
				recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				// ������������
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				// ���ñ���·��---ʹ�þ���·��
				recorder.setOutputFile(soundFile.getAbsolutePath());
				// ׼��¼��
				recorder.prepare();
				// ��ʼ¼��
				recorder.start();
				start.setEnabled(false);
				stop.setEnabled(true);
				Toast.makeText(RecorderTest.this, "����¼��...", 2000).show();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case R.id.recorder_stop:
			if (soundFile != null && soundFile.exists()) {
				// ֹͣ¼��
				recorder.stop();
				// �ͷ���Դ
				recorder.release();
				// ���������Դ
				recorder = null;
				stop.setEnabled(false);
				start.setEnabled(true);
				Toast.makeText(RecorderTest.this, "����¼��!�ѱ���¼��!", 2000).show();
			}
			break;
		}
	}
}
