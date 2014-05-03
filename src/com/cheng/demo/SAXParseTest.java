package com.cheng.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ProgressDialog;
import android.content.res.AssetManager;
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

import com.cheng.adapter.SAXWeatherAdapter;
import com.cheng.entity.CurrentWeather;
import com.cheng.entity.ForecastWeather;
import com.cheng.entity.WeatherInfo;
import com.cheng.util.FileUtil;
import com.cheng.util.SAXContentHandler;
import com.cheng.util.SAXWeatherThread;

public class SAXParseTest extends CommonActivity implements OnClickListener {

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
	SAXWeatherAdapter adapter;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				Toast.makeText(SAXParseTest.this, "网络异常", 2000).show();
				dialog.cancel();
				return;
			}
			if (msg.what == 0x666) {
				image.setImageBitmap(defaultBitmap);
			}
			if (msg.what == 0x999) {
				adapter.notifyDataSetChanged();
			}
			if (msg.arg1 == 0x111) {
				info = (WeatherInfo) msg.obj;
			}
			if (msg.arg1 == 0x222) {
				cur = (CurrentWeather) msg.obj;
				updateGridAdapter(null);
			}
			if (msg.arg1 == 0x555) {
				List<ForecastWeather> data = (List<ForecastWeather>) msg.obj;
				updateGridAdapter(data);
				dialog.cancel();
			}
		}
	};

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.sax_weather_main);
		input = (EditText) findViewById(R.id.sax_weather_input);
		query = (Button) findViewById(R.id.sax_weather_query);
		image = (ImageView) findViewById(R.id.sax_weather_image);
		text_info = (TextView) findViewById(R.id.sax_weather_cur_info);
		grid = (GridView) findViewById(R.id.sax_weather_grid);
		info = new WeatherInfo();
		cur = new CurrentWeather();
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
				String icon = cur.getIcon();
				if (icon != null) {
					Bitmap tmp = FileUtil.getBitmap(icon);
					if (tmp != null) {
						defaultBitmap = tmp;
						handler.sendEmptyMessage(0x666);
					}
				} else {
					System.out.println("解析异常！");
				}
			}
		}.start();
		if (data != null) {
			adapter = new SAXWeatherAdapter(SAXParseTest.this, data);
			grid.setAdapter(adapter);
			executor.execute(new SAXWeatherThread(handler, adapter));
		} else {
			// 有网络必有异常此处不用处理
		}
	}

	// 此处开始解析
	private void parseXML(String str) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader reader = factory.newSAXParser().getXMLReader();
			reader.setContentHandler(new SAXContentHandler(handler));
			reader.parse(new InputSource(new StringReader(str)));
		} catch (Exception e) {
			System.out.println("sax解析总是会抛异常--->");
			// Toast.makeText(this,"解析异常",2000).show();
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		System.out.println("正在查询...");
		dialog = ProgressDialog
				.show(SAXParseTest.this, "", "正在查询！请稍后...", true);
		dialog.setCancelable(true);
		String city = input.getText().toString().trim();
		if (city.equals("")) {
			Toast.makeText(SAXParseTest.this, "请输入城市名", 2000).show();
			return;
		}
		final String url = url_head + url_weather + city;
		new Thread() {
			public void run() {
				// String data = getDoc();
				String data = FileUtil.downFile(url);
				if (data != null) {
					parseXML(data);
				}
			}
		}.start();
	}

	private String getDoc() {
		String str = "";
		try {
			AssetManager asset = this.getAssets();
			InputStream in = asset.open("file/weather.xml");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String s = "";
			while ((s = reader.readLine()) != null) {
				str += s;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
