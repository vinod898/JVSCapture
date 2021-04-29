package com.jvscapture.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.comm.CommPortIdentifier;

import com.jvscapture.datasource.DataSource;
import com.jvscapture.datasource.enums.InputMode;
import com.jvscapture.datasource.enums.NumericDataExportPriority;
import com.jvscapture.datasource.enums.OutputMode;
import com.jvscapture.datasource.enums.Scale;
import com.jvscapture.datasource.enums.WaveDataExportPriority;
import com.jvscapture.datasource.philips.PropertyName;

public class Utils {

	static Scanner sc = new Scanner(System.in);

	public static int showAvailableOPtions(String header, String tailer, DataSource[] values, boolean required) {
		int option = -1;
		try {
			System.out.println();
			System.out.println(header);
			int startRange = 0;
			int endRange = 0;
			for (int i = 0; i < values.length; i++) {
				DataSource mode = values[i];
				System.out.println(mode.getValue() + ". " + mode.getDescription());
				if (i == 0)
					startRange = mode.getValue();
				if (i == (values.length - 1))
					endRange = mode.getValue();
			}
			System.out.println(tailer + "(" + startRange + "-" + endRange + "):");
			System.out.println();

			option = pickOption(required, startRange, endRange);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// sc.close();
		}
		return option;

	}

	private static int pickOption(boolean required, int startRange, int endRange) {
		while (true) {
			String strValue = sc.nextLine();

			if (strValue != "") {
				if ("q".equalsIgnoreCase(strValue)) {
					return -1;
				}
				try {
					int value = Integer.parseInt(strValue);
					if (value >= startRange && value <= endRange)
						return value;
					else {
						System.out.println("please enter valid number between given range  " + "(" + startRange + "-"
								+ endRange + ") : (or) enter q to quit.");
						continue;

					}
				} catch (NumberFormatException e) {
					System.err.println("please enter valid number between given range  " + "(" + startRange + "-"
							+ endRange + ") : (or) enter q to quit.");
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				if (!required)
					break;
			}

		}
		return -1;

	}

	public static InputMode getMode(Map<String, String> properties) {

		int opt = -1;
		InputMode inputMOde;
		if (properties.containsKey(PropertyName.ConnectionMode))
			opt = Integer.parseInt(properties.get(PropertyName.ConnectionMode));
		else
			opt = showAvailableOPtions("", "Choose connection mode ", InputMode.values(), true);
		inputMOde = (InputMode) InputMode.get(opt);
		return inputMOde;

	}

	public static void setPort(Map<String, String> properties) {

		List<String> list = new ArrayList<String>();

		if (!properties.containsKey(PropertyName.SERIALPORT)) {
			System.out.println("Select the Port to which Intellivue Monitor is to be connected, Available Ports:");
			Enumeration<?> ports = CommPortIdentifier.getPortIdentifiers();

			int index = 0;
			while (ports.hasMoreElements()) {
				CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();
				System.out.println(++index + " ." + curPort.getName());
				list.add(curPort.getName());
			}
			System.out.println("Choose Serial Port : " + "(1-" + list.size() + "):");

			int option = pickOption(true, 1, list.size());

			properties.put(PropertyName.SERIALPORT, list.get(option - 1));

		}

	}

	public static Scale getScale(Map<String, String> properties) {

		int opt = -1;
		Scale scale;
		if (properties.containsKey(PropertyName.Scale))
			opt = Integer.parseInt(properties.get(PropertyName.Scale));
		else
			opt = showAvailableOPtions("Waveform data export scale and calibrate options:",
					"Choose Waveform data export scale option ", Scale.values(), true);
		scale = (Scale) Scale.get(opt);
		return scale;

	}

	public static NumericDataExportPriority getTimeInterval(Map<String, String> properties) {

		int opt = -1;
		NumericDataExportPriority numericDataExportPriority;
		if (properties.containsKey(PropertyName.TimeInterval))
			opt = Integer.parseInt(properties.get(PropertyName.TimeInterval));
		else
			opt = showAvailableOPtions("Numeric Data Transmission sets:", "Choose Data Transmission interval ",
					NumericDataExportPriority.values(), true);
		numericDataExportPriority = (NumericDataExportPriority) NumericDataExportPriority.get(opt);
		return numericDataExportPriority;

	}
//
//	public static void setOutputMode(Map<String, String> properties) {
//		showAvailableOPtions("Data export options:", "Choose data export option ", OutPutMode.values(), true,
//				(option) -> {
//					if (option == -1)
//						return;
//					inputData.setOutputMode(OutPutMode.get(option));
//				});
//
//	}

	public static WaveDataExportPriority getWaveSet(Map<String, String> properties) {

		int opt = -1;
		WaveDataExportPriority numericDataExportPriority;
		if (properties.containsKey(PropertyName.WaveSet))
			opt = Integer.parseInt(properties.get(PropertyName.WaveSet));
		else
			opt = showAvailableOPtions("Waveform data export priority options:",
					"Selecting all waves can lead to data loss due to bandwidth issues." + "\n"
							+ "Choose Waveform data export priority option",
					WaveDataExportPriority.values(), true);
		numericDataExportPriority = (WaveDataExportPriority) WaveDataExportPriority.get(opt);
		return numericDataExportPriority;
	}

	public static OutputMode getOutputMode(Map<String, String> properties) {
		int opt = -1;
		OutputMode outputMode;

		if (properties.containsKey(PropertyName.OutputMOde))
			opt = Integer.parseInt(properties.get(PropertyName.OutputMOde));
		else
			opt = showAvailableOPtions("Waveform data export priority options:",
					"Selecting all waves can lead to data loss due to bandwidth issues." + "\n"
							+ "Choose Waveform data export priority option",
					OutputMode.values(), true);
		outputMode = (OutputMode) OutputMode.get(opt);
		return outputMode;
	}

}
