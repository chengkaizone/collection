package com.cheng.demo;

import java.util.Locale;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 使用文本朗读一定要退出时关闭---提高程序性能--有时不能朗读原因未知
 * 
 * @author Administrator
 * 
 */
public class TextToSpeechTest extends CommonActivity implements OnClickListener {
	private TextToSpeech tts;
	private EditText et;
	private Button play, save;
	boolean flag = false;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.text_to_speech_main);
		initWidget();
	}

	private void initWidget() {
		System.out.println("初始化控件");
		et = (EditText) findViewById(R.id.text_to_speech_et);
		play = (Button) findViewById(R.id.text_to_speech_action);
		save = (Button) findViewById(R.id.text_to_speech_save);
		tts = new TextToSpeech(this, new OnInitListener() {
			public void onInit(int status) {
				if (status == TextToSpeech.SUCCESS) {
					System.out.println("设置朗读语言---");
					// 设置使用美式英语朗读
					int lauguage = tts.setLanguage(Locale.ENGLISH);
					if (lauguage != TextToSpeech.LANG_COUNTRY_AVAILABLE
							&& lauguage != TextToSpeech.LANG_AVAILABLE) {
						System.out.println("暂不支持你设置的语种...");
						Toast.makeText(TextToSpeechTest.this, "暂不支持你设置的语种...",
								5000).show();
					}
				}
			}
		});
		play.setOnClickListener(this);
		save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		String str = et.getText().toString().trim();

		switch (id) {
		case R.id.text_to_speech_action:
			if (str.equals("")) {
				Toast.makeText(this, "请输入朗读内容...", 3000).show();
			} else {
				// 使用追加模式朗读
				System.out.println("正在朗读---" + str);
				tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
				flag = true;
			}
			break;
		case R.id.text_to_speech_save:
			if (str.equals("")) {
				Toast.makeText(this, "请输入朗读内容...", 3000).show();
			} else {
				if (flag) {
					String file = "/mnt/sdcard/" + System.currentTimeMillis()
							+ ".wav";
					tts.synthesizeToFile(str, null, file);
					Toast.makeText(this, "声音保存成功", 2000).show();
				} else {
					Toast.makeText(this, "还没有朗读文本内容...", 2000).show();
				}
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// 关闭文本朗读
		if (tts != null) {
			tts.shutdown();
		}
		super.onDestroy();
	}

}
