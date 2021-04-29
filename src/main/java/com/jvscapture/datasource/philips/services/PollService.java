package com.jvscapture.datasource.philips.services;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.jvscapture.datasource.DataSourceWriter;
import com.jvscapture.datasource.philips.InputData;
import com.jvscapture.datasource.philips.constants.IntelliVue.DataConstants;
import com.jvscapture.datasource.philips.domain.Ava;
import com.jvscapture.datasource.philips.domain.NumericData;
import com.jvscapture.datasource.philips.domain.NumericValResult;
import com.jvscapture.datasource.philips.domain.ObjectType;
import com.jvscapture.datasource.philips.domain.ObservationPoll;
import com.jvscapture.datasource.philips.domain.PollInfoList;
import com.jvscapture.datasource.philips.domain.PollMdibDataReply;
import com.jvscapture.datasource.philips.domain.PollMdibDataReplyExt;
import com.jvscapture.datasource.philips.domain.SaCalibData16;
import com.jvscapture.datasource.philips.domain.SaSpec;
import com.jvscapture.datasource.philips.domain.SingleContextPoll;
import com.jvscapture.datasource.philips.domain.WaveData;
import com.jvscapture.datasource.philips.domain.WaveValResult;
import com.jvscapture.datasource.philips.utils.Utils;
import com.jvscapture.util.BitConverter;

public class PollService {

	private static PollService single_instance = null;

	private PollService() {
	}

	public static PollService getInstance() {
		if (single_instance == null)
			single_instance = new PollService();

		return single_instance;
	}

	public long m_baseRelativeTime;
	public Date m_baseDateTime = new Date();
	private static int m_actiontype;
	private InputData inputData;
	private DecodeAvaObjectService avaObjectService = DecodeAvaObjectService.getInstance();
	private UtilServices utilServices = UtilServices.getInstance();

	public int mObPollHandle = 0;
	long mStrTimestamp = 0;
	public boolean mCalibratewavevalues = false;
	List<SaCalibData16> mSaCalibDataSpecList = new LinkedList<SaCalibData16>();
	List<SaSpec> m_SaSpecList = new LinkedList<SaSpec>();
	List<NumericValResult> numericValueResultList = new LinkedList<NumericValResult>();
	List<WaveValResult> waveValueResultList = new LinkedList<WaveValResult>();

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
	private DataSourceWriter outputDataWriter;

	private int m_idlabelhandle = 0;

	public void init(InputData inputData, DataSourceWriter outputDataWriter) {

		this.inputData = inputData;
		this.outputDataWriter = outputDataWriter;

	}

	public void checkLinkedPollPacketActionType(byte[] packetbuffer) {

		byte[] header = new byte[22];
		ByteBuffer byteBuffer = ByteBuffer.wrap(packetbuffer);
		byteBuffer.get(header);
		short action_type = byteBuffer.getShort();
		m_actiontype = action_type;
		switch (action_type) {
		case DataConstants.NOM_ACT_POLL_MDIB_DATA:
			pollPacketDecoder(packetbuffer, 46);
			break;
		case DataConstants.NOM_ACT_POLL_MDIB_DATA_EXT:
			pollPacketDecoder(packetbuffer, 48);
			break;
		default:
			break;
		}

	}

	public void checkPollPacketActionType(byte[] packetbuffer) {
		byte[] header = new byte[20];
		ByteBuffer byteBuffer = ByteBuffer.wrap(packetbuffer);
		byteBuffer.get(header);
		short action_type = byteBuffer.getShort();
		m_actiontype = action_type;
		switch (action_type) {
		case DataConstants.NOM_ACT_POLL_MDIB_DATA:
			pollPacketDecoder(packetbuffer, 44);
			break;
		case DataConstants.NOM_ACT_POLL_MDIB_DATA_EXT:
			pollPacketDecoder(packetbuffer, 46);
			break;
		default:
			break;
		}

	}

