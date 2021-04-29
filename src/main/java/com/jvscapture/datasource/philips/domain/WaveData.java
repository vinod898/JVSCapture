package com.jvscapture.datasource.philips.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WaveData {

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS")
	private Date time;
	private long relativetime;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS")
	private Date systemlocaltime;
	private double value;
	private long timeStamp;
	private String physioID;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public long getRelativetime() {
		return relativetime;
	}

	public void setRelativetime(long relativetime) {
		this.relativetime = relativetime;
	}

	public Date getSystemlocaltime() {
		return systemlocaltime;
	}

	public void setSystemlocaltime(Date systemlocaltime) {
		this.systemlocaltime = systemlocaltime;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getPhysioID() {
		return physioID;
	}

	public void setPhysioID(String physioID) {
		this.physioID = physioID;
	}

	@Override
	public String toString() {
		return "WaveData [time=" + time + ", relativetime=" + relativetime + ", systemlocaltime=" + systemlocaltime
				+ ", value=" + value + ", timeStamp=" + timeStamp + "]";
	}

	public String toCSV() {
		return time + "," + relativetime + "," + systemlocaltime + "," + timeStamp + "," + value;
	}

}
