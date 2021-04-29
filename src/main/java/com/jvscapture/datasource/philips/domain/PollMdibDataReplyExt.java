package com.jvscapture.datasource.philips.domain;

public class PollMdibDataReplyExt {

	public int pollNumber;
	public int sequenceNo;
	public long relTimestamp;
	public AbsoluteTime absTimestamp;
	public ObjectType type = new ObjectType();
	public int polledAttrGrp;

	public int getPollNumber() {
		return pollNumber;
	}

	public void setPollNumber(int pollNumber) {
		this.pollNumber = pollNumber;
	}

	public int getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public long getRelTimestamp() {
		return relTimestamp;
	}

	public void setRelTimestamp(long relTimestamp) {
		this.relTimestamp = relTimestamp;
	}

	public AbsoluteTime getAbsTimestamp() {
		return absTimestamp;
	}

	public void setAbsTimestamp(AbsoluteTime absTimestamp) {
		this.absTimestamp = absTimestamp;
	}

	public ObjectType getType() {
		return type;
	}

	public void setType(ObjectType type) {
		this.type = type;
	}

	public int getPolledAttrGrp() {
		return polledAttrGrp;
	}

	public void setPolledAttrGrp(int polledAttrGrp) {
		this.polledAttrGrp = polledAttrGrp;
	}

}
