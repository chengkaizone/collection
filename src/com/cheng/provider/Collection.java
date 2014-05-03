package com.cheng.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 用于声明应用的常量作为内容提供器；供外部应用调用方便维护
 * 
 * @author chengkai
 * 
 */
public final class Collection {
	// 定义改ContentProvider的AUTHORITY
	public static final String AUTHORITY = "com.cheng.providers.colprovider";

	// 定义一个静态内部类
	public static final class Coll implements BaseColumns {
		// 定义Content所允许操作的4个数据列
		public static final String _ID = "_id";
		public static final String NAME = "username";
		public static final String PASSWORD = "password";
		public static final String LOGINTIME = "logintime";
		// 所有用户的Uri
		public static final Uri COLS_CONTENT_URI = Uri.parse("content://"
				+ AUTHORITY + "/users");
		// 单个用户的Uri
		public static final Uri COL_CONTENT_URI = Uri.parse("content://"
				+ AUTHORITY + "/user");
	}
}
