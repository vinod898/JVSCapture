package com.jvscapture.datasource.philips.services;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.jvscapture.datasource.philips.constants.IntelliVue.DataConstants;
import com.jvscapture.datasource.philips.domain.AlertSource;
import com.jvscapture.datasource.philips.domain.NuObsValue;
import com.jvscapture.datasource.philips.domain.NuObsValueCmp;
import com.jvscapture.datasource.philips.domain.NumericValResult;
import com.jvscapture.datasource.philips.domain.SaCalibData16;
import com.jvscapture.datasource.philips.domain.SaObsValue;
import com.jvscapture.datasource.philips.domain.SaObsValueCmp;
import com.jvscapture.datasource.philips.domain.SaSpec;
import com.jvscapture.datasource.philips.domain.WaveValResult;
import com.jvscapture.datasource.philips.utils.Utils;

public class DecodeAvaObjectService {

	private static DecodeAvaObjectService single_instance = null;

	private DecodeAvaObjectService() {
	}

	public static DecodeAvaObjectService getInstance() {
		if (single_instance == null)
			single_instance = new DecodeAvaObjectService();

		return single_instance;
	}

	String pattern = "dd-MM-yyyy HH:mm:ss:SSS";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	public void readSaSpecifications(byte[] avaattribobjects, Consumer<SaSpec> callback) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(avaattribobjects);

		SaSpec saspecobj = new SaSpec();
		saspecobj.setArraySize((int) byteBuffer.getShort());
		saspecobj.setSampleSize(byteBuffer.get());
		saspecobj.setSignificantBits(byteBuffer.get());
		saspecobj.setSaFlags((int) byteBuffer.getShort());

