package com.jvscapture.datasource.philips.services;

import java.io.OutputStream;

import java.nio.ByteBuffer;

import com.jvscapture.datasource.DataSourceWriter;
import com.jvscapture.datasource.philips.InputData;
import com.jvscapture.datasource.philips.constants.IntelliVue.DataConstants;

public class PocketProcessService {

	private static PocketProcessService single_instance = null;

	private PocketProcessService() {
	}

	public static PocketProcessService getInstance() {
		if (single_instance == null)
			single_instance = new PocketProcessService();

		return single_instance;
	}

	MDSCreateEventService mdsCreateEvent;
	PollService pollService;
	public void init(OutputStream outputSerialPortWriter, InputData inputData, DataSourceWriter outputDataWriter) {
		mdsCreateEvent = MDSCreateEventService.getInstance();
		pollService = PollService.getInstance();
		mdsCreateEvent.init(outputSerialPortWriter);
		pollService.init(inputData,outputDataWriter);

	}

	public void process(byte[] frameData) {

		byte[] sessionheader = new byte[4];
		ByteBuffer byteBuffer = ByteBuffer.wrap(frameData);
		byteBuffer.get(sessionheader);

		int roapdu_type = (int) byteBuffer.getShort();

		switch (roapdu_type) {
		case DataConstants.ROIV_APDU:
			// This is an MDS create event, answer with create response
			mdsCreateEvent.parseMDSCreateEventReport(frameData);
			mdsCreateEvent.sendMDSCreateEventResult();
			break;
		case DataConstants.RORS_APDU:
			pollService.checkPollPacketActionType(frameData);
			break;
		case DataConstants.RORLS_APDU:
			pollService.checkLinkedPollPacketActionType(frameData);
			break;
		case DataConstants.ROER_APDU:
			break;
		default:
			break;
		}
	}

}

