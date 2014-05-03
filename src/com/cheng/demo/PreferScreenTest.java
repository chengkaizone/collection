package com.cheng.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.cheng.util.SkinManager;

public class PreferScreenTest extends PreferenceActivity {

	public void onCreate(Bundle save) {
		super.onCreate(save);
		this.addPreferencesFromResource(R.xml.preferences);
	}

	public void onResume() {
		super.onResume();
		SharedPreferences share = this.getSharedPreferences("skin",
				Context.MODE_PRIVATE);
		int skin = share.getInt("skin", 0);
		Drawable draw = SkinManager.getDefDrawable(this, skin);
		this.getWindow().setBackgroundDrawable(draw);
	}
}
