package com.cheng.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.cheng.app.ColorPickerDialog;
import com.cheng.widgets.DrawView;

public class PictureTest extends CommonActivity implements
		ColorPickerDialog.OnColorChangedListener {
	private BlurMaskFilter blur;
	private EmbossMaskFilter emboss;
	private DrawView draw;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.picture_main);
		draw = (DrawView) findViewById(R.id.picture_draw_view);
		blur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
		emboss = new EmbossMaskFilter(new float[] { 1.5f, 1.5f, 1.5f }, 0.6f,
				6f, 4.2f);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 获取菜单解析器
		MenuInflater inflater = new MenuInflater(this);
		// 将菜单资源解析到菜单项中
		inflater.inflate(R.menu.picture_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.width_1:
			draw.cachePaint.setStrokeWidth(1);
			break;
		case R.id.width_3:
			draw.cachePaint.setStrokeWidth(3);
			break;
		case R.id.width_5:
			draw.cachePaint.setStrokeWidth(5);
			break;
		case R.id.blur:
			draw.cachePaint.setMaskFilter(blur);
			break;
		case R.id.emboss:
			draw.cachePaint.setMaskFilter(emboss);
			break;
		case R.id.select_color:
			new ColorPickerDialog(this, this, draw.cachePaint.getColor())
					.show();
			break;
		case R.id.save:
			try {
				Toast.makeText(this, "正在保存图像...", 2000).show();
				saveBitmap();
				Toast.makeText(this, "保存成功", 2000).show();
			} catch (Exception e) {
				Toast.makeText(this, "保存失败", 2000).show();
				e.printStackTrace();
			}
			break;
		case R.id.repicture:
			// 通知重新绘图
			draw.initParam(PictureTest.this);
			break;
		}
		return true;
	}

	private void saveBitmap() {
		String path = "/mnt/sdcard/pictures/";
		String fileName = System.currentTimeMillis() + ".png";
		try {
			File file = new File(path);
			if (!file.exists()) {
				// 创建文件夹
				file.mkdir();
			}
			// 声明文件
			File png = new File(path + fileName);
			// 创建图片文件
			png.createNewFile();
			// 打开文件输入流
			FileOutputStream output = new FileOutputStream(png);
			Bitmap cache = draw.getBitmap();
			// 压缩成二进制到指定输出流并指定格式保存图像
			cache.compress(Bitmap.CompressFormat.PNG, 100, output);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void colorChanged(int color) {
		draw.cachePaint.setColor(color);
	}
}