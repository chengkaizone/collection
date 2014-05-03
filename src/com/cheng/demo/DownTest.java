package com.cheng.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.util.FileUtil;

public class DownTest extends CommonActivity implements OnClickListener {
	Button btn1, btn2;
	TextView tv;
	Handler h = new Handler() {
		public void handleMessage(Message msg) {
			int i = msg.what;
			if (i == -1) {
				Toast.makeText(DownTest.this, "下载失败", 2000).show();
			} else if (i == 0) {
				Toast.makeText(DownTest.this, "文件已存在", 2000).show();
			} else if (i == 1) {
				Toast.makeText(DownTest.this, "下载完成", 2000).show();
			}
			if (msg.arg1 == 123) {
				String str = (String) msg.obj;
				if (str != null) {
					tv.setText(str);
					Toast.makeText(DownTest.this, "下载成功", 2000).show();
				} else {
					Toast.makeText(DownTest.this, "下载异常", 2000).show();
				}
			}
		}
	};

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.down);
		btn1 = (Button) findViewById(R.id.down_button1);
		btn2 = (Button) findViewById(R.id.down_button2);
		tv = (TextView) findViewById(R.id.down_tv);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.down_button1:
			final String url = "http://10.0.2.2:8080/mp3s/android_themes.txt";
			new Thread() {
				public void run() {
					String str = FileUtil.downFile(url);
					Message msg = new Message();
					msg.arg1 = 123;
					msg.obj = str;
					h.sendMessage(msg);
				}
			}.start();
			break;
		case R.id.down_button2:
			final String wqs = "http://zhangmenshiting2.baidu.com/data2/music/"
					+ "1687450/1687450.mp3?xcode=9aa25d70d2e2d63318e4ea5899f10dfe&"
					+ "mid=0.82543138340886";
			final String music = "http://10.0.2.2:8080/mp3s/desk.zip";
			Toast.makeText(this, "请稍后...", 2000).show();
			new Thread() {
				public void run() {
					int i = FileUtil.downFile(music, "mp3s", "test.zip");
					h.sendEmptyMessage(i);
				}
			}.start();
			break;
		}
	}
}
