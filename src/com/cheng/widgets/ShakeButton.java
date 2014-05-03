package com.cheng.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cheng.demo.R;

public class ShakeButton extends Button {

	public ShakeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setOnClickListener(final OnClickListener l) {
		super.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ShakeButton.this.startAnimation(AnimationUtils.loadAnimation(
						getContext(), R.anim.shake));
				showToast();
				l.onClick(v);
			}
		});
	}

	private void showToast() {
		Context act = this.getContext();
		Toast toast = new Toast(act);
		// 使用makeText方法创建的Toast有view；直接创建没有view；
		LinearLayout lay = new LinearLayout(act);
		lay.setOrientation(LinearLayout.VERTICAL);
		ImageView img = new ImageView(act);
		img.setImageResource(R.drawable.ready);
		lay.addView(img);
		// lay.addView(view);
		toast.setView(lay);
		toast.show();
	}
}
