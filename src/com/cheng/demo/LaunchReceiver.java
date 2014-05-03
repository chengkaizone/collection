package com.cheng.demo;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cheng.main.RootMain;

/**
 * 监听开机事件来启动应用
 * 
 * @author Administrator
 * 
 */
public class LaunchReceiver extends BroadcastReceiver {
	private PendingIntent pi;

	public void onReceive(Context context, Intent paramIntent) {
		// Intent intent=new Intent(context,RootMain.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// context.startActivity(intent);
	}
}