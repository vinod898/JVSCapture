package com.jvscapture.datasource.philips.domain;

public class PollInfoList {

	private int count;
	private int length;
	// private SingleContextPoll [] value= new SingleContextPoll[1];
	private SingleContextPoll value1; // null placeholder
	private byte[] scpollarray;

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

	public SingleContextPoll getValue1() {
		return value1;
	}

	public void setValue1(SingleContextPoll value1) {
		this.value1 = value1;
	}

	public byte[] getScpollarray() {
		return scpollarray;
	}

	public void setScpollarray(byte[] scpollarray) {
		this.scpollarray = scpollarray;
	}

}
