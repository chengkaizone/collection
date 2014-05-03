package com.cheng.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheng.demo.R;
import com.cheng.entity.ForecastWeather;

public class WeatherAdapter extends BaseAdapter {
	private Context context;
	private List<ForecastWeather> data = new ArrayList<ForecastWeather>();
	private LayoutInflater inflater;
	private Bitmap defaultBitmap;

	public WeatherAdapter(Context context, List<ForecastWeather> data) {
		this.context = context;
		this.data = data;
		System.out.println("--->" + data.size());
		inflater = LayoutInflater.from(context);
		defaultBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.icon_menu_update);
	}

	public int getCount() {
		return data.size();
	}

	public ForecastWeather getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		WeatherItem item = null;
		if (convertView == null) {
			item = new WeatherItem();
			convertView = inflater.inflate(R.layout.adapter_weather, null);
			item.img = (ImageView) convertView
					.findViewById(R.id.adapter_weather_img);
			item.info = (TextView) convertView
					.findViewById(R.id.adapter_weather_info);
			convertView.setTag(item);
		} else {
			item = (WeatherItem) convertView.getTag();
		}
		ForecastWeather tmp = data.get(position);
		Bitmap bit = tmp.getBitmap();
		if (bit != null) {
			item.img.setImageBitmap(bit);
		} else {
			item.img.setImageBitmap(defaultBitmap);
		}
		String tmp_info = tmp.getDayWeek() + "\n" + tmp.getLow() + "~"
				+ tmp.getHigh() + "\n" + tmp.getCondition();
		item.info.setText(tmp_info);
		return convertView;
	}

	private class WeatherItem {
		ImageView img;
		TextView info;
	}
}
