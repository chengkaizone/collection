package com.cheng.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cheng.entity.User;

/**
 * 操作数据库的封装类---在内容提供器中只提供了获取数据库的作用
 * 
 * @author chengkai
 */
public class CollectionDatabaseHelper extends SQLiteOpenHelper {
	public static final String DB = "collection.db3";
	public static final String TABLE = "user";

	public CollectionDatabaseHelper(Context paramContext, String dataName,
			int paramInt) {
		super(paramContext, DB, null, paramInt);
	}

	public CollectionDatabaseHelper(Context paramContext, String dataName,
			SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt) {
		super(paramContext, DB, paramCursorFactory, paramInt);
	}

	public CollectionDatabaseHelper(Context context, int version) {
		super(context, DB, null, version);
	}

	/**
	 * 如果数据库存在则不会调用该方法；
	 */
	public void onCreate(SQLiteDatabase db) {
		String drop = "drop table if exists " + TABLE;
		db.execSQL(drop);
		String sql = "create table "
				+ TABLE
				+ "(_id integer primary key autoincrement,'username'"
				+ " text not null,'password' text not null,'logintime' text not null)";
		System.out.println("创建表--->" + TABLE);
		db.execSQL(sql);
	}

	/**
	 * 当数据库版本大于1时回调该方法---这样可以再同一个应用中使用一个数据库创建多个表如果version为1则不能创建多个表
	 */
	public void onUpgrade(SQLiteDatabase db, int paramInt1, int paramInt2) {
		String drop = "drop table if exists " + TABLE;
		db.execSQL(drop);
		onCreate(db);
	}

	public void insertUser(String userName, String password, String loginTime) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("username", userName);
		cv.put("password", password);
		cv.put("logintime", loginTime);
		db.insert(TABLE, null, cv);
		db.close();
	}

	public void updateUser(String username, String password, String loginTime) {
		SQLiteDatabase db = getWritableDatabase();
		String str = "update " + TABLE + " set password='" + password
				+ "' where username='" + username + "'";
		db.execSQL(str);
		db.close();
	}

	public void updatePassword(String userId, String password) {
		SQLiteDatabase db = getWritableDatabase();
		String str = "update " + TABLE + " set password='" + password
				+ "' where userid='" + userId + "'";
		db.execSQL(str);
		db.close();
	}

	public User queryUser(String userName) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(TABLE, null, null, null, null, null, null);
		User user = null;
		while (cursor.moveToFirst()) {
			if (userName.equals(cursor.getString(cursor
					.getColumnIndex("username")))) {
				user = new User();
				user.setUserName(userName);
				user.setPassword(cursor.getString(cursor
						.getColumnIndex("password")));
				user.setLoginTime(cursor.getString(cursor
						.getColumnIndex("logintime")));
			}
		}
		return null;
	}
}
