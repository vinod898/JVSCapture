package com.jvscapture.datasource.philips.domain;

public class NuObsValue {

	private int physioId;
	private int state;
	private int unitCode;
	private int value;

	public int getPhysioId() {
		return physioId;
	}

	public void setPhysioId(int physioId) {
		this.physioId = physioId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(int unitCode) {
		this.unitCode = unitCode;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
