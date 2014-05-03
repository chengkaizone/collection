package com.cheng.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.cheng.util.PictureUtils;

public class ImageAdapter extends BaseAdapter {
	int i = 0;
	private Context context;
	private int[] resIds;
	private LayoutInflater inflater;

	public ImageAdapter(Context context, int[] resIds) {
		this.context = context;
		this.resIds = resIds;
		inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return resIds.length;
	}

	public Bitmap getItem(int position) {
		return getBitmap(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return getImageView(position);
	}

	private ImageView getImageView(int loc) {
		System.out.println("创建ImageView对象--->" + i++);
		ImageView img = new ImageView(context);
		img.setImageBitmap(PictureUtils.createReflectedImage(getBitmap(loc)));
		img.setLayoutParams(new Gallery.LayoutParams(60, 40));
		// TypedArray typedArray =
		// context.obtainStyledAttributes(R.styleable.Gallery);
		// img.setBackgroundResource(typedArray.getResourceId(
		// R.styleable.Gallery_android_galleryItemBackground, 0));
		return img;
	}

	/**
	 * 此方法可以有效解决内存溢出问题
	 * 
	 * @param loc
	 * @return
	 */
	private Bitmap getBitmap(int loc) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inSampleSize = 4;
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resIds[loc], option);
		return bitmap;
	}
}
