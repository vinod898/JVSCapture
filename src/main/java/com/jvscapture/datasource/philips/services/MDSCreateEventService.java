package com.jvscapture.datasource.philips.services;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Date;

import com.jvscapture.datasource.philips.constants.IntelliVue.DataConstants;
import com.jvscapture.datasource.philips.domain.Ava;

public class MDSCreateEventService {
	private static MDSCreateEventService single_instance = null;

	PollService pollService = PollService.getInstance();

	private MDSCreateEventService() {
	}

	public static MDSCreateEventService getInstance() {
		if (single_instance == null)
			single_instance = new MDSCreateEventService();

		return single_instance;
	}

	private OutputStream output;

	public void init(OutputStream output) {
		this.output = output;
	}

	public void parseMDSCreateEventReport(byte[] byteArray) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);

		byte[] header = new byte[34];
		byteBuffer.get(header);

		int attriblist_count = (int) byteBuffer.getShort();
		int attriblist_length = (int) byteBuffer.getShort();
		int avaobjectscount = attriblist_count;

		if (avaobjectscount > 0) {
			byte[] attriblistobjects = new byte[attriblist_length];

			byteBuffer.get(attriblistobjects);

			ByteBuffer byteBuffer2 = ByteBuffer.wrap(attriblistobjects);

			for (int i = 0; i < avaobjectscount; i++) {
				Ava avaobjects = new Ava();
				decodeMDSAttribObjects(avaobjects, byteBuffer2);
			}
		}

	}

	private void decodeMDSAttribObjects(Ava avaobject, ByteBuffer byteBuffer) {
		avaobject.setAttribute_id((int) byteBuffer.getShort());
		avaobject.setLength((int) byteBuffer.getShort()); // avaobject.attribute_val =
															// correctendianshortus(binreader4.ReadUInt16());
		if (avaobject.getLength() > 0) {
			byte[] avaattribobjects = new byte[avaobject.getLength()];
			byteBuffer.get(avaattribobjects);

			switch (avaobject.getAttribute_id()) {
			// Get Date and Time
			case DataConstants.NOM_ATTR_TIME_ABS:
				Date m_baseDateTime = pollService.getAbsoluteTimeFromBCDFormat(avaattribobjects);
				pollService.m_baseDateTime = m_baseDateTime;
				break;
			// Get Relative Time attribute
			case DataConstants.NOM_ATTR_TIME_REL:
				pollService.getBaselineRelativeTimestamp(avaattribobjects);
				break;
			// Get Patient demographics
			case DataConstants.NOM_ATTR_PT_ID:
				break;
			case DataConstants.NOM_ATTR_PT_NAME_GIVEN:
				break;
			case DataConstants.NOM_ATTR_PT_NAME_FAMILY:
				break;
			case DataConstants.NOM_ATTR_PT_DOB:
				break;
			}
		}

	}

	public void sendMDSCreateEventResult() {
		try {
			output.write(DataConstants.mds_create_resp_msg);
		} catch (IOException e) {
			System.out.println("sendMDSCreateEventResult Error");
		}

	}

}
