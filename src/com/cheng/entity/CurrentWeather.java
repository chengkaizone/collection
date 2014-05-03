package com.cheng.entity;

import java.io.Serializable;

public class CurrentWeather implements Serializable {
	private String element;
	private String condition;
	private String tempf;
	private String tempc;
	private String humidity;
	private String icon;
	private String wind;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getTempf() {
		return tempf;
	}

	public void setTempf(String tempf) {
		this.tempf = tempf;
	}

	public String getTempc() {
		return tempc;
	}

	public void setTempc(String tempc) {
		this.tempc = tempc;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}
}
