package com.jvscapture.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

import javax.comm.CommPortIdentifier;

import com.jvscapture.datasource.DataSource;
import com.jvscapture.datasource.enums.InputMode;
import com.jvscapture.datasource.enums.NumericDataExportPriority;
import com.jvscapture.datasource.enums.WaveDataExportPriority;
import com.jvscapture.datasource.philips.PropertyName;

public class InputDataUtils {

	static Scanner sc = new Scanner(System.in);

	public static void showAvailableOPtions(String header, String tailer, DataSource[] values, boolean required,
			Consumer<Integer> callback) {

		try {
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

			pickOption(required, callback, startRange, endRange);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// sc.close();
		}

	}

	private static void pickOption(boolean required, Consumer<Integer> callback, int startRange, int endRange) {
		while (true) {
			String strValue = sc.nextLine();

			if (strValue != "") {
				if ("q".equalsIgnoreCase(strValue)) {
					callback.accept(-1);
					break;
				}
				try {
					int value = Integer.parseInt(strValue);
					if (value >= startRange && value <= endRange) {
						System.out.println(value);
						callback.accept(value);
						break;
					} else {
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

	}

	public static void setMode(Map<String, String> properties) {

		showAvailableOPtions("", "Choose connection mode ", InputMode.values(), true, (option) -> {
			if (option == -1)
				return;
			properties.put(PropertyName.ConnectionMode, InputMode.get(option).getDescription());
		});

	}

	public static void setPort(Map<String, String> properties) {
		System.out.println("Select the Port to which Intellivue Monitor is to be connected, Available Ports:");
		Enumeration<?> ports = CommPortIdentifier.getPortIdentifiers();
		List<String> list = new ArrayList<String>();
		int index = 0;
		while (ports.hasMoreElements()) {
			CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();
			System.out.println(++index + " ." + curPort.getName());
			list.add(curPort.getName());
		}
		System.out.println("Choose Serial Port : " + "(1-" + list.size() + "):");

		pickOption(true, (option) -> {
			if (option == -1)
				return;
			properties.put(PropertyName.SERIALPORT, list.get(option));
		}, 1, list.size());

	}

	public static void setTimeInterval(Map<String, String> properties) {
		showAvailableOPtions("Numeric Data Transmission sets:", "Choose Data Transmission interval ",
				NumericDataExportPriority.values(), true, (option) -> {
					if (option == -1)
						return;
					properties.put(PropertyName.TimeInterval, NumericDataExportPriority.get(option).getDescription());
				});

	}

	/*
	 * public static void setOutputMode(Map<String, String> properties) {
	 * showAvailableOPtions("Data export options:", "Choose data export option ",
	 * OutPutMode.values(), true, (option) -> { if (option == -1) return;
	 * inputData.setOutputMode(OutPutMode.get(option)); });
	 * 
	 * }
	 */

	public static void setWaveSet(Map<String, String> properties) {
		showAvailableOPtions("Waveform data export priority options:",
				"Selecting all waves can lead to data loss due to bandwidth issues." + "\n"
						+ "Choose Waveform data export priority option",
				WaveDataExportPriority.values(), true, (option) -> {
					if (option == -1)
						return;
					properties.put(PropertyName.WaveSet, WaveDataExportPriority.get(option).getDescription());
				});

	}

}
