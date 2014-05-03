package com.cheng.demo;

import java.io.File;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AudioManagerTest extends CommonActivity implements
		OnClickListener, OnCheckedChangeListener {
	boolean isPlay = false;
	AudioManager am;
	Button play, add, lower;
	ToggleButton toggle;
	MediaPlayer player;
	int current = 0;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.audiomanager_main);
		initWidget();
	}

	private void initWidget() {
		play = (Button) findViewById(R.id.audio_play);
		add = (Button) findViewById(R.id.audio_add);
		lower = (Button) findViewById(R.id.audio_lower);
		toggle = (ToggleButton) findViewById(R.id.audio_toggle);
		am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		player = new MediaPlayer();
		// 设置循环播放
		// player.setLooping(true);
		play.setOnClickListener(this);
		add.setOnClickListener(this);
		lower.setOnClickListener(this);
		toggle.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.audio_play:
			try {
				String path = Environment.getExternalStorageDirectory()
						.getCanonicalPath() + "/music.mp3";
				File file = new File(path);
				if (!file.exists()) {
					Toast.makeText(AudioManagerTest.this,
							"在sd卡根文件夹下放入music.mp3文件即可播放", 5000).show();
				} else {
					player.setDataSource(path);
					if (!isPlay) {
						player.start();
						isPlay = !isPlay;
					} else {
						player.pause();
						isPlay = !isPlay;
					}
				}
			} catch (Exception e) {
				Toast.makeText(AudioManagerTest.this,
						"在sd卡根文件夹下放入music.mp3文件即可播放", 5000).show();
				e.printStackTrace();
			}
			break;
		case R.id.audio_add:
			am.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			break;
		case R.id.audio_lower:
			am.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		am.setStreamMute(AudioManager.STREAM_MUSIC, isChecked);
	}

	public void onPause() {
		// 记录当前位置
		current = player.getCurrentPosition();
		super.onPause();
	}

	public void onDestroy() {
		if (isPlay) {
			player.stop();
		}
		// 释放资源
		player.release();
		super.onDestroy();
	}
}
