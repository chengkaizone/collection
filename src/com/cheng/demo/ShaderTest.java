package com.cheng.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.cheng.widgets.ShaderView;

public class ShaderTest extends CommonActivity implements OnClickListener {
	private ShaderView shader;
	private Button btn1, btn2, btn3, btn4, btn5;
	private Shader[] shaders = new Shader[5];
	private Bitmap bitmap;
	private ImageView img;
	private int[] colors = { Color.RED, Color.GREEN, Color.BLUE };

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.shader_main);
		shader = (ShaderView) findViewById(R.id.shader_view);
		btn1 = (Button) findViewById(R.id.shader_btn1);
		btn2 = (Button) findViewById(R.id.shader_btn2);
		btn3 = (Button) findViewById(R.id.shader_btn3);
		btn4 = (Button) findViewById(R.id.shader_btn4);
		btn5 = (Button) findViewById(R.id.shader_btn5);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		// 实例化位图对象
		bitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.lashou);
		// 实例化位图渐变对象
		shaders[0] = new BitmapShader(bitmap, TileMode.REPEAT, TileMode.MIRROR);
		// 线性渐变对象
		shaders[1] = new LinearGradient(0, 0, 100, 100, colors, null,
				TileMode.REPEAT);
		// 圆形渐变对象
		shaders[2] = new RadialGradient(100, 100, 80, colors, null,
				TileMode.REPEAT);
		// 角度渐变对象
		shaders[3] = new SweepGradient(160, 160, colors, null);
		// 混合渐变对象
		shaders[4] = new ComposeShader(shaders[1], shaders[2],
				PorterDuff.Mode.DARKEN);
		shader.getPaint().setShader(shaders[0]);
		shader.invalidate();
	}

	@Override
	public void onClick(View v) {
		Paint p = shader.getPaint();
		int id = v.getId();
		switch (id) {
		case R.id.shader_btn1:
			p.setShader(shaders[0]);
			break;
		case R.id.shader_btn2:
			p.setShader(shaders[1]);
			break;
		case R.id.shader_btn3:
			p.setShader(shaders[2]);
			break;
		case R.id.shader_btn4:
			p.setShader(shaders[3]);
			break;
		case R.id.shader_btn5:
			p.setShader(shaders[4]);
			break;
		}
		shader.invalidate();
	}
}