		callback.accept(saspecobj);
	}

	public void readSaCalibrationSpecifications(byte[] avaattribobjects, Consumer<SaCalibData16> callback) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(avaattribobjects);

		SaCalibData16 saCalibData = new SaCalibData16();

		saCalibData.setLowerAbsoluteValue(Utils.floattypeToValue(byteBuffer.getInt()));
		saCalibData.setUpperAbsoluteValue(Utils.floattypeToValue(byteBuffer.getInt()));
		saCalibData.setLowerScaledValue((int) byteBuffer.getShort());
		saCalibData.setUpperScaledValue((int) byteBuffer.getShort());
		saCalibData.setIncrement((int) byteBuffer.getShort());
		saCalibData.setCalType((int) byteBuffer.getShort());

		callback.accept(saCalibData);

	}

	public List<WaveValResult> readCompoundWaveSaObservationValue(ByteBuffer byteBuffer, long m_baseRelativeTime,
			Date m_baseDateTime, BiConsumer<WaveValResult, Integer> callback) {

		List<WaveValResult> resultList = new ArrayList<WaveValResult>();

		SaObsValueCmp waveSaObjectValueCmp = new SaObsValueCmp();
		waveSaObjectValueCmp.setCount((int) byteBuffer.getShort());
		waveSaObjectValueCmp.setLength((int) byteBuffer.getShort());

		int cmpwaveobjectscount = waveSaObjectValueCmp.getCount();
		int cmpwaveobjectslength = waveSaObjectValueCmp.getLength();

		byte[] cmpwavearrayobject = new byte[cmpwaveobjectslength];
		byteBuffer.get(cmpwavearrayobject);

		if (cmpwaveobjectscount > 0) {

			ByteBuffer byteBuffer2 = ByteBuffer.wrap(cmpwavearrayobject);

			for (int k = 0; k < cmpwaveobjectscount; k++) {
				WaveValResult waveValResult = readWaveSaObservationValueObject(byteBuffer2, m_baseRelativeTime,
						m_baseDateTime, callback);
				resultList.add(waveValResult);
			}
		}
		return resultList;

	}

	public WaveValResult readWaveSaObservationValueObject(ByteBuffer byteBuffer, long m_baseRelativeTime,
			Date m_baseDateTime, BiConsumer<WaveValResult, Integer> callback) {

		SaObsValue waveSaObjectValue = new SaObsValue();
		waveSaObjectValue.setPhysioId((int) byteBuffer.getShort());
		waveSaObjectValue.setState((int) byteBuffer.getShort());
		waveSaObjectValue.setLength((int) byteBuffer.getShort());

		int wavevalobjectslength = waveSaObjectValue.getLength();
		byte[] waveValObjects = new byte[wavevalobjectslength];
		byteBuffer.get(waveValObjects);
		String physioId = AlertSource.get(waveSaObjectValue.getPhysioId());

		WaveValResult waveVal = new WaveValResult();

		// DateTime dtDateTime = DateTime.Now;

		// WaveVal.Timestamp = DateTime.Now.ToString();

		waveVal.setSystemLocalTime(new Date());

		waveVal.setPhysioID(physioId);
		int physio_id_handle = waveSaObjectValue.getPhysioId();

		waveVal.setValue(new byte[wavevalobjectslength]);
		System.arraycopy(waveValObjects, 0, waveVal.getValue(), 0, wavevalobjectslength);

		callback.accept(waveVal, physio_id_handle);

		long currentRelativeTime = waveVal.getRelativetimestamp();
		Date dtDateTime = Utils.getAbsoluteTimeFromRelativeTimestamp(currentRelativeTime, m_baseRelativeTime,
				m_baseDateTime);
		waveVal.setTimestamp(dtDateTime);

		if (waveVal.getSaCalibData() == null) {
			SaCalibData16 saCalibData = new SaCalibData16();
			if (physio_id_handle == 0x107) {
				// use default values for ecg II
				saCalibData.setLowerAbsoluteValue(0);
				saCalibData.setUpperAbsoluteValue(1);
				saCalibData.setLowerScaledValue(0x1fe7);
				saCalibData.setUpperScaledValue(0x20af);

			} else if (physio_id_handle == 0x102) {
				// use default values for ecg V5

				saCalibData.setLowerAbsoluteValue(0);
				saCalibData.setUpperAbsoluteValue(1);
				saCalibData.setLowerScaledValue(0x1fd4);
				saCalibData.setUpperScaledValue(0x209c);

			} else if (physio_id_handle == 0x4A10) {
				// use default values for art ibp

				saCalibData.setLowerAbsoluteValue(0);
				saCalibData.setUpperAbsoluteValue(150);
				saCalibData.setLowerScaledValue(0x0320);
				saCalibData.setUpperScaledValue(0x0c80);

			} else if (physio_id_handle == 0x5000) {
				// use default values for resp

				saCalibData.setLowerAbsoluteValue(0);
				saCalibData.setUpperAbsoluteValue(1);
				saCalibData.setLowerScaledValue(0x04ce);
				saCalibData.setUpperScaledValue(0x0b33);

			} else
				waveVal.setSaCalibData(null);

			waveVal.setSaCalibData(saCalibData);
		}

		if (waveVal.getSaSpecData() == null) {

			SaSpec saSpecData = new SaSpec();

			if (wavevalobjectslength % 128 == 0) {
				// use default values for ecg
				saSpecData.setSignificantBits((byte) 0x0E);
				saSpecData.setSaFlags(0x3000);
				saSpecData.setSampleSize((byte) 0x10);
				saSpecData.setArraySize(0x80);
			} else if (wavevalobjectslength % 64 == 0) {
				// use default values for art ibp
				saSpecData.setSignificantBits((byte) 0x0E);
				saSpecData.setSaFlags(0x3000);
				saSpecData.setSampleSize((byte) 0x10);
				saSpecData.setArraySize(0x40);

			} else if (wavevalobjectslength % 32 == 0) {
				// use default values for resp
				saSpecData.setSignificantBits((byte) 0x0C);
				saSpecData.setSaFlags(0x8000);
				saSpecData.setSampleSize((byte) 0x10);
				saSpecData.setArraySize(0x20);

			} else if (wavevalobjectslength % 16 == 0) {
				// use default values for pleth
				saSpecData.setSignificantBits((byte) 0x0C);
				saSpecData.setSaFlags(0x8000);
				saSpecData.setSampleSize((byte) 0x10);
				saSpecData.setArraySize(0x10);

			}
			waveVal.setSaSpecData(saSpecData);
		}

		return waveVal;

	}

	public String readIDLabelString(byte[] avaattribobjects) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(avaattribobjects);

		int length = byteBuffer.getShort();
		byte[] stringval = new byte[length];

		byteBuffer.get(stringval);
		String label = new String(stringval, StandardCharsets.UTF_8);

		return label;

	}

	public List<NumericValResult> readCompoundNumericObsValue(byte[] avaattribobjects, long m_strTimestamp,
			String deviceId) {

		List<NumericValResult> resultList = new ArrayList<NumericValResult>();

		ByteBuffer byteBuffer = ByteBuffer.wrap(avaattribobjects);

		NuObsValueCmp numObjectValueCmp = new NuObsValueCmp();
		numObjectValueCmp.setCount(byteBuffer.getShort());
		numObjectValueCmp.setLength(byteBuffer.getShort());

		int cmpnumericobjectscount = (int) numObjectValueCmp.getCount();

		if (cmpnumericobjectscount > 0) {
			for (int j = 0; j < cmpnumericobjectscount; j++) {
				byte[] cmpnumericarrayobject = new byte[10];
				byteBuffer.get(cmpnumericarrayobject);
				NumericValResult numericValResult = readNumericObservationValue(cmpnumericarrayobject, m_strTimestamp,
						deviceId);
				resultList.add(numericValResult);
			}
		}
		return resultList;

	}

	public NumericValResult readNumericObservationValue(byte[] avaattribobjects, long m_strTimestamp, String deviceId) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(avaattribobjects);

		NuObsValue numObjectValue = new NuObsValue();
		numObjectValue.setPhysioId((int) byteBuffer.getShort());
		numObjectValue.setState((int) byteBuffer.getShort());
		numObjectValue.setUnitCode((int) byteBuffer.getShort());
		numObjectValue.setValue(byteBuffer.getInt());

		double value = Utils.floattypeToValue(numObjectValue.getValue());

		String physio_id = AlertSource.get(numObjectValue.getPhysioId());

		String valuestr;
		if (value != DataConstants.FLOATTYPE_NAN) {
			valuestr = String.valueOf(value);
		} else
			valuestr = "-";

		NumericValResult numVal = new NumericValResult();
		numVal.setRelativetimestamp(m_strTimestamp);
		numVal.setTimestamp(new Date());

		// NumVal.Timestamp = DateTime.Now.ToString();

		Date dtSystemDateTime = new Date();
		numVal.setSystemLocalTime(dtSystemDateTime);

		numVal.setPhysioID(physio_id);
		numVal.setValue(valuestr);
		numVal.setDeviceID(deviceId);
		return numVal;

	}

	public int readIDLabel(byte[] avaattribobjects) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(avaattribobjects);
		int IDlabel = (int) byteBuffer.getInt();
		return IDlabel;
	}
}
