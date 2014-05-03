package com.cheng.demo;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MoniterMessage extends CommonActivity {
	TextView show;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.monister_message);
		this.show = ((TextView) findViewById(R.id.monister_message_show));
		getContentResolver().registerContentObserver(
				Uri.parse("content://sms"), true,
				new SMSObserver(new Handler()));
	}

	private final class SMSObserver extends ContentObserver {
		public SMSObserver(Handler handler) {
			super(handler);
			System.out.println("�ص�������");
		}

		public void onChange(boolean paramBoolean) {
			System.out.println("���ݸı�--------->");
			Cursor localCursor = MoniterMessage.this.getContentResolver()
					.query(Uri.parse("content://sms/outbox"), null, null, null,
							null);
			while (localCursor.moveToNext()) {
				StringBuilder localStringBuilder = new StringBuilder();
				String str1 = localCursor.getString(localCursor
						.getColumnIndex("address"));
				String str2 = localCursor.getString(localCursor
						.getColumnIndex("subject"));
				String str3 = localCursor.getString(localCursor
						.getColumnIndex("body"));
				long l = localCursor
						.getLong(localCursor.getColumnIndex("date"));
				localStringBuilder.append("���͵�ַ��" + str1 + "\n");
				localStringBuilder.append("���ͱ��⣺" + str2 + "\n");
				localStringBuilder.append("�������ݣ�" + str3 + "\n");
				localStringBuilder.append("�������ڣ�" + l + "\n");
				MoniterMessage.this.show.append(localStringBuilder.toString());
				System.out.println(localStringBuilder.toString());
			}
		}
	}
}
