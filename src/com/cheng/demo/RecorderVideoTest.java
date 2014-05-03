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
 * ¼����Ƶ��Ҫ���4��Ȩ�޴洢��Ȩ�ޣ�������ʹ�����������Ƶ¼��Ȩ�� ���������ģ����������û���⣻ ������������׳��쳣--ԭ��--ˢ�����ǹٷ���rom��
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
		// �����Լ���ά��������
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// ���÷ֱ��ʴ�С
		holder.setFixedSize(surface.getWidth(), surface.getHeight());
		// ���ñ�����Ļ���ڿ���״̬
		holder.setKeepScreenOn(true);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.recordervideo_record:
			// �ж��Ƿ���sd
			if (!Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED)) {
				Toast.makeText(RecorderVideoTest.this, "sd��������", 2000).show();
				return;
			}
			if (!isRecording) {
				initRecorder();
			}
			break;
		case R.id.recordervideo_stop:
			// ֹͣ¼����Ƶ
			if (isRecording) {
				stopRecorder();
				Toast.makeText(RecorderVideoTest.this, "��Ƶ�ѱ��棡", 2000).show();
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
			// ����¼����
			recorder = new MediaRecorder();
			recorder.reset();
			// ��������Դ---��˷�Ϊ����Դ
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			// ���ô�����ͷ�ɼ�¼��---�������������ͷ
			recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			// ������Ƶ�����ʽ(������������Ƶ�����¼�����֮ǰ����)---mp4��ʽ
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			// �������������ʽ
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			// ������Ƶ�����ʽ
			recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
			// ������Ƶ���
			recorder.setVideoSize(surface.getWidth(), surface.getHeight());
			// ����ÿ����ʾ��֡��--4֡
			recorder.setVideoFrameRate(15);
			// �����������--��λ����
			recorder.setMaxDuration(100000);
			// ��������ļ�·��
			recorder.setOutputFile(videoFile.getAbsolutePath());
			// ����ʹ��surface��Ԥ����Ƶ
			recorder.setPreviewDisplay(holder.getSurface());
			// �����ڵ���start����֮ǰ����
			recorder.prepare();
			// ������prepare֮����ã������׳��Ƿ������쳣���˷������õ��Ǳ��ط���
			recorder.start();
			System.out.println("----recording----");
			start.setEnabled(false);
			stop.setEnabled(true);
			isRecording = true;
		} catch (Exception e) {
			Toast.makeText(RecorderVideoTest.this, "¼����Ƶ�쳣���ֻ�Ӳ����֧��", 2000)
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
