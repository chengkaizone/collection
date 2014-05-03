package com.cheng.demo;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import android.app.ProgressDialog;
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
 * 天气解析的注意事项先获取数据字符串；得到字符串后再解析数据显示比直接通过输入流解析要快
 * 
 * @author Administrator
 * 
 */
public class WeatherInfoTest extends CommonActivity implements OnClickListener {
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
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				Toast.makeText(WeatherInfoTest.this, "网络异常", 2000).show();
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
				String str = (String) msg.obj;
				System.out.println("str---->" + str);
				List<ForecastWeather> data = parseXML(str);
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
				+ cur.getCondition() + "\n" + cur.getHumidity() + "\n风向："
				+ cur.getWind();
		text_info.setText(cur_info);
		System.out.println("cur_info--->" + cur_info);
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
		adapter = new WeatherAdapter(WeatherInfoTest.this, data);
		grid.setAdapter(adapter);
		executor.execute(new WeatherThread(handler, adapter));
	}

	private List<ForecastWeather> parseXML(String data) {
		System.out.println("data------>" + data);
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new StringReader(data));
			Element declare = doc.getRootElement();
			Element root = declare.element("weather");
			Element main = root.element("forecast_information");
			info = new WeatherInfo();
			info.setElement(main.getName());
			info.setCity(main.element("city").attributeValue("data"));
			info.setPostal(main.element("postal_code").attributeValue("data"));
			info.setLatitude(main.element("latitude_e6").attributeValue("data"));
			info.setLongitude(main.element("longitude_e6").attributeValue(
					"data"));
			info.setForecastDate(main.element("forecast_date").attributeValue(
					"data"));
			info.setCurrentTime(main.element("current_date_time")
					.attributeValue("data"));
			info.setUnitSystem(main.element("unit_system").attributeValue(
					"data"));

			Element current = root.element("current_conditions");
			cur = new CurrentWeather();
			cur.setElement(current.getName());
			cur.setCondition(current.element("condition")
					.attributeValue("data"));
			cur.setTempf(current.element("temp_f").attributeValue("data"));
			cur.setTempc(current.element("temp_c").attributeValue("data"));
			cur.setHumidity(current.element("humidity").attributeValue("data"));
			cur.setIcon(url_head
					+ current.element("icon").attributeValue("data"));
			cur.setWind(current.element("wind_condition")
					.attributeValue("data"));

			List<Element> eles = root.elements("forecast_conditions");
			List<ForecastWeather> forecasts = new ArrayList<ForecastWeather>();
			for (Element e : eles) {
				ForecastWeather fore = new ForecastWeather();
				fore.setElement(e.getName());
				fore.setDayWeek(e.element("day_of_week").attributeValue("data"));
				fore.setLow(e.element("low").attributeValue("data"));
				fore.setHigh(e.element("high").attributeValue("data"));
				fore.setIcon(url_head
						+ e.element("icon").attributeValue("data"));
				fore.setCondition(e.element("condition").attributeValue("data"));
				forecasts.add(fore);
			}
			return forecasts;
		} catch (Exception e) {
			System.out.println("获取数据异常！");
			e.printStackTrace();
		}
		return null;
	}

	private List<ForecastWeather> parseXml(String url) {
		System.out.println("url--->" + url);
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(url);
			Element declare = doc.getRootElement();
			Element root = declare.element("weather");
			Element main = root.element("forecast_information");
			info = new WeatherInfo();
			info.setElement(main.getName());
			info.setCity(main.element("city").attributeValue("data"));
			info.setPostal(main.element("postal_code").attributeValue("data"));
			info.setLatitude(main.element("latitude_e6").attributeValue("data"));
			info.setLongitude(main.element("longitude_e6").attributeValue(
					"data"));
			info.setForecastDate(main.element("forecast_date").attributeValue(
					"data"));
			info.setCurrentTime(main.element("current_date_time")
					.attributeValue("data"));
			info.setUnitSystem(main.element("unit_system").attributeValue(
					"data"));

			Element current = root.element("current_conditions");
			cur = new CurrentWeather();
			cur.setElement(current.getName());
			cur.setCondition(current.element("condition")
					.attributeValue("data"));
			cur.setTempf(current.element("temp_f").attributeValue("data"));
			cur.setTempc(current.element("temp_c").attributeValue("data"));
			cur.setHumidity(current.element("humidity").attributeValue("data"));
			cur.setIcon(url_head
					+ current.element("icon").attributeValue("data"));
			cur.setWind(current.element("wind_condition")
					.attributeValue("data"));

			List<Element> eles = root.elements("forecast_conditions");
			List<ForecastWeather> forecasts = new ArrayList<ForecastWeather>();
			for (Element e : eles) {
				ForecastWeather fore = new ForecastWeather();
				fore.setElement(e.getName());
				fore.setDayWeek(e.element("day_of_week").attributeValue("data"));
				fore.setLow(e.element("low").attributeValue("data"));
				fore.setHigh(e.element("high").attributeValue("data"));
				fore.setIcon(url_head
						+ e.element("icon").attributeValue("data"));
				fore.setCondition(e.element("condition").attributeValue("data"));
				forecasts.add(fore);
			}
			return forecasts;
		} catch (Exception e) {
			System.out.println("获取数据异常！");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onClick(View v) {
		System.out.println("正在查询...");
		String city = input.getText().toString().trim();
		if (city.equals("")) {
			Toast.makeText(WeatherInfoTest.this, "请输入城市名", 2000).show();
			return;
		}
		dialog = ProgressDialog.show(WeatherInfoTest.this, "", "正在查询！请稍后...",
				true);
		dialog.setCancelable(true);
		final String url = url_head + url_weather + city;
		new Thread() {
			public void run() {
				String data = FileUtil.downFile(url);
				if (data != null) {
					Message msg = new Message();
					msg.obj = data;
					handler.sendMessage(msg);
					System.out.println("成功返回数据");
				} else {
					handler.sendEmptyMessage(0x123);
					System.out.println("网络异常！");
				}
			}
		}.start();
	}
}
