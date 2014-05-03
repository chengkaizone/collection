package com.cheng.demo;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cheng.provider.MySearchSuggestProvider;

/**
 * �������ʵ�ֲ���Ҫʵ��ContentProvider������ʵ������Ҫ���ڱ���������ʷ��¼��
 * Ҳ���ǲ�һ��Ҫʹ��SearchRecentSuggestions��
 * 
 * @author Administrator
 * 
 */
public class SearchableTest extends CommonActivity implements OnClickListener {
	TextView tv;
	EditText et;
	Button btn;
	SearchRecentSuggestions recent;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.search_main);
		// ����Ĭ�ϵİ���������������---������
		setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL);
		tv = (TextView) findViewById(R.id.search_main_tv);
		et = (EditText) findViewById(R.id.search_main_et);
		recent = new SearchRecentSuggestions(this,
				MySearchSuggestProvider.AUTHORITY, MySearchSuggestProvider.MODE);
		btn = (Button) findViewById(R.id.search_main_btn);
		btn.setOnClickListener(this);
		System.out.println("onCreate--->");
		doSearch(getIntent());
	}

	@Override
	protected void onPause() {
		System.out.println("��ͣ--->");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		System.out.println("���¿�ʼ--->");
		super.onRestart();
	}

	// �˷�����������ģʽΪ�������ʱ���ã�����̨��Activity��ʱ���ܱ�ϵͳ���գ����Ի�Ҫ��onCreate�����е�����������
	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("onNew--->");
		setIntent(intent);
		doSearch(intent);
	}

	private void doSearch(Intent intent) {
		System.out.println("doSearch--->");
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String result = intent.getStringExtra(SearchManager.QUERY);
			saveSearch(result);
			tv.setText(result);
		}
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			Bundle b = intent.getBundleExtra(SearchManager.APP_DATA);
			if (b != null) {
				tv.append(b.getString("data"));
			} else {
				tv.append("������");
			}
		}
	}

	@Override
	public void onClick(View v) {
		tv.setText("");
		/*
		 * �����4������Ϊtrue��������ѯ��Ϊfalse�������� �ڶ�������Ϊtrue��һ��������������н���Ԥѡ;Ϊfalse����Ϊ����ͷ
		 */
		startSearch("��ť����", false, null, false);
	}

	// �˷�����������������;�˷�����������İ�ť������д��������ִ������ʱ�������£����������ʾ��
	public boolean onSearchRequested() {
		System.out.println("onSearchRequested--->");
		Bundle bundle = new Bundle();
		String tmp = et.getText().toString();
		if (tmp.equals("")) {
			bundle.putString("data", "û������");
		} else {
			bundle.putString("data", tmp);
		}
		// ���ô˷����ǵ���google������������������4������Ϊtrue��ʾȫ������
		this.startSearch("����", true, bundle, true);
		return true;
	}

	private void saveSearch(String str) {
		recent.saveRecentQuery(str, null);
	}

	private void clearSearch() {
		recent.clearHistory();
	}

	@Override
	protected void onDestroy() {
		clearSearch();
		super.onDestroy();
	}
}
