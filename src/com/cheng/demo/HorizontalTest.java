package com.cheng.demo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cheng.widgets.ProgressBars;

public class HorizontalTest extends CommonActivity {
	Button btn1, btn2;
	ProgressBars tpb;
	ProgressDialog dialog;
	int i = 1;
	Handler h = new Handler() {
		public void handleMessage(Message msg) {
			System.out.println("接受更新消息---->" + msg.what);
			tpb.setProgress(msg.what);
			int recod = ((int) (msg.what * 1.5));
			if (recod <= 100) {
				tpb.setSecondaryProgress(recod);
			}
		}
	};

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.kai_main);
		btn1 = (Button) findViewById(R.id.main_start);
		btn2 = (Button) findViewById(R.id.main_stop);
		tpb = (ProgressBars) findViewById(R.id.main_pb);
		btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				i = 1;
				tpb.setProgress(0);
				start();
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void start() {
		System.out.println("开始启动--->");
		new Thread() {
			public void run() {
				try {
					while (i <= 100) {
						System.out.println("线程运行中---->" + i);
						h.sendEmptyMessage(i);
						i++;
						Thread.sleep(200);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}