package com.jvscapture.datasource.philips.services;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.primitives.Bytes;
import com.jvscapture.datasource.enums.WaveDataExportPriority;
import com.jvscapture.datasource.philips.constants.IntelliVue;
import com.jvscapture.datasource.philips.constants.IntelliVue.DataConstants;
import com.jvscapture.datasource.philips.domain.AttributeList;
import com.jvscapture.datasource.philips.domain.Ava;
import com.jvscapture.datasource.philips.domain.ObservationPoll;
import com.jvscapture.datasource.philips.domain.PollInfoList;
import com.jvscapture.datasource.philips.domain.ROIVapdu;
import com.jvscapture.datasource.philips.domain.ROapdus;
import com.jvscapture.datasource.philips.domain.SingleContextPoll;
import com.jvscapture.util.BitConverter;

public class UtilServices {
	private static UtilServices single_instance = null;

	private UtilServices() {
	}

	public static UtilServices getInstance() {
		if (single_instance == null)
			single_instance = new UtilServices();

		return single_instance;
	}

	protected int decodeObservationPollObjects(ObservationPoll obpollobject, ByteBuffer byteBuffer3,
			Consumer<Integer> callback) {
		obpollobject.setObjHandle((int) byteBuffer3.getShort());

		callback.accept(obpollobject.getObjHandle());

		AttributeList attributeliststruct = new AttributeList();

		attributeliststruct.setCount((int) byteBuffer3.getShort());
		if (attributeliststruct.getCount() > 0)
			attributeliststruct.setLength((int) byteBuffer3.getShort());

		int avaobjectscount = attributeliststruct.getCount();
		if (attributeliststruct.getLength() > 0) {
			obpollobject.setAvaObjectsArray(new byte[attributeliststruct.getLength()]);
			byteBuffer3.get(obpollobject.getAvaObjectsArray());
		}

		return avaobjectscount;
	}

	protected int decodeSingleContextPollObjects(SingleContextPoll scpoll, ByteBuffer byteBuffer2) {
		scpoll.setContextId((int) byteBuffer2.getShort());
		scpoll.setCount((int) byteBuffer2.getShort());
		// There can be empty singlecontextpollobjects
		// if(scpoll.count>0) scpoll.getLenth() =
		// correctendianshortus(binreader2.ReadUInt16());
		scpoll.setLength((int) byteBuffer2.getShort());

		int obpollobjectscount = scpoll.getCount();
		if (scpoll.getLength() > 0) {
			scpoll.setObPollObjectsArray(new byte[scpoll.getLength()]);
			byteBuffer2.get(scpoll.getObPollObjectsArray());
		}

		return obpollobjectscount;
	}

	protected int decodePollObjects(PollInfoList pollobjects, byte[] packetdata) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(packetdata);

		pollobjects.setCount((int) byteBuffer.getShort());
		if (pollobjects.getCount() > 0)
			pollobjects.setLength((int) byteBuffer.getShort());

		int scpollobjectscount = pollobjects.getCount();
		if (pollobjects.getLength() > 0) {
			pollobjects.setScpollarray(new byte[pollobjects.getLength()]);
			byteBuffer.get(pollobjects.getScpollarray());
		}

