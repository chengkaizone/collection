package com.cheng.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheng.demo.R;

public class BluetoothDataAdapter extends BaseAdapter {
	private List<String> data;
	private Context context;
	LayoutInflater inflater;

	public BluetoothDataAdapter(Context context, List<String> data) {
		super();
		this.data = data;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public void add(String content) {
		boolean flag = false;
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).equals(content)) {
				flag = true;
			}
		}
		if (!flag) {
			data.add(content);
			this.notifyDataSetChanged();
		}
	}

	public void clear() {
		data.clear();
		this.notifyDataSetChanged();
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public String getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DeviceItem item = null;
		if (convertView == null) {
			item = new DeviceItem();
			convertView = inflater.inflate(R.layout.blue_show, null);
			item.text = (TextView) convertView
					.findViewById(R.id.blue_show_text);
			convertView.setTag(item);
		} else {
			item = (DeviceItem) convertView.getTag();
		}
		item.text.setText(data.get(position));
		return convertView;
	}

	public class DeviceItem {
		TextView text;
	}
}
