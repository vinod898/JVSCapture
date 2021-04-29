package com.jvscapture.datasource.philips.domain;

public class SaCalibData16 {

	private double lowerAbsoluteValue;
	private double upperAbsoluteValue;
	private int lowerScaledValue;
	private int upperScaledValue;
	private int increment;
	private int calType;
	private int obpollHandle;
	private int physioId;

	public double getLowerAbsoluteValue() {
		return lowerAbsoluteValue;
	}

	public void setLowerAbsoluteValue(double lowerAbsoluteValue) {
		this.lowerAbsoluteValue = lowerAbsoluteValue;
	}

	public double getUpperAbsoluteValue() {
		return upperAbsoluteValue;
	}

	public void setUpperAbsoluteValue(double upperAbsoluteValue) {
		this.upperAbsoluteValue = upperAbsoluteValue;
	}

	public int getLowerScaledValue() {
		return lowerScaledValue;
	}

	public void setLowerScaledValue(int lowerScaledValue) {
		this.lowerScaledValue = lowerScaledValue;
	}

	public int getUpperScaledValue() {
		return upperScaledValue;
	}

	public void setUpperScaledValue(int upperScaledValue) {
		this.upperScaledValue = upperScaledValue;
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public int getCalType() {
		return calType;
	}

	public void setCalType(int calType) {
		this.calType = calType;
	}

	public int getObpollHandle() {
		return obpollHandle;
	}

	public void setObpollHandle(int obpollHandle) {
		this.obpollHandle = obpollHandle;
	}

	public int getPhysioId() {
		return physioId;
	}

	public void setPhysioId(int physioId) {
		this.physioId = physioId;
	}

}
