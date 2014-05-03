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
 * ʹ���ı��ʶ�һ��Ҫ�˳�ʱ�ر�---��߳�������--��ʱ�����ʶ�ԭ��δ֪
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
		System.out.println("��ʼ���ؼ�");
		et = (EditText) findViewById(R.id.text_to_speech_et);
		play = (Button) findViewById(R.id.text_to_speech_action);
		save = (Button) findViewById(R.id.text_to_speech_save);
		tts = new TextToSpeech(this, new OnInitListener() {
			public void onInit(int status) {
				if (status == TextToSpeech.SUCCESS) {
					System.out.println("�����ʶ�����---");
					// ����ʹ����ʽӢ���ʶ�
					int lauguage = tts.setLanguage(Locale.ENGLISH);
					if (lauguage != TextToSpeech.LANG_COUNTRY_AVAILABLE
							&& lauguage != TextToSpeech.LANG_AVAILABLE) {
						System.out.println("�ݲ�֧�������õ�����...");
						Toast.makeText(TextToSpeechTest.this, "�ݲ�֧�������õ�����...",
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
				Toast.makeText(this, "�������ʶ�����...", 3000).show();
			} else {
				// ʹ��׷��ģʽ�ʶ�
				System.out.println("�����ʶ�---" + str);
				tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
				flag = true;
			}
			break;
		case R.id.text_to_speech_save:
			if (str.equals("")) {
				Toast.makeText(this, "�������ʶ�����...", 3000).show();
			} else {
				if (flag) {
					String file = "/mnt/sdcard/" + System.currentTimeMillis()
							+ ".wav";
					tts.synthesizeToFile(str, null, file);
					Toast.makeText(this, "��������ɹ�", 2000).show();
				} else {
					Toast.makeText(this, "��û���ʶ��ı�����...", 2000).show();
				}
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// �ر��ı��ʶ�
		if (tts != null) {
			tts.shutdown();
		}
		super.onDestroy();
	}

}
