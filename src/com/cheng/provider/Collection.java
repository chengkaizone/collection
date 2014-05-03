package com.cheng.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * ��������Ӧ�õĳ�����Ϊ�����ṩ�������ⲿӦ�õ��÷���ά��
 * 
 * @author chengkai
 * 
 */
public final class Collection {
	// �����ContentProvider��AUTHORITY
	public static final String AUTHORITY = "com.cheng.providers.colprovider";

	// ����һ����̬�ڲ���
	public static final class Coll implements BaseColumns {
		// ����Content�����������4��������
		public static final String _ID = "_id";
		public static final String NAME = "username";
		public static final String PASSWORD = "password";
		public static final String LOGINTIME = "logintime";
		// �����û���Uri
		public static final Uri COLS_CONTENT_URI = Uri.parse("content://"
				+ AUTHORITY + "/users");
		// �����û���Uri
		public static final Uri COL_CONTENT_URI = Uri.parse("content://"
				+ AUTHORITY + "/user");
	}
}
