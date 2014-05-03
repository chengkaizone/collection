package com.cheng.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.cheng.demo.R;

public class MyAppWidgetProvider extends AppWidgetProvider {
	private static final String UPDATE_ACTION = "android.appwidget.action.MyAppwidget";

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("--->onReceive");
		String action = intent.getAction();
		if (action.equals(UPDATE_ACTION)) {
			SharedPreferences sp = context.getSharedPreferences("appwidget",
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			boolean flag = sp.getBoolean("flag", true);
			System.out.println("--->触发动作--->" + flag);
			RemoteViews remotes = new RemoteViews(context.getPackageName(),
					R.layout.appwidget);
			AppWidgetManager awm = AppWidgetManager.getInstance(context);
			ComponentName componentName = new ComponentName(context,
					MyAppWidgetProvider.class);
			if (flag) {
				remotes.setImageViewResource(R.id.appwidget_img1,
						R.drawable.app_icon);
				remotes.setImageViewResource(R.id.appwidget_img2,
						R.drawable.app_icon);
				remotes.setTextViewText(R.id.appwidget_text, "名车收藏");
				awm.updateAppWidget(componentName, remotes);
				editor.putBoolean("flag", false);
				editor.commit();
			} else {
				remotes.setImageViewResource(R.id.appwidget_img1,
						R.drawable.app_icon);
				remotes.setImageViewResource(R.id.appwidget_img2,
						R.drawable.app_icon);
				remotes.setTextViewText(R.id.appwidget_text, "个人照片");
				awm.updateAppWidget(componentName, remotes);
				editor.putBoolean("flag", true);
				editor.commit();
			}
		} else {
			super.onReceive(context, intent);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// 通过Activity触发
		// for(int i=0;i<appWidgetIds.length;i++){
		// System.out.println("--->onUpdate");
		// Intent intent=new Intent(context,MyDialogTest.class);
		// PendingIntent pi=PendingIntent.getActivity(context, 0, intent, 0);
		// RemoteViews remote=new
		// RemoteViews(context.getPackageName(),R.layout.appwidget);
		// remote.setOnClickPendingIntent(R.id.appwidget_lay, pi);
		// appWidgetManager.updateAppWidget(appWidgetIds[i],remote);
		// }
		System.out.println("--->onUpdate");
		Intent intent = new Intent();
		intent.setAction(UPDATE_ACTION);
		// 通过广播触发
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		RemoteViews remotes = new RemoteViews(context.getPackageName(),
				R.layout.appwidget);
		remotes.setOnClickPendingIntent(R.id.appwidget_lay, pi);
		appWidgetManager.updateAppWidget(appWidgetIds, remotes);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		System.out.println("--->onDeleted");
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		System.out.println("--->onEnabled\n放入数据");
		SharedPreferences sp = context.getSharedPreferences("appwidget",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean("flag", true);
		editor.commit();
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		System.out.println("--->onDisabled");
		super.onDisabled(context);
	}

}
