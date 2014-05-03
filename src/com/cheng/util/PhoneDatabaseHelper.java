package com.cheng.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhoneDatabaseHelper extends SQLiteOpenHelper {
	private static final String DB = "collection.db3";
	private static final String TABLE = "phones";

	public PhoneDatabaseHelper(Context paramContext, String paramString,
			int paramInt) {
		super(paramContext, DB, null, paramInt);
	}

	public PhoneDatabaseHelper(Context paramContext, String paramString,
			SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt) {
		super(paramContext, DB, paramCursorFactory, paramInt);
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
		System.out.println("创建表--->phones");
		paramSQLiteDatabase.execSQL("create table " + TABLE
				+ "(_id integer primary key autoincrement,"
				+ "name text not null,phone text not null)");
		paramSQLiteDatabase.execSQL("insert into " + TABLE
				+ "(name,phone) values('甘成凯','13436547658')");
		paramSQLiteDatabase.execSQL("insert into " + TABLE
				+ "(name,phone) values('陈文龙','15567656577')");
		paramSQLiteDatabase.execSQL("insert into " + TABLE
				+ "(name,phone) values('刘立夫','15836547658')");
		paramSQLiteDatabase.execSQL("insert into " + TABLE
				+ "(name,phone) values('游芬','14736547658')");
		paramSQLiteDatabase.execSQL("insert into " + TABLE
				+ "(name,phone) values('甘艳','18736547658')");
	}

	/**
	 * 当数据库版本大于1时回调该方法---这样可以再同一个应用中使用一个数据库创建多个表如果version为1则不能创建多个表
	 */
	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
			int paramInt2) {
		System.out.println("数据库更新---->");
		paramSQLiteDatabase.execSQL("drop table " + TABLE + " if exists");
		onCreate(paramSQLiteDatabase);
	}

	public void deleteUser(String paramString1, String paramString2) {
		System.out.println("删除用户--->");
		SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
		String str = "delete from " + TABLE + " where name='" + paramString1
				+ "' and phone='" + paramString2 + "'";
		System.out.println(str);
		localSQLiteDatabase.execSQL(str);
	}

	public void insertUser(String paramString1, String paramString2) {
		System.out.println("添加用户--->");
		SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("name", paramString1);
		localContentValues.put("phone", paramString2);
		localSQLiteDatabase.insert(TABLE, null, localContentValues);
	}

	public void updateUser(String paramString1, String paramString2,
			String paramString3, String paramString4) {
		System.out.println("更新用户--->");
		SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
		String str = "update " + TABLE + " set name='" + paramString3
				+ "' , phone='" + paramString4 + "' where name='"
				+ paramString1 + "' and phone='" + paramString2 + "'";
		System.out.println(str);
		localSQLiteDatabase.execSQL(str);
	}
}
