package com.jvscapture.datasource.philips.domain;

public class RemoteOpErrors {

	private short NO_SUCH_OBJECT_CLASS = 0;
	private short NO_SUCH_OBJECT_INSTANCE = 1;
	private short ACCESS_DENIED = 2;
	private short GET_LIST_ERROR = 7;
	private short SET_LIST_ERROR = 8;
	private short NO_SUCH_ACTION = 9;
	private short PROCESSING_FAILURE = 10;
	private short INVALID_ARGUMENT_VALUE = 15;
	private short INVALID_SCOPE = 16;
	private short INVALID_OBJECT_INSTANCE = 17;
	private short NONE = 255;

	public short getNO_SUCH_OBJECT_CLASS() {
		return NO_SUCH_OBJECT_CLASS;
	}

	public void setNO_SUCH_OBJECT_CLASS(short nO_SUCH_OBJECT_CLASS) {
		NO_SUCH_OBJECT_CLASS = nO_SUCH_OBJECT_CLASS;
	}

	public short getNO_SUCH_OBJECT_INSTANCE() {
		return NO_SUCH_OBJECT_INSTANCE;
	}

	public void setNO_SUCH_OBJECT_INSTANCE(short nO_SUCH_OBJECT_INSTANCE) {
		NO_SUCH_OBJECT_INSTANCE = nO_SUCH_OBJECT_INSTANCE;
	}

	public short getACCESS_DENIED() {
		return ACCESS_DENIED;
	}

	public void setACCESS_DENIED(short aCCESS_DENIED) {
		ACCESS_DENIED = aCCESS_DENIED;
	}

	public short getGET_LIST_ERROR() {
		return GET_LIST_ERROR;
	}

	public void setGET_LIST_ERROR(short gET_LIST_ERROR) {
		GET_LIST_ERROR = gET_LIST_ERROR;
	}

	public short getSET_LIST_ERROR() {
		return SET_LIST_ERROR;
	}

	public void setSET_LIST_ERROR(short sET_LIST_ERROR) {
		SET_LIST_ERROR = sET_LIST_ERROR;
	}

	public short getNO_SUCH_ACTION() {
		return NO_SUCH_ACTION;
	}

	public void setNO_SUCH_ACTION(short nO_SUCH_ACTION) {
		NO_SUCH_ACTION = nO_SUCH_ACTION;
	}

	public short getPROCESSING_FAILURE() {
		return PROCESSING_FAILURE;
	}

	public void setPROCESSING_FAILURE(short pROCESSING_FAILURE) {
		PROCESSING_FAILURE = pROCESSING_FAILURE;
	}

	public short getINVALID_ARGUMENT_VALUE() {
		return INVALID_ARGUMENT_VALUE;
	}

	public void setINVALID_ARGUMENT_VALUE(short iNVALID_ARGUMENT_VALUE) {
		INVALID_ARGUMENT_VALUE = iNVALID_ARGUMENT_VALUE;
	}

	public short getINVALID_SCOPE() {
		return INVALID_SCOPE;
	}

	public void setINVALID_SCOPE(short iNVALID_SCOPE) {
		INVALID_SCOPE = iNVALID_SCOPE;
	}

	public short getINVALID_OBJECT_INSTANCE() {
		return INVALID_OBJECT_INSTANCE;
	}

	public void setINVALID_OBJECT_INSTANCE(short iNVALID_OBJECT_INSTANCE) {
		INVALID_OBJECT_INSTANCE = iNVALID_OBJECT_INSTANCE;
	}

	public short getNONE() {
		return NONE;
	}

	public void setNONE(short nONE) {
		NONE = nONE;
	}

}
