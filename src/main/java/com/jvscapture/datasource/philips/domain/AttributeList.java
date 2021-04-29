package com.jvscapture.datasource.philips.domain;

public class AttributeList {
	private int count;
	private int length;
	// private Ava [] value = new Ava[1];
	private Ava value1; // null placeholder
	private byte[] avaobjectsarray;

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

	public Ava getValue1() {
		return value1;
	}

	public void setValue1(Ava value1) {
		this.value1 = value1;
	}

	public byte[] getAvaobjectsarray() {
		return avaobjectsarray;
	}

	public void setAvaobjectsarray(byte[] avaobjectsarray) {
		this.avaobjectsarray = avaobjectsarray;
	}

}