		return scpollobjectscount;
	}

	public byte[] setRTSAPriorityList(WaveDataExportPriority waveDataExportPriority) {

		List<Byte> waveTrType = new ArrayList<Byte>();
		createWaveformSet(waveDataExportPriority, waveTrType);
		return sendRTSAPriorityMessage(Bytes.toArray(waveTrType));

	}

	public byte[] sendRTSAPriorityMessage(byte[] waveTrType) {
		List<Byte> tempbufflist = new ArrayList<Byte>();

		// Assemble request in reverse order first to calculate lengths
		// Insert TextIdList

		tempbufflist.addAll(0, Bytes.asList(waveTrType));

		Ava avatype = new Ava();
		avatype.setAttribute_id((short) IntelliVue.AttributeIDs.NOM_ATTR_POLL_RTSA_PRIO_LIST);
		avatype.setLength((short) waveTrType.length);
		// avatype.length = (ushort)tempbufflist.Count;
		tempbufflist.addAll(0, Bytes.asList(BitConverter.getBytesU16((short) avatype.getLength())));
		tempbufflist.addAll(0, Bytes.asList(BitConverter.getBytesU16((short) avatype.getAttribute_id())));

		byte[] AttributeModEntry = { 0x00, 0x00 };
		tempbufflist.addAll(0, Bytes.asList(AttributeModEntry));

		byte[] ModListlength = BitConverter.getBytesU16((short) tempbufflist.size());
		byte[] ModListCount = { 0x00, 0x01 };
		tempbufflist.addAll(0, Bytes.asList(ModListlength));
		tempbufflist.addAll(0, Bytes.asList(ModListCount));

		byte[] ManagedObjectID = { 0x00, 0x21, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		tempbufflist.addAll(0, Bytes.asList(ManagedObjectID));

		ROIVapdu rovi = new ROIVapdu();
		rovi.setLength((short) tempbufflist.size());
		rovi.setCommandType((short) IntelliVue.Commands.CMD_CONFIRMED_SET);
		rovi.setInovkeId((short) 0x0000);
		tempbufflist.addAll(0, Bytes.asList(BitConverter.getBytesU16((short) rovi.getLength())));
		tempbufflist.addAll(0, Bytes.asList(BitConverter.getBytesU16((short) rovi.getCommandType())));
		tempbufflist.addAll(0, Bytes.asList(BitConverter.getBytesU16((short) rovi.getInovkeId())));

		ROapdus roap = new ROapdus();
		roap.setLength((short) tempbufflist.size());
		roap.setRoType((short) IntelliVue.RemoteOperationHeader.ROIV_APDU);
		tempbufflist.addAll(0, Bytes.asList(BitConverter.getBytesU16((short) roap.getLength())));
		tempbufflist.addAll(0, Bytes.asList(BitConverter.getBytesU16((short) roap.getRoType())));

		byte[] Spdu = { (byte) 0xE1, 0x00, 0x00, 0x02 };
		tempbufflist.addAll(0, Bytes.asList(Spdu));

		byte[] finaltxbuff = Bytes.toArray(tempbufflist);

		return finaltxbuff;

	}

	public void createWaveformSet(WaveDataExportPriority waveDataExportPriority, List<Byte> waveTrType) {
		// Upto 3 ECG and/or 8 non-ECG waveforms can be polled by selecting the
		// appropriate labels
		// in the Wave object priority list

		switch (waveDataExportPriority) {
		case None:
			break;
		case One:
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x03))); // count
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x0C))); // length
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_II)));
			waveTrType.addAll(Bytes
					.asList((BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_I))));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_III)));
			break;
		case Two:
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x06))); // count
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x18))); // length
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_II)));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_BLD_ART_ABP)));
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_BLD_ART)));
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PULS_OXIM_PLETH)));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_BLD_VEN_CENT)));
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_RESP)));
			break;
		case Three:
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x03))); // count
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x0C))); // length
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_AVR)));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_AVL)));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_AVF)));
			break;
		case Four:
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x03))); // count
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x0C))); // length
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_V1)));
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_V2)));
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_V3)));
			break;
		case Five:
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x03))); // count
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x0C))); // length
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_V4)));
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_V5)));
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_V6)));
			break;
		case Six:
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x04))); // count
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x10))); // length
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_EEG_NAMES_EEG_CHAN1_LBL)));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_EEG_NAMES_EEG_CHAN2_LBL)));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_EEG_NAMES_EEG_CHAN3_LBL)));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_EEG_NAMES_EEG_CHAN4_LBL)));
			break;
		case Seven:
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x02))); // count
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x08))); // length
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_BLD_ART_ABP)));
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_BLD_ART)));
			break;

		case Eight:
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x06))); // count
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x18))); // length

			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL)));
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PULS_OXIM_PLETH)));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_BLD_ART_ABP)));
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_BLD_ART)));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_BLD_VEN_CENT)));
			waveTrType
					.addAll(Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_AWAY_CO2)));
			break;
		case Nine:
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x06))); // count
			waveTrType.addAll(Bytes.asList(BitConverter.getBytesU16((short) 0x18))); // length
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_ECG_ELEC_POTL_II)));
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_BLD_ART)));
			waveTrType.addAll(
					Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_INTRA_CRAN)));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_INTRA_CRAN_2)));
			waveTrType.addAll(Bytes
					.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_PRESS_BLD_VEN_CENT)));
			waveTrType
					.addAll(Bytes.asList(BitConverter.getBytesU32((int) DataConstants.WavesIDLabels.NLS_NOM_TEMP_BLD)));
			break;
		default:
			break;

		}
	}

}
