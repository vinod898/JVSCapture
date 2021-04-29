package com.jvscapture.datasource.philips.domain;

public class SaObsValue {

	private int physioId;
	private int state;
	private int length;
	// public byte [] value = new byte[1];
	private byte value;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public int getPhysioId() {
		return physioId;
	}

	public void setPhysioId(int physioId) {
		this.physioId = physioId;
	}

}