	private void pollPacketDecoder(byte[] packetbuffer, int headersize) {

		int packetsize = packetbuffer.length;

		byte[] header = new byte[headersize];
		ByteBuffer byteBuffer = ByteBuffer.wrap(packetbuffer);
		byteBuffer.get(header);

		byte[] packetdata = new byte[packetsize - header.length];
		System.arraycopy(packetbuffer, header.length, packetdata, 0, packetdata.length);

		this.mStrTimestamp = getPacketTimestamp(header);

		// int currentRelativeTime = Integer.parseInt(m_strTimestamp);
		// Date dtDateTime =
		// Utils.getAbsoluteTimeFromRelativeTimestamp(currentRelativeTime,
		// m_baseRelativeTime,
		// m_baseDateTime);

		// String strDateTime = simpleDateFormat.format(dtDateTime);

		// ParsePacketType

		PollInfoList pollobjects = new PollInfoList();

		int scpollobjectscount = utilServices.decodePollObjects(pollobjects, packetdata);

		if (scpollobjectscount > 0) {
			ByteBuffer byteBuffer2 = ByteBuffer.wrap(pollobjects.getScpollarray());
			for (int i = 0; i < scpollobjectscount; i++) {
				SingleContextPoll scpoll = new SingleContextPoll();
				int obpollobjectscount = utilServices.decodeSingleContextPollObjects(scpoll, byteBuffer2);

				if (obpollobjectscount > 0) {
					ByteBuffer byteBuffer3 = ByteBuffer.wrap(scpoll.getObPollObjectsArray());

					for (int j = 0; j < obpollobjectscount; j++) {
						ObservationPoll obpollobject = new ObservationPoll();
						int avaobjectscount = utilServices.decodeObservationPollObjects(obpollobject, byteBuffer3,
								(obj_handle) -> {
									this.mObPollHandle = obj_handle;
								});

						if (avaobjectscount > 0) {
							ByteBuffer byteBuffer4 = ByteBuffer.wrap(obpollobject.getAvaObjectsArray());

							for (int k = 0; k < avaobjectscount; k++) {
								Ava avaobject = new Ava();
								decodeAvaObjects(avaobject, byteBuffer4);
							}
						}
					}
				}
			}

			try {
				exportNumericData();
				exportWaveData();
			} catch (IOException e) {
				System.err.println("Error while Exporting the Data...");
			}

			// if (dataExportSet == 2)
			// ExportNumValListToJSON("Numeric");
			// ExportDataToCSV();
			// ExportWaveToCSV();
		}

	}

	private void exportNumericData() throws IOException {
		if (numericValueResultList.size() != 0) {

			List<NumericValResult> removeList = new LinkedList<NumericValResult>();
			long firstelementreltimestamp = numericValueResultList.get(0).getRelativetimestamp();
			int listcount = numericValueResultList.size();

			int m_elementcount = 0;
			NumericData numData = new NumericData();

			for (int i = m_elementcount; i < listcount; i++) {
				NumericValResult numVal = numericValueResultList.get(i);
				long elementreltime = numVal.getRelativetimestamp();
				if (elementreltime == firstelementreltimestamp) {
					numData.set(numVal.getPhysioID(), numVal.getValue());
					removeList.add(numVal);
					m_elementcount++;
				} else {

					numData.setTime(numVal.getTimestamp());
					numData.setRelativetime(numVal.getRelativetimestamp());
					numData.setSystemlocaltime(numVal.getSystemLocalTime());
					this.outputDataWriter.write(numData);
					numericValueResultList.removeAll(removeList);
					m_elementcount = 0;
					listcount = numericValueResultList.size();
				}
			}

		}
	}

