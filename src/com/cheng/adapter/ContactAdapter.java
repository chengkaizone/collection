package com.cheng.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheng.demo.R;
import com.cheng.entity.Contact;

public class ContactAdapter extends BaseAdapter {
	private Context context;
	private List<Contact> data;
	private LayoutInflater inflater;

	public ContactAdapter(Context context, List<Contact> data) {
		this.context = context;
		this.data = data;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int loc) {
		return data.get(loc);
	}

	@Override
	public long getItemId(int loc) {
		return loc;
	}

	@Override
	public View getView(int loc, View view, ViewGroup parent) {
		Holder holder = null;
		if (view == null) {
			holder = new Holder();
			view = inflater.inflate(R.layout.adapter_phone_data, null);
			holder.name = (TextView) view.findViewById(R.id.phone_data_name);
			holder.phone = (TextView) view.findViewById(R.id.phone_data_phone);
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
		Contact c = data.get(loc);
		holder.name.setText(c.getName());
		holder.phone.setText(c.getPhone());
		System.out.println("ÓÊÏä--->" + c.getEmail());
		return view;
	}

	private class Holder {
		TextView name;
		TextView phone;
	}
}
