package com.cheng.demo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.cheng.util.SkinManager;

public class CommonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences share = this.getSharedPreferences("skins",
				Context.MODE_PRIVATE);
		int skin = share.getInt("skin", 0);
		Drawable draw = SkinManager.getDefDrawable(this, skin);
		this.getWindow().setBackgroundDrawable(draw);
	}

	public void showHint(String hint) {
		Toast.makeText(this, hint, Toast.LENGTH_LONG).show();
	}
}