	private void exportWaveData() throws IOException {

		int wavevallistcount = waveValueResultList.size();
		if (wavevallistcount != 0) {
			for (WaveValResult wavValResult : waveValueResultList) {

				int wavvalarraylength = wavValResult.getValue().length;

				String physioID = wavValResult.getPhysioID();

				for (int index = 0; index < wavvalarraylength; index++) {
					WaveData waveData = new WaveData();
					// Data sample size is 16 bits, but the significant bits
					// represent actual sample value

					// Read every 2 bytes
					byte msb = wavValResult.getValue()[index];
					byte lsb = wavValResult.getValue()[index + 1];

					int msbval = msb;
					// mask depends on no. of significant bits
					// int mask = 0x3FFF; //mask for 14 bits
					int mask = Utils.createMask(wavValResult.getSaSpecData().getSignificantBits());

					// int shift = (m_sample_size-8);
					int msbshift = (msb << 8);

					if (wavValResult.getSaSpecData().getSaFlags() < 0x4000) {
						msbval = (msbshift & mask);
						msbval = (msbval >> 8);
					} else
						msbval = msb;

					msb = (byte) msbval;

					byte[] data = { msb, lsb };

					double Waveval = BitConverter.toInt16(data, 0);

					if (wavValResult.getSaSpecData().getSaFlags() != 0x2000 && mCalibratewavevalues == true) {
						Waveval = Utils.calibrateSaValue(Waveval, wavValResult.getSaCalibData());
					}

					index = index + 1;
					waveData.setRelativetime(wavValResult.getRelativetimestamp());
					waveData.setSystemlocaltime(wavValResult.getSystemLocalTime());
					waveData.setTime(wavValResult.getTimestamp());

					Calendar calendar = Calendar.getInstance();
					calendar.setTime(wavValResult.getTimestamp());
					calendar.add(Calendar.MILLISECOND, (4 * (index / 2)));
					waveData.setTimeStamp(calendar.getTime().getTime());
					waveData.setValue(Waveval);
					waveData.setPhysioID(physioID);
					this.outputDataWriter.write(waveData);
				}
				waveValueResultList.clear();

			}

		}

	}

