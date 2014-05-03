package com.cheng.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class BatteryMoniterReceiver extends BroadcastReceiver {
	public void onReceive(Context paramContext, Intent paramIntent) {
		Bundle localBundle = paramIntent.getExtras();
		int i = localBundle.getInt("level");
		int j = localBundle.getInt("scale");
		if (1.0D * i / j >= 0.15D)
			return;
		Toast.makeText(paramContext, "电量过低，请尽快充电！", 5000).show();
	}
}