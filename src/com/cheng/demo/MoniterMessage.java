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
			System.out.println("回调处理器");
		}

		public void onChange(boolean paramBoolean) {
			System.out.println("数据改变--------->");
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
				localStringBuilder.append("发送地址：" + str1 + "\n");
				localStringBuilder.append("发送标题：" + str2 + "\n");
				localStringBuilder.append("发送内容：" + str3 + "\n");
				localStringBuilder.append("发送日期：" + l + "\n");
				MoniterMessage.this.show.append(localStringBuilder.toString());
				System.out.println(localStringBuilder.toString());
			}
		}
	}
}
