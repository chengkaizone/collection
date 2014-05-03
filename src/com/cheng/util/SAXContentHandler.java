package com.cheng.util;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.os.Handler;
import android.os.Message;

import com.cheng.entity.CurrentWeather;
import com.cheng.entity.ForecastWeather;
import com.cheng.entity.WeatherInfo;

public class SAXContentHandler extends DefaultHandler {
	private String head = "http://www.google.com";
	private Handler handler;
	private List<ForecastWeather> data;
	private int curId = -1;
	private String preLabel = "";
	private WeatherInfo info = new WeatherInfo();
	private CurrentWeather cur = new CurrentWeather();

	public SAXContentHandler(Handler handler) {
		this.handler = handler;
		this.data = new ArrayList<ForecastWeather>();
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		System.out.println("获取标签值--->");
	}

	public void endDocument() throws SAXException {
		System.out.println("结束文档--->");
		// 发送解析完文档的消息；
		sendMessage();
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.println("结束元素--->");
		if (localName.equals("forecast_information")) {
			sendInfoMessage();
		}
		if (localName.equals("current_conditions")) {
			sendCurMessage();
		}
	}

	public void startDocument() throws SAXException {
		System.out.println("开始文档--->");
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attr) throws SAXException {
		System.out.println("开始元素--->");
		System.out.println(localName + "-------->");
		if (localName.equals("weather")) {
			preLabel = localName;
		} else if (localName.equals("forecast_information")) {
			preLabel = localName;
		} else if (localName.equals("current_conditions")) {
			preLabel = localName;
		} else if (localName.equals("forecast_conditions")) {
			preLabel = localName;
			ForecastWeather fore = new ForecastWeather();
			data.add(fore);
			curId++;
		}
		if (preLabel.equals("forecast_information")) {
			if (localName.equals("city")) {
				info.setCity(attr.getValue(0));
			} else if (localName.equals("postal_code")) {
				info.setPostal(attr.getValue(0));
			} else if (localName.equals("latitude_e6")) {
				info.setLatitude(attr.getValue(0));
			} else if (localName.equals("longitude_e6")) {
				info.setLongitude(attr.getValue(0));
			} else if (localName.equals("forecast_date")) {
				info.setForecastDate(attr.getValue(0));
			} else if (localName.equals("current_date_time")) {
				info.setCurrentTime(attr.getValue(0));
			} else if (localName.equals("unit_system")) {
				info.setUnitSystem(attr.getValue(0));
			}
		}
		if (preLabel.equals("current_conditions")) {
			if (localName.equals("condition")) {
				cur.setCondition(attr.getValue(0));
			} else if (localName.equals("temp_f")) {
				cur.setTempf(attr.getValue(0));
			} else if (localName.equals("temp_c")) {
				cur.setTempc(attr.getValue(0));
			} else if (localName.equals("humidity")) {
				cur.setHumidity(attr.getValue(0));
			} else if (localName.equals("icon")) {
				cur.setIcon(head + attr.getValue(0));
			} else if (localName.equals("wind_condition")) {
				cur.setWind(attr.getValue(0));
			}
		}
		if (preLabel.equals("forecast_conditions")) {
			if (localName.equals("day_of_week")) {
				data.get(curId).setDayWeek(attr.getValue(0));
			} else if (localName.equals("low")) {
				data.get(curId).setLow(attr.getValue(0));
			} else if (localName.equals("high")) {
				data.get(curId).setHigh(attr.getValue(0));
			} else if (localName.equals("icon")) {
				data.get(curId).setIcon(head + attr.getValue(0));
			} else if (localName.equals("condition")) {
				data.get(curId).setCondition(attr.getValue(0));
			}
		}
	}

	private void sendMessage() {
		Message msg = new Message();
		msg.obj = data;
		msg.arg1 = 0x555;
		handler.sendMessage(msg);
	}

	private void sendInfoMessage() {
		Message msg = new Message();
		msg.arg1 = 0x111;
		msg.obj = info;
		handler.sendMessage(msg);
	}

	private void sendCurMessage() {
		Message msg = new Message();
		msg.arg1 = 0x222;
		msg.obj = cur;
		handler.sendMessage(msg);
	}
}
