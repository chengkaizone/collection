package com.cheng.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * ������ʾ
 * 
 * @author Administrator
 * 
 */
public class MySearchSuggestProvider extends SearchRecentSuggestionsProvider {
	// ��ס�������ֶ�
	// public static final String AUTHORITY="searchprovider";
	public static final String AUTHORITY = MySearchSuggestProvider.class
			.getName();
	public static final int MODE = DATABASE_MODE_QUERIES;

	public MySearchSuggestProvider() {
		// System.out.println(AUTHORITY);
		this.setupSuggestions(AUTHORITY, MODE);
	}
}
