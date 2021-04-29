package com.shris;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.jvscapture.datasource.DataSourceWriter;
import com.jvscapture.datasource.enums.NumericDataExportPriority;
import com.jvscapture.datasource.enums.Scale;
import com.jvscapture.datasource.enums.WaveDataExportPriority;
import com.jvscapture.datasource.philips.InputData;
import com.jvscapture.datasource.philips.MPSerialPort;
import com.jvscapture.datasource.philips.domain.AlertSource;
import com.jvscapture.datasource.philips.domain.NumericData;
import com.jvscapture.datasource.philips.domain.NumericValResult;
import com.jvscapture.datasource.philips.domain.WaveData;
import com.jvscapture.datasource.philips.services.DecodeAvaObjectService;
import com.jvscapture.datasource.philips.services.PocketProcessService;
import com.jvscapture.datasource.philips.utils.Utils;
import com.jvscapture.datasource.writer.AeronWriter;
import com.jvscapture.datasource.writer.CSVWriter;
import com.jvscapture.datasource.writer.ConsoleWriter;

public class MainTest {

	DecodeAvaObjectService decodeAvaObjectService = DecodeAvaObjectService.getInstance();


	@Test
	public void readIdLabelTest() {
		byte[] arr = new byte[] { 0, 2, 88, 4 };
		int result = decodeAvaObjectService.readIDLabel(arr);
		assertEquals(result, 153604);
	}

	// ReadNumericObservationValue

	@Test
	public void readNumericObservationValueTest() {
		byte[] arr = new byte[] { 65, (byte) 130, 0, 0, 10, (byte) 160, 0, 0, 0, 60 };
		NumericValResult result = decodeAvaObjectService.readNumericObservationValue(arr, 8794795, "");
		assertNotNull(result);
	}

	@Test
	public void readCompoundNumericObsValueTest() {
		byte[] arr = new byte[] { 0, 3, 0, 30, 74, 5, 32, 0, 15, 32, 0, 127, (byte) 255, (byte) 255, 74, 6, 32, 0, 15,
				32, 0, 127, (byte) 255, (byte) 255, 74, 7, 32, 0, 15, 32, 0, 127, (byte) 255, (byte) 255 };
		List<NumericValResult> resultList = decodeAvaObjectService.readCompoundNumericObsValue(arr, 8794795, "");
		assertTrue(resultList.size() > 0);
	}

	@Test
	public void readIDLabelStringTest() {
		byte[] arr = new byte[] { 0, 14, 0, 80, 0, 117, 0, 108, 0, 115, 0, 101, 0, 32, 0, 0 };
		String result = decodeAvaObjectService.readIDLabelString(arr);
		assertNotNull(result);
	}
//
//	@Test
//	public void readBytesFromFile() {
//		Path path = Paths.get("files/input_files/MPRawoutput1.raw");
//		byte[] buffer = new byte[4096];
//		byte[] data;
//
//		MPSerialPort mpSerialPort = MPSerialPort.getInstance();
//		PocketProcessService pocketProcessService = mpSerialPort.getPocketProcessService();
//		pocketProcessService.init(new ByteArrayOutputStream(),
//				new InputData(NumericDataExportPriority.OneSecondRealTime, WaveDataExportPriority.Eight, Scale.Scaled),
//				new ConsoleWriter());
//		try {
//			data = Files.readAllBytes(path);
//			// reverse array
//			Collections.reverse(Arrays.asList(data));
//			ByteBuffer bb = ByteBuffer.wrap(data);
//
//			do {
//				try {
//					/// clear buffer
//					for (int i = 0; i < 4096; i++)
//						buffer[i] = 0;
//					bb.get(buffer);
//					mpSerialPort.initIOStream(new ByteArrayInputStream(buffer), new ByteArrayOutputStream());
//					mpSerialPort.readBuffer();
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} catch (BufferUnderflowException e) {
//					bb = ByteBuffer.wrap(data);
//				}
//			} while (true);
//
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void readBytesFromFile1() {
//		Path path = Paths.get("files/input_files/MPRawoutput1.raw");
//		byte[] buffer = new byte[4096];
//		byte[] data;
//
//		MPSerialPort mpSerialPort = MPSerialPort.getInstance();
//		PocketProcessService pocketProcessService = mpSerialPort.getPocketProcessService();
//		pocketProcessService.init(new ByteArrayOutputStream(),
//				new InputData(NumericDataExportPriority.OneSecondRealTime, WaveDataExportPriority.Eight, Scale.Scaled),
//				new ConsoleWriter());
//
//		try {
//			data = Files.readAllBytes(path);
//
//			int to, from = data.length;
//
//			do {
//
//				to = from;
//				from = from % 4096 == 0 ? from - 4096 : from - (from % 4096);
////				System.out.println(from + "," + to + "," + (to - from));
//				System.arraycopy(data, from, buffer, 0, to - from);
//				mpSerialPort.initIOStream(new ByteArrayInputStream(buffer), new ByteArrayOutputStream());
//				mpSerialPort.readBuffer();
//				Thread.sleep(1000);
//
//			}
//
//			while (from > 0);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	// floattypeToValue

	@Test
	public void floattypeToValueTest() {
		int input = 60;
		double result = Utils.floattypeToValue(input);
		assertEquals(result, 60.0, 0.01);
	}

	@Test
	public void enumTest() {
		String pay = AlertSource.get(2);
		System.out.println(pay);
	}

	@Test
	public void consoleWriterTest() {

		DataSourceWriter writer = new ConsoleWriter();
		try {
			writer.write("My Message in ");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void NumericDataCSVWriterTest() {

		NumericData numericData = new NumericData();
		numericData.setNomEcgAmplStAvf(-0.99);
		numericData.setNomEcgAmplStAvl(-0.99);
		numericData.setNomEcgAmplStAvr(-0.99);
		numericData.setNomEcgAmplStI(-0.99);
		numericData.setNomEcgAmplStIi(-0.99);
		numericData.setNomEcgAmplStIii(-0.99);
		numericData.setNomEcgAmplStMcl(-0.99);
		numericData.setNomEcgAmplStV(-0.99);
		numericData.setNomEcgCardBeatRate(-0.99);
		numericData.setNomEcgVPCCnt(-0.99);
		numericData.setNomPlethPulsRate(-0.99);
		numericData.setNomPressBldNoninvDia(-0.99);
		numericData.setNomPressBldNoninvMean(-0.99);
		numericData.setNomPressBldNoninvSys(-0.99);
		numericData.setNomPulsOximPerfRel(-0.99);
		numericData.setNomPulsOximSatO2(-0.99);
		numericData.setRelativetime(new Date().getTime());
		numericData.setSystemlocaltime(new Date());
		numericData.setTime(new Date());

		DataSourceWriter writer = new CSVWriter();
		try {

			writer.write(numericData);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void WaveDataCSVWriterTest() {

		WaveData waveData = new WaveData();

		waveData.setValue(59);
		waveData.setRelativetime(new Date().getTime());
		waveData.setSystemlocaltime(new Date());
		waveData.setTime(new Date());
		waveData.setPhysioID("test");

		DataSourceWriter writer = new CSVWriter();
		try {
			writer.write(waveData);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
