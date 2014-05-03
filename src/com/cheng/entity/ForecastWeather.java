package com.cheng.entity;

import java.io.Serializable;

import android.graphics.Bitmap;

public class ForecastWeather implements Serializable {
	private String element;
	private String dayWeek;
	private String low;
	private String high;
	private String icon;
	private String condition;
	private Bitmap bitmap;

	public String getDayWeek() {
		return dayWeek;
	}

	public void setDayWeek(String dayWeek) {
		this.dayWeek = dayWeek;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