	public void decodeAvaObjects(Ava avaobject, ByteBuffer byteBuffer) {
		avaobject.setAttribute_id((int) byteBuffer.getShort());
		avaobject.setLength((int) byteBuffer.getShort());
		// avaobject.attribute_val =
		// correctendianshortus(binreader4.ReadUInt16());
		if (avaobject.getLength() > 0) {
			byte[] avaattribobjects = new byte[avaobject.getLength()];
			byteBuffer.get(avaattribobjects);

			switch (avaobject.getAttribute_id()) {
			case DataConstants.NOM_ATTR_ID_HANDLE:
				// ReadIDHandle(avaattribobjects);
				break;
			case DataConstants.NOM_ATTR_ID_LABEL:
				m_idlabelhandle = avaObjectService.readIDLabel(avaattribobjects);
				break;
			case DataConstants.NOM_ATTR_NU_VAL_OBS:
				NumericValResult numericValResult = avaObjectService.readNumericObservationValue(avaattribobjects,
						mStrTimestamp, this.inputData.getDeviceId());
				numericValueResultList.add(numericValResult);

				break;
			case DataConstants.NOM_ATTR_NU_CMPD_VAL_OBS:
				List<NumericValResult> list = avaObjectService.readCompoundNumericObsValue(avaattribobjects,
						mStrTimestamp, this.inputData.getDeviceId());
				numericValueResultList.addAll(list);
				break;
			case DataConstants.NOM_ATTR_METRIC_SPECN:
				break;
			case DataConstants.NOM_ATTR_ID_LABEL_STRING:
				avaObjectService.readIDLabelString(avaattribobjects);
				break;
			case DataConstants.NOM_ATTR_SA_VAL_OBS:
				WaveValResult waveValresult = avaObjectService.readWaveSaObservationValueObject(
						ByteBuffer.wrap(avaattribobjects), m_baseRelativeTime, m_baseDateTime,
						(waveVal, physioIdHandle) -> {

							waveVal.setRelativetimestamp(mStrTimestamp);
							waveVal.setDeviceID(inputData.getDeviceId());
							waveVal.setObPollHandle(mObPollHandle);
							SaCalibData16 saCalibData = mSaCalibDataSpecList.stream()
									.filter(x -> x.getPhysioId() == physioIdHandle).findFirst().orElse(null);
							waveVal.setSaCalibData(saCalibData);
							SaSpec saSpecData = m_SaSpecList.stream()
									.filter(x -> x.getObpollHandle() == waveVal.getObPollHandle()).findFirst()
									.orElse(null);

							waveVal.setSaSpecData(saSpecData);

						});
				waveValueResultList.add(waveValresult);
				break;
			case DataConstants.NOM_ATTR_SA_CMPD_VAL_OBS:
				List<WaveValResult> waveValresults = avaObjectService.readCompoundWaveSaObservationValue(
						ByteBuffer.wrap(avaattribobjects), m_baseRelativeTime, m_baseDateTime,
						(waveVal, physioIdHandle) -> {

							waveVal.setRelativetimestamp(mStrTimestamp);
							waveVal.setDeviceID(inputData.getDeviceId());
							waveVal.setObPollHandle(mObPollHandle);
							SaCalibData16 saCalibData = mSaCalibDataSpecList.stream()
									.filter(x -> x.getPhysioId() == physioIdHandle).findFirst().orElse(null);
							waveVal.setSaCalibData(saCalibData);
							SaSpec saSpecData = m_SaSpecList.stream()
									.filter(x -> x.getObpollHandle() == waveVal.getObPollHandle()).findFirst()
									.orElse(null);
							waveVal.setSaSpecData(saSpecData);
						});
				waveValueResultList.addAll(waveValresults);
				break;
			case DataConstants.NOM_ATTR_SA_SPECN:
				avaObjectService.readSaSpecifications(avaattribobjects, (saspecobj) -> {
					saspecobj.setObpollHandle(mObPollHandle);

					// Add to a list of Sample array specification definitions
					// if it's not already present
					int salistindex = m_SaSpecList.indexOf(saspecobj);
					if (salistindex == -1) {
						m_SaSpecList.add(saspecobj);
					} else {
						m_SaSpecList.remove(salistindex);
						m_SaSpecList.add(saspecobj);
					}
				});
				break;
			case DataConstants.NOM_ATTR_SCALE_SPECN_I16:
				// ReadSaScaleSpecifications(avaattribobjects);
				break;
			case DataConstants.NOM_ATTR_SA_CALIB_I16:
				avaObjectService.readSaCalibrationSpecifications(avaattribobjects, (saCalibData) -> {

					saCalibData.setObpollHandle(mObPollHandle);

					// Get 16 bit physiological id from 32 bit wave id label
					saCalibData.setPhysioId(Utils.get16bitLSBfromUInt(m_idlabelhandle));

					// Add to a list of Sample array calibration specification
					// definitions if it's not already present
					// m_idlabelhandle.fo
					SaCalibData16 saCalibData1 = mSaCalibDataSpecList.stream()
							.filter(x -> x.getPhysioId() == saCalibData.getPhysioId()).findFirst().orElse(null);

					if (saCalibData1 == null) {
						mSaCalibDataSpecList.add(saCalibData);
					} else {
						mSaCalibDataSpecList.remove(saCalibData1);
						mSaCalibDataSpecList.add(saCalibData);
					}

				});
				break;
			default:
				// unknown attribute -> do nothing
				break;
			}
		}

	}

	public long getPacketTimestamp(byte[] header) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(header);

		int pollmdibdatareplysize = 20;
		if (m_actiontype == DataConstants.NOM_ACT_POLL_MDIB_DATA)
			pollmdibdatareplysize = 20;
		else if (m_actiontype == DataConstants.NOM_ACT_POLL_MDIB_DATA_EXT)
			pollmdibdatareplysize = 22;

		int firstpartheaderlength = (header.length - pollmdibdatareplysize);
		byte[] firstpartheader = new byte[firstpartheaderlength];
		byte[] pollmdibdatareplyarray = new byte[pollmdibdatareplysize];
		byteBuffer.get(firstpartheader);
		byteBuffer.get(pollmdibdatareplyarray);

		long relativetime = 0;
		byte[] absolutetimearray = new byte[8];
		int pollresultcode = 0;

