package com.jvscapture.datasource.philips.domain;

public class ROIVapdu {
	private short inovkeId;
	private short commandType;
	private short length;

	public short getInovkeId() {
		return inovkeId;
	}

	public void setInovkeId(short inovkeId) {
		this.inovkeId = inovkeId;
	}

	public short getCommandType() {
		return commandType;
	}

	public void setCommandType(short commandType) {
		this.commandType = commandType;
	}

	public short getLength() {
		return length;
	}

	public void setLength(short length) {
		this.length = length;
	}

}
