package com.jvscapture.datasource.philips.domain;

public class ObservationPoll {

	private int objHandle;
	private AttributeList attributes;
	private byte[] avaObjectsArray;

	public int getObjHandle() {
		return objHandle;
	}

	public void setObjHandle(int objHandle) {
		this.objHandle = objHandle;
	}

	public AttributeList getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributeList attributes) {
		this.attributes = attributes;
	}

	public byte[] getAvaObjectsArray() {
		return avaObjectsArray;
	}

	public void setAvaObjectsArray(byte[] avaObjectsArray) {
		this.avaObjectsArray = avaObjectsArray;
	}

}
