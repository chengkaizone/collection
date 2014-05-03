package com.cheng.entity;

import java.io.Serializable;

public class WeatherInfo implements Serializable {
	private String element;
	private String city;
	private String postal;
	private String latitude;
	private String longitude;
	private String forecastDate;
	private String currentTime;
	private String unitSystem;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getForecastDate() {
		return forecastDate;
	}

	public void setForecastDate(String forecastDate) {
		this.forecastDate = forecastDate;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public String getUnitSystem() {
		return unitSystem;
	}

	public void setUnitSystem(String unitSystem) {
		this.unitSystem = unitSystem;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}
}
