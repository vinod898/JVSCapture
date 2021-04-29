package com.jvscapture.datasource.philips.domain;

public class SingleContextPoll {

	private int contextId;
	private int count;
	private int length;
	// private ObservationPoll [] value = new ObservationPoll[1];
	private ObservationPoll value1; // null placeholder
	private byte[] obPollObjectsArray;

	public int getContextId() {
		return contextId;
	}

	public void setContextId(int contextId) {
		this.contextId = contextId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public ObservationPoll getValue1() {
		return value1;
	}

	public void setValue1(ObservationPoll value1) {
		this.value1 = value1;
	}

	public byte[] getObPollObjectsArray() {
		return obPollObjectsArray;
	}

	public void setObPollObjectsArray(byte[] obPollObjectsArray) {
		this.obPollObjectsArray = obPollObjectsArray;
	}

}
