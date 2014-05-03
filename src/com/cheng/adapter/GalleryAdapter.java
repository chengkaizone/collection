package com.cheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {
	private Context context;
	private int[] ress;

	public GalleryAdapter(Context context, int[] ress) {
		this.context = context;
		this.ress = ress;
	}

	public int getCount() {
		return ress.length;
	}

	public Object getItem(int loc) {
		return ress[loc];
	}

	public long getItemId(int loc) {
		return loc;
	}

	public View getView(int loc, View convertView, ViewGroup group) {
		ImageView img = new ImageView(context);
		img.setImageResource(ress[loc]);
		img.setLayoutParams(new Gallery.LayoutParams(-1, 80));
		return img;
	}
}
