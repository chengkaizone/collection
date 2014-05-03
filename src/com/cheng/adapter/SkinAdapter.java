package com.cheng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cheng.demo.R;

public class SkinAdapter extends BaseAdapter {
	private Context context;
	private int[] resIds;
	private LayoutInflater inflater;
	private String[] srr;
	private boolean[] brr;

	public SkinAdapter(Context context, String[] srr, int[] resIds) {
		this.context = context;
		this.resIds = resIds;
		this.srr = srr;
		inflater = LayoutInflater.from(context);
		brr = new boolean[srr.length];
	}

	@Override
	public int getCount() {
		return resIds.length;
	}

	@Override
	public Object getItem(int position) {
		return resIds[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void selected(int loc) {
		System.out.println("��ѡ��ť��ѡ" + loc);
		for (int i = 0; i < brr.length; i++) {
			if (i == loc) {
				brr[i] = true;
			} else {
				brr[i] = false;
			}
		}
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView--->������");
		SkinHolder holder = null;
		if (convertView == null) {
			holder = new SkinHolder();
			convertView = inflater.inflate(R.layout.adapter_skin, null);
			holder.img = (ImageView) convertView
					.findViewById(R.id.adapter_skin_img);
			holder.tv = (TextView) convertView
					.findViewById(R.id.adapter_skin_tv);
			holder.rb = (RadioButton) convertView
					.findViewById(R.id.adapter_skin_rb);
			convertView.setTag(holder);
		} else {
			holder = (SkinHolder) convertView.getTag();
		}
		holder.img.setImageResource(resIds[position]);
		holder.tv.setText(srr[position]);
		System.out.println(brr[position] + "����---״̬");
		holder.rb.setChecked(brr[position]);
		return convertView;
	}

	private class SkinHolder {
		ImageView img;
		TextView tv;
		RadioButton rb;
	}
}
