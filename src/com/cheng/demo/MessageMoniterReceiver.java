package com.cheng.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * ��������---��ϵͳ���յ�����ʱ����������
 * 
 * @author chengkai
 * 
 */
public class MessageMoniterReceiver extends BroadcastReceiver {
	static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";

	public void onReceive(Context context, Intent intent) {
		// ����ǽ��յ�����
		if (intent.getAction().equals(SMS_ACTION)) {
			// ȡ���㲥������������д��뽫����ϵͳ�ղ������ţ�---���ų��򽫽��ղ�������
			// abortBroadcast();
			StringBuilder sb = new StringBuilder();
			// ������SMS������������
			Bundle bundle = intent.getExtras();
			// �ж��Ƿ�������
			if (bundle != null) {
				// ͨ��pdus���Ի�ý��յ������ж�����Ϣ
				Object[] pdus = (Object[]) bundle.get("pdus");
				// �������Ŷ���array,�������յ��Ķ��󳤶�������array�Ĵ�С
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				// �������Ķ��źϲ��Զ�����Ϣ��StringBuilder����
				for (SmsMessage message : messages) {
					sb.append("��������:");
					// ��ý��ն��ŵĵ绰����
					sb.append(message.getDisplayOriginatingAddress() + "\n");
					sb.append("��������:\n");
					// ��ö��ŵ�����
					sb.append(message.getDisplayMessageBody());
				}
			}
			Toast.makeText(context, sb.toString(), 5000).show();
		}
	}
}