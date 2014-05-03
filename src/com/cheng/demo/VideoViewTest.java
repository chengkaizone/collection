package com.cheng.demo;

import java.io.File;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * 视频播放器
 * 
 * @author Administrator
 * 
 */
public class VideoViewTest extends CommonActivity {
	VideoView video;
	MediaController controll;
	// 是否播放网络视频
	private boolean flag = false;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		// 这一句是关键
		this.getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.video_main);
		video = (VideoView) findViewById(R.id.video_main);
		controll = new MediaController(this);
		File file = new File("/mnt/sdcard/video.3gp");
		// 播放网络视频
		if (flag) {
			if (file.exists()) {
				video.setVideoPath(file.getAbsolutePath());
				// 为视频播放器设置播放控制
				video.setMediaController(controll);
				// 设置接口实现类
				controll.setMediaPlayer(video);
				// 请求获取焦点
				video.requestFocus();
				video.start();
			} else {
				Toast.makeText(VideoViewTest.this,
						"在sd卡根文件夹下放入video.3gp文件即可播放", Toast.LENGTH_LONG).show();
			}
		} else {
			try {
				// 播放网络视频
				Uri uri = Uri.parse("http://192.168.88.21:8080/test/video.3gp");
				video.setVideoURI(uri);
				// 为视频播放器设置播放控制
				video.setMediaController(controll);
				// 设置接口实现类
				controll.setMediaPlayer(video);
				// 请求获取焦点
				video.requestFocus();
				video.start();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(VideoViewTest.this, "网络视频地址不存在",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
