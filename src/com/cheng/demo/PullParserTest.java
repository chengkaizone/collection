package com.cheng.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.adapter.WeatherAdapter;
import com.cheng.entity.CurrentWeather;
import com.cheng.entity.ForecastWeather;
import com.cheng.entity.WeatherInfo;
import com.cheng.util.FileUtil;
import com.cheng.util.WeatherThread;

/**
 * pull解析xml文件注意next方法的调用时机；属性也在next的调用范围内 所以有几个属性就要调用几次next方法
 * 
 * @author Administrator
 * 
 */
public class PullParserTest extends CommonActivity implements OnClickListener {
	int i = 0;
	String url_head = "http://www.google.com";
	String url_weather = "/ig/api?weather=";
	EditText input;
	Button query;
	ImageView image;
	TextView text_info;
	GridView grid;
	WeatherInfo info;
	CurrentWeather cur;
	ProgressDialog dialog;
	Bitmap defaultBitmap;
	private ThreadPoolExecutor executor;
	WeatherAdapter adapter;
	XmlResourceParser pull;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				Toast.makeText(PullParserTest.this, "网络异常", 2000).show();
				dialog.cancel();
				return;
			}
			if (msg.what == 0x666) {
				image.setImageBitmap(defaultBitmap);
			}
			if (msg.what == 0x999) {
				adapter.notifyDataSetChanged();
			}
			try {
				List<ForecastWeather> data = (List<ForecastWeather>) msg.obj;
				updateGridAdapter(data);
				dialog.cancel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.weather_main);
		input = (EditText) findViewById(R.id.weather_input);
		query = (Button) findViewById(R.id.weather_query);
		image = (ImageView) findViewById(R.id.weather_image);
		text_info = (TextView) findViewById(R.id.weather_cur_info);
		grid = (GridView) findViewById(R.id.weather_grid);
		query.setOnClickListener(this);
		defaultBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_menu_update);
		executor = new ThreadPoolExecutor(5, 10, 3, TimeUnit.SECONDS,
				new LinkedBlockingQueue());
	}

	private void updateGridAdapter(List<ForecastWeather> data) {
		String cur_info = "当前城市：" + info.getCity() + "\n时间："
				+ info.getCurrentTime() + "\n温度：" + cur.getTempc() + "~"
				+ cur.getTempf() + cur.getTempc() + "\n天气："
				+ cur.getCondition() + "\n" + cur.getHumidity() + "\n"
				+ cur.getWind();
		text_info.setText(cur_info);
		image.setImageBitmap(defaultBitmap);
		new Thread() {
			public void run() {
				Bitmap tmp = FileUtil.getBitmap(cur.getIcon());
				if (tmp != null) {
					defaultBitmap = tmp;
					handler.sendEmptyMessage(0x666);
				}
			}
		}.start();
		adapter = new WeatherAdapter(PullParserTest.this, data);
		grid.setAdapter(adapter);
		executor.execute(new WeatherThread(handler, adapter));
	}

	private List<ForecastWeather> parseXML() {
		pull = this.getResources().getXml(R.xml.weather);
		List<ForecastWeather> forecasts = new ArrayList<ForecastWeather>();
		info = new WeatherInfo();
		cur = new CurrentWeather();
		try {
			while (pull.getEventType() != XmlResourceParser.END_DOCUMENT) {
				// 遇到开始标签
				switch (pull.getEventType()) {
				case XmlResourceParser.START_TAG:
					String tagName = pull.getName();
					if (tagName.equals("forecast_information")) {
						while (pull.nextTag() == XmlResourceParser.START_TAG) {
							String name = pull.getName();
							if (name.equals("city")) {
								info.setCity(pull.getAttributeValue(0));
							}
							if (name.equals("postal_code")) {
								info.setCurrentTime(pull.getAttributeValue(0));
							}
							if (name.equals("forecast_date")) {
								info.setForecastDate(pull.getAttributeValue(0));
							}
							if (name.equals("current_date_time")) {
								info.setCurrentTime(pull.getAttributeValue(0));
							}
							if (name.equals("unit_system")) {
								info.setUnitSystem(pull.getAttributeValue(0));
							}
							if (name.equals("latitude_e6")) {
								info.setLatitude(pull.getAttributeValue(0));
							}
							if (name.equals("longitude_e6")) {
								info.setLongitude(pull.getAttributeValue(0));
							}
							pull.next();
						}
						pull.next();
						tagName = pull.getName();
					}
					if (tagName.equals("current_conditions")) {
						cur.setElement(pull.getName());
						while (pull.nextTag() == XmlResourceParser.START_TAG) {
							String name = pull.getName();
							if (name.equals("condition")) {
								cur.setCondition(pull.getAttributeValue(0));
							}
							if (name.equals("temp_f")) {
								cur.setTempf(pull.getAttributeValue(0));
							}
							if (name.equals("temp_c")) {
								cur.setTempc(pull.getAttributeValue(0));
							}
							if (name.equals("humidity")) {
								cur.setHumidity(pull.getAttributeValue(0));
							}
							if (name.equals("icon")) {
								cur.setIcon(url_head
										+ pull.getAttributeValue(0));
							}
							if (name.equals("wind_condition")) {
								cur.setWind(pull.getAttributeValue(0));
							}
							pull.next();
						}
						pull.next();
						tagName = pull.getName();
					}
					while (tagName.equals("forecast_conditions")) {
						ForecastWeather forecast = new ForecastWeather();
						forecast.setElement(tagName);
						while (pull.nextTag() == XmlResourceParser.START_TAG) {
							String name = pull.getName();
							if (name.equals("day_of_week")) {
								forecast.setDayWeek(pull.getAttributeValue(0));
							}
							if (name.equals("low")) {
								forecast.setLow(pull.getAttributeValue(0));
							}
							if (name.equals("high")) {
								forecast.setHigh(pull.getAttributeValue(0));
							}
							if (name.equals("icon")) {
								forecast.setIcon(url_head
										+ pull.getAttributeValue(0));
							}
							if (name.equals("condition")) {
								forecast.setCondition(pull.getAttributeValue(0));
							}
							pull.next();
						}
						forecasts.add(forecast);
						pull.next();
						tagName = pull.getName();
					}
					break;
				case XmlResourceParser.END_TAG:
					break;
				case XmlResourceParser.TEXT:
					break;
				}
				pull.next();
			}
		} catch (XmlPullParserException e) {
			System.out.println("shujujiexiyichang--->");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forecasts;
	}

	public void onClick(View v) {
		dialog = ProgressDialog.show(PullParserTest.this, "", "正在解析！请稍后...");
		dialog.setCancelable(true);
		new Thread() {
			public void run() {
				List<ForecastWeather> data = parseXML();
				System.out.println("data.size()---->" + data.size());
				if (data.size() >= 1) {
					Message msg = new Message();
					msg.obj = data;
					handler.sendMessage(msg);
				} else {
					handler.sendEmptyMessage(0x123);
					System.out.println("数据解析异常！");
				}
			}
		}.start();
	}
}
