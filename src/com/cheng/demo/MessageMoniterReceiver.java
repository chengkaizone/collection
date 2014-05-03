package com.cheng.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * 短信拦截---当系统接收到短信时触发接收器
 * 
 * @author chengkai
 * 
 */
public class MessageMoniterReceiver extends BroadcastReceiver {
	static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";

	public void onReceive(Context context, Intent intent) {
		// 如果是接收到短信
		if (intent.getAction().equals(SMS_ACTION)) {
			// 取消广播（添加下面这行代码将会让系统收不到短信）---短信程序将接收不到短信
			// abortBroadcast();
			StringBuilder sb = new StringBuilder();
			// 接收由SMS传过来的数据
			Bundle bundle = intent.getExtras();
			// 判断是否有数据
			if (bundle != null) {
				// 通过pdus可以获得接收到的所有短信消息
				Object[] pdus = (Object[]) bundle.get("pdus");
				// 构建短信对象array,并依据收到的对象长度来创建array的大小
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				// 将送来的短信合并自定义信息于StringBuilder当中
				for (SmsMessage message : messages) {
					sb.append("短信来自:");
					// 获得接收短信的电话号码
					sb.append(message.getDisplayOriginatingAddress() + "\n");
					sb.append("短信内容:\n");
					// 获得短信的内容
					sb.append(message.getDisplayMessageBody());
				}
			}
			Toast.makeText(context, sb.toString(), 5000).show();
		}
	}
}