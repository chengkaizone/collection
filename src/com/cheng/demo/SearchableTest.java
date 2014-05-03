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
 * 搜索框的实现不需要实现ContentProvider；但是实现它主要用于保存搜索历史记录；
 * 也就是不一定要使用SearchRecentSuggestions类
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
		// 设置默认的按键来调用搜索条---搜索键
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
		System.out.println("暂停--->");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		System.out.println("重新开始--->");
		super.onRestart();
	}

	// 此方法会在启动模式为单例情况时调用；但后台的Activity随时可能被系统回收；所以还要在onCreate方法中调用搜索方法
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
				tv.append("无数据");
			}
		}
	}

	@Override
	public void onClick(View v) {
		tv.setText("");
		/*
		 * 如果第4各参数为true将联网查询；为false不联网； 第二个参数为true第一个参数在输入框中将被预选;为false将作为搜索头
		 */
		startSearch("按钮触发", false, null, false);
	}

	// 此方法由搜索按键触发;此方法由搜索框的按钮触发重写它可以在执行搜索时做其他事；比如进度显示等
	public boolean onSearchRequested() {
		System.out.println("onSearchRequested--->");
		Bundle bundle = new Bundle();
		String tmp = et.getText().toString();
		if (tmp.equals("")) {
			bundle.putString("data", "没有数据");
		} else {
			bundle.putString("data", tmp);
		}
		// 调用此方法是调用google的搜索条联网搜索第4个参数为true表示全球搜索
		this.startSearch("测试", true, bundle, true);
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
