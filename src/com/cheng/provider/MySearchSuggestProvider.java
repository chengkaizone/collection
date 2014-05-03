package com.cheng.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * 搜索提示
 * 
 * @author Administrator
 * 
 */
public class MySearchSuggestProvider extends SearchRecentSuggestionsProvider {
	// 记住这两个字段
	// public static final String AUTHORITY="searchprovider";
	public static final String AUTHORITY = MySearchSuggestProvider.class
			.getName();
	public static final int MODE = DATABASE_MODE_QUERIES;

	public MySearchSuggestProvider() {
		// System.out.println(AUTHORITY);
		this.setupSuggestions(AUTHORITY, MODE);
	}
}
