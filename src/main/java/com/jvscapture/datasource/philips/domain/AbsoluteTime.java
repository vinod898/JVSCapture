package com.jvscapture.datasource.philips.domain;

public class AbsoluteTime {
	private byte century;
	private byte year;
	private byte month;
	private byte day;
	private byte hour;
	private byte minute;
	private byte second;
	private byte fraction;

	public byte getCentury() {
		return century;
	}

	public void setCentury(byte century) {
		this.century = century;
	}

	public byte getYear() {
		return year;
	}

	public void setYear(byte year) {
		this.year = year;
	}

	public byte getMonth() {
		return month;
	}

	public void setMonth(byte month) {
		this.month = month;
	}

	public byte getDay() {
		return day;
	}

	public void setDay(byte day) {
		this.day = day;
	}

	public byte getHour() {
		return hour;
	}

	public void setHour(byte hour) {
		this.hour = hour;
	}

	public byte getMinute() {
		return minute;
	}

	public void setMinute(byte minute) {
		this.minute = minute;
	}

	public byte getSecond() {
		return second;
	}

	public void setSecond(byte second) {
		this.second = second;
	}

	public byte getFraction() {
		return fraction;
	}

	public void setFraction(byte fraction) {
		this.fraction = fraction;
	}

};