package com.cheng.demo;

import java.io.File;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * ��Ƶ������
 * 
 * @author Administrator
 * 
 */
public class VideoViewTest extends CommonActivity {
	VideoView video;
	MediaController controll;
	// �Ƿ񲥷�������Ƶ
	private boolean flag = false;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		// ��һ���ǹؼ�
		this.getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.video_main);
		video = (VideoView) findViewById(R.id.video_main);
		controll = new MediaController(this);
		File file = new File("/mnt/sdcard/video.3gp");
		// ����������Ƶ
		if (flag) {
			if (file.exists()) {
				video.setVideoPath(file.getAbsolutePath());
				// Ϊ��Ƶ���������ò��ſ���
				video.setMediaController(controll);
				// ���ýӿ�ʵ����
				controll.setMediaPlayer(video);
				// �����ȡ����
				video.requestFocus();
				video.start();
			} else {
				Toast.makeText(VideoViewTest.this,
						"��sd�����ļ����·���video.3gp�ļ����ɲ���", Toast.LENGTH_LONG).show();
			}
		} else {
			try {
				// ����������Ƶ
				Uri uri = Uri.parse("http://192.168.88.21:8080/test/video.3gp");
				video.setVideoURI(uri);
				// Ϊ��Ƶ���������ò��ſ���
				video.setMediaController(controll);
				// ���ýӿ�ʵ����
				controll.setMediaPlayer(video);
				// �����ȡ����
				video.requestFocus();
				video.start();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(VideoViewTest.this, "������Ƶ��ַ������",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
