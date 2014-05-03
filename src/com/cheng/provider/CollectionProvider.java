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
 * 自定义的内容提供器(应用级)
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
		// 注册Uri 第一个多条记录的查询--注册之后该Uri才有效；能被外部调用
		matcher.addURI(Collection.AUTHORITY, "users", USERS);
		// 该Uri条件查询
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
			// 解析出想查询的记录id
			long id = ContentUris.parseId(uri);
			System.out.println("解析的id值---->" + id);
			String where = Collection.Coll._ID + "=" + id;
			if (selection != null && !"".equals(selection)) {
				where = where + "and" + selection;
			}
			System.out.println("where条件--->" + where);
			return db.query(TABLE, projection, where, selectionArgs, null,
					null, sortOrder);
		default:
			throw new IllegalArgumentException("未知uri" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = helper.getWritableDatabase();
		// 记录行Id
		long rowId = db.insert(TABLE, Collection.Coll._ID, values);
		// 如果插入成功则返回Uri
		if (rowId > 0) {
			// 在已有的Uri后面追加id数据
			Uri tempUri = ContentUris.withAppendedId(uri, rowId);
			// 通知数据已经改变
			this.getContext().getContentResolver().notifyChange(uri, null);
			return tempUri;
		}
		return null;
	}

	@Override
	public String getType(Uri uri) {
		switch (matcher.match(uri)) {
		case USERS:
			// 如果操作的数据是多项数据
			return "vnd.android.cursor.users/com.cheng.testmain";
		case USER:
			// 如果是单项数据
			return "vnd.android.cursor.user/com.cheng.testmain";
		default:
			throw new IllegalArgumentException("未知uri" + uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = helper.getWritableDatabase();
		// 记录受影响的记录数
		int num = 0;
		switch (matcher.match(uri)) {
		case USERS:
			num = db.delete(TABLE, selection, selectionArgs);
			break;
		case USER:
			long id = ContentUris.parseId(uri);
			System.out.println("解析的id值---->" + id);
			String where = Collection.Coll._ID + id;
			// 如果原来的where子句存在则拼接原来的子句
			if (selection != null && !"".equals(selection)) {
				where = where + "and" + selection;
			}
			System.out.println("where条件--->" + where);
			num = db.delete(TABLE, where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("未知uri" + uri);
		}
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = helper.getWritableDatabase();
		// 记录受影响的记录数
		int num = 0;
		switch (matcher.match(uri)) {
		case USERS:
			num = db.update(TABLE, values, selection, selectionArgs);
			break;
		case USER:
			long id = ContentUris.parseId(uri);
			System.out.println("解析的id值---->" + id);
			String where = Collection.Coll._ID + "=" + id;
			if (selection != null && !"".equals(selection)) {
				where = where + "and" + selection;
			}
			System.out.println("where条件--->" + where);
			num = db.update(TABLE, values, where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("未知uri" + uri);
		}
		// 通知数据已经改变
		this.getContext().getContentResolver().notifyChange(uri, null);
		return num;
	}

}
