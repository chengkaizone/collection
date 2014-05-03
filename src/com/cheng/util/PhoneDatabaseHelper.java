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
		System.out.println("������--->phones");
		paramSQLiteDatabase.execSQL("create table " + TABLE
				+ "(_id integer primary key autoincrement,"
				+ "name text not null,phone text not null)");
		paramSQLiteDatabase.execSQL("insert into " + TABLE
				+ "(name,phone) values('�ʳɿ�','13436547658')");
		paramSQLiteDatabase.execSQL("insert into " + TABLE
				+ "(name,phone) values('������','15567656577')");
		paramSQLiteDatabase.execSQL("insert into " + TABLE
				+ "(name,phone) values('������','15836547658')");
		paramSQLiteDatabase.execSQL("insert into " + TABLE
				+ "(name,phone) values('�η�','14736547658')");
		paramSQLiteDatabase.execSQL("insert into " + TABLE
				+ "(name,phone) values('����','18736547658')");
	}

	/**
	 * �����ݿ�汾����1ʱ�ص��÷���---����������ͬһ��Ӧ����ʹ��һ�����ݿⴴ����������versionΪ1���ܴ��������
	 */
	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
			int paramInt2) {
		System.out.println("���ݿ����---->");
		paramSQLiteDatabase.execSQL("drop table " + TABLE + " if exists");
		onCreate(paramSQLiteDatabase);
	}

	public void deleteUser(String paramString1, String paramString2) {
		System.out.println("ɾ���û�--->");
		SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
		String str = "delete from " + TABLE + " where name='" + paramString1
				+ "' and phone='" + paramString2 + "'";
		System.out.println(str);
		localSQLiteDatabase.execSQL(str);
	}

	public void insertUser(String paramString1, String paramString2) {
		System.out.println("����û�--->");
		SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("name", paramString1);
		localContentValues.put("phone", paramString2);
		localSQLiteDatabase.insert(TABLE, null, localContentValues);
	}

	public void updateUser(String paramString1, String paramString2,
			String paramString3, String paramString4) {
		System.out.println("�����û�--->");
		SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
		String str = "update " + TABLE + " set name='" + paramString3
				+ "' , phone='" + paramString4 + "' where name='"
				+ paramString1 + "' and phone='" + paramString2 + "'";
		System.out.println(str);
		localSQLiteDatabase.execSQL(str);
	}
}
