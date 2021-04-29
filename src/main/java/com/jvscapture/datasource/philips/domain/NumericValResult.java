package com.jvscapture.datasource.philips.domain;

import java.util.Date;

public class NumericValResult {

	private Date timestamp;
	private long relativetimestamp;
	private Date systemLocalTime;
	private String physioID;
	private String value;
	private String deviceID;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public long getRelativetimestamp() {
		return relativetimestamp;
	}

	public void setRelativetimestamp(long relativetimestamp) {
		this.relativetimestamp = relativetimestamp;
	}

	public Date getSystemLocalTime() {
		return systemLocalTime;
	}

	public void setSystemLocalTime(Date systemLocalTime) {
		this.systemLocalTime = systemLocalTime;
	}

	public String getPhysioID() {
		return physioID;
	}

	public void setPhysioID(String physioID) {
		this.physioID = physioID;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

}
