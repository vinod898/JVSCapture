package com.jvscapture.datasource.philips.domain;

public class SaSpec {
	private int arraySize;
	private byte sampleSize;
	private byte significantBits;
	private int saFlags;
	private int obpollHandle;

	public int getArraySize() {
		return arraySize;
	}

	public void setArraySize(int arraySize) {
		this.arraySize = arraySize;
	}

	public byte getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(byte sampleSize) {
		this.sampleSize = sampleSize;
	}

	public byte getSignificantBits() {
		return significantBits;
	}

	public void setSignificantBits(byte significantBits) {
		this.significantBits = significantBits;
	}

	public int getSaFlags() {
		return saFlags;
	}

	public void setSaFlags(int saFlags) {
		this.saFlags = saFlags;
	}

	public int getObpollHandle() {
		return obpollHandle;
	}

	public void setObpollHandle(int obpollHandle) {
		this.obpollHandle = obpollHandle;
	}

}