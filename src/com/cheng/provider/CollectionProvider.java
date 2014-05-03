package com.cheng.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.cheng.helper.CollectionDatabaseHelper;

/**
 * �Զ���������ṩ��(Ӧ�ü�)
 * 
 * @author chengkai
 */
public class CollectionProvider extends ContentProvider {
	private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	private CollectionDatabaseHelper helper;
	private static final int USERS = 1;
	private static final int USER = 2;
	private static final String TABLE = "user";
	static {
		// ע��Uri ��һ��������¼�Ĳ�ѯ--ע��֮���Uri����Ч���ܱ��ⲿ����
		matcher.addURI(Collection.AUTHORITY, "users", USERS);
		// ��Uri������ѯ
		matcher.addURI(Collection.AUTHORITY, "user", USER);
	}

	@Override
	public boolean onCreate() {
		helper = new CollectionDatabaseHelper(this.getContext(), 2);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = helper.getReadableDatabase();
		switch (matcher.match(uri)) {
		case USERS:
			return db.query(TABLE, projection, selection, selectionArgs, null,
					null, sortOrder);
		case USER:
			// ���������ѯ�ļ�¼id
			long id = ContentUris.parseId(uri);
			System.out.println("������idֵ---->" + id);
			String where = Collection.Coll._ID + "=" + id;
			if (selection != null && !"".equals(selection)) {
				where = where + "and" + selection;
			}
			System.out.println("where����--->" + where);
			return db.query(TABLE, projection, where, selectionArgs, null,
					null, sortOrder);
		default:
			throw new IllegalArgumentException("δ֪uri" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = helper.getWritableDatabase();
		// ��¼��Id
		long rowId = db.insert(TABLE, Collection.Coll._ID, values);
		// �������ɹ��򷵻�Uri
		if (rowId > 0) {
			// �����е�Uri����׷��id����
			Uri tempUri = ContentUris.withAppendedId(uri, rowId);
			// ֪ͨ�����Ѿ��ı�
			this.getContext().getContentResolver().notifyChange(uri, null);
			return tempUri;
		}
		return null;
	}

	@Override
	public String getType(Uri uri) {
		switch (matcher.match(uri)) {
		case USERS:
			// ��������������Ƕ�������
			return "vnd.android.cursor.users/com.cheng.testmain";
		case USER:
			// ����ǵ�������
			return "vnd.android.cursor.user/com.cheng.testmain";
		default:
			throw new IllegalArgumentException("δ֪uri" + uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = helper.getWritableDatabase();
		// ��¼��Ӱ��ļ�¼��
		int num = 0;
		switch (matcher.match(uri)) {
		case USERS:
			num = db.delete(TABLE, selection, selectionArgs);
			break;
		case USER:
			long id = ContentUris.parseId(uri);
			System.out.println("������idֵ---->" + id);
			String where = Collection.Coll._ID + id;
			// ���ԭ����where�Ӿ������ƴ��ԭ�����Ӿ�
			if (selection != null && !"".equals(selection)) {
				where = where + "and" + selection;
			}
			System.out.println("where����--->" + where);
			num = db.delete(TABLE, where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("δ֪uri" + uri);
		}
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = helper.getWritableDatabase();
		// ��¼��Ӱ��ļ�¼��
		int num = 0;
		switch (matcher.match(uri)) {
		case USERS:
			num = db.update(TABLE, values, selection, selectionArgs);
			break;
		case USER:
			long id = ContentUris.parseId(uri);
			System.out.println("������idֵ---->" + id);
			String where = Collection.Coll._ID + "=" + id;
			if (selection != null && !"".equals(selection)) {
				where = where + "and" + selection;
			}
			System.out.println("where����--->" + where);
			num = db.update(TABLE, values, where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("δ֪uri" + uri);
		}
		// ֪ͨ�����Ѿ��ı�
		this.getContext().getContentResolver().notifyChange(uri, null);
		return num;
	}

}
