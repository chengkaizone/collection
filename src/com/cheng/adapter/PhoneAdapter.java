package com.cheng.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheng.demo.R;

public class PhoneAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, String>> data;
	private LayoutInflater inflater;

	public PhoneAdapter(Context paramContext,
			List<Map<String, String>> paramList) {
		this.context = paramContext;
		this.data = paramList;
		this.inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return this.data.size();
	}

	public Map<String, String> getItem(int paramInt) {
		return this.data.get(paramInt);
	}

	public long getItemId(int paramInt) {
		System.out.println("adapter--->" + paramInt);
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		Holder localHolder;
		if (paramView != null) {
			localHolder = (Holder) paramView.getTag();
		} else {
			localHolder = new Holder();
			paramView = this.inflater
					.inflate(R.layout.adapter_phone_data, null);
			localHolder.name = ((TextView) paramView
					.findViewById(R.id.phone_data_name));
			localHolder.phone = ((TextView) paramView
					.findViewById(R.id.phone_data_phone));
			paramView.setTag(localHolder);
		}
		Map<String, String> localMap = data.get(paramInt);
		localHolder.name.setText(localMap.get("name"));
		localHolder.phone.setText(localMap.get("phone"));
		return paramView;
	}

	private class Holder {
		TextView name;
		TextView phone;
	}
}
