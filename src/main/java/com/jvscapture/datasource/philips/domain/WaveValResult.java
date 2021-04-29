package com.jvscapture.datasource.philips.domain;

import java.util.Date;

public class WaveValResult {

	private Date timestamp;
	private long relativetimestamp;
	private Date systemLocalTime;
	private String physioID;
	private byte[] value;
	private String deviceID;
	private int obPollHandle;
	private SaSpec saSpecData = null;
	private SaCalibData16 saCalibData = null;

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

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public int getObPollHandle() {
		return obPollHandle;
	}

	public void setObPollHandle(int obPollHandle) {
		this.obPollHandle = obPollHandle;
	}

	public SaSpec getSaSpecData() {
		return saSpecData;
	}

	public void setSaSpecData(SaSpec saSpecData) {
		this.saSpecData = saSpecData;
	}

	public SaCalibData16 getSaCalibData() {
		return saCalibData;
	}

	public void setSaCalibData(SaCalibData16 saCalibData) {
		this.saCalibData = saCalibData;
	}

}