		if (m_actiontype == DataConstants.NOM_ACT_POLL_MDIB_DATA) {
			PollMdibDataReply pollmdibdatareply = new PollMdibDataReply();
			ByteBuffer byteBuffer2 = ByteBuffer.wrap(pollmdibdatareplyarray);
			pollmdibdatareply.setPollNumber((int) byteBuffer2.getShort());
			pollmdibdatareply.setRelTimestamp((long) byteBuffer2.getInt());

			relativetime = pollmdibdatareply.getRelTimestamp();

			byteBuffer2.get(absolutetimearray);
			ObjectType type = new ObjectType();
			type.setPartition((int) byteBuffer2.getShort());
			type.setCode((int) byteBuffer2.getShort());
			pollmdibdatareply.setType(type);

			pollresultcode = pollmdibdatareply.getType().getCode();
		} else if (m_actiontype == DataConstants.NOM_ACT_POLL_MDIB_DATA_EXT) {
			PollMdibDataReplyExt pollmdibdatareplyext = new PollMdibDataReplyExt();

			ByteBuffer byteBuffer2 = ByteBuffer.wrap(pollmdibdatareplyarray);

			pollmdibdatareplyext.setPollNumber((int) byteBuffer2.getShort());
			pollmdibdatareplyext.setSequenceNo((int) byteBuffer2.getShort());
			pollmdibdatareplyext.setRelTimestamp((long) byteBuffer2.getInt());

			relativetime = pollmdibdatareplyext.getRelTimestamp();

			byteBuffer2.get(absolutetimearray);

			ObjectType type = new ObjectType();
			type.setPartition((int) byteBuffer2.getShort());
			type.setCode((int) byteBuffer2.getShort());
			pollmdibdatareplyext.setType(type);

			pollresultcode = pollmdibdatareplyext.getType().getCode();
		}

		if (pollresultcode == (int) DataConstants.NOM_MOC_VMS_MDS) {
			// Get baseline timestamps if packet type is MDS attributes
			m_baseRelativeTime = relativetime;
			m_baseDateTime = getAbsoluteTimeFromBCDFormat(absolutetimearray);
		}

		// m_pollDateTime = GetAbsoluteTimeFromBCDFormat(absolutetimearray);

		// AbsoluteTime is not supported by several monitors
		/*
		 * AbsoluteTime absolutetime = new AbsoluteTime();
		 * 
		 * absolutetime.century = binreader2.ReadByte(); absolutetime.year =
		 * binreader2.ReadByte(); absolutetime.month = binreader2.ReadByte();
		 * absolutetime.day = binreader2.ReadByte(); absolutetime.hour =
		 * binreader2.ReadByte(); absolutetime.minute = binreader2.ReadByte();
		 * absolutetime.second = binreader2.ReadByte(); absolutetime.fraction =
		 * binreader2.ReadByte();
		 */

		return relativetime;
	}

	Date getAbsoluteTimeFromBCDFormat(byte[] bcdtimebuffer) {

		int century = Utils.binaryCodedDecimalToInteger(bcdtimebuffer[0]);
		int year = Utils.binaryCodedDecimalToInteger(bcdtimebuffer[1]);
		int month = Utils.binaryCodedDecimalToInteger(bcdtimebuffer[2]);
		int day = Utils.binaryCodedDecimalToInteger(bcdtimebuffer[3]);
		int hour = Utils.binaryCodedDecimalToInteger(bcdtimebuffer[4]);
		int minute = Utils.binaryCodedDecimalToInteger(bcdtimebuffer[5]);
		int second = Utils.binaryCodedDecimalToInteger(bcdtimebuffer[6]);
		// int fraction = Utils.binaryCodedDecimalToInteger(bcdtimebuffer[7]);

		int formattedyear = (century * 100) + year;

		Date dateTime = m_baseDateTime;

		if (formattedyear != 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateTime);
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month - 1);
			calendar.set(Calendar.DAY_OF_MONTH, day);

			calendar.set(Calendar.HOUR, hour);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, second);
			// calendar.set(Calendar.MILLISECOND, fraction);

			dateTime = calendar.getTime();
		}

		// m_baseDateTime = dateTime;
		return dateTime;
	}

	public void getBaselineRelativeTimestamp(byte[] timebuffer) {
		m_baseRelativeTime = BitConverter.toInt32_2(timebuffer, 0);

	}

}