package com.jvscapture;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.jvscapture.datasource.DataSourceWriter;
import com.jvscapture.datasource.VSDataSource;
import com.jvscapture.datasource.ExceptionHandlers.ConnectionException;
import com.jvscapture.datasource.ExceptionHandlers.NotConnectedException;
import com.jvscapture.datasource.enums.InputMode;
import com.jvscapture.datasource.enums.NumericDataExportPriority;
import com.jvscapture.datasource.enums.OutputMode;
import com.jvscapture.datasource.enums.Scale;
import com.jvscapture.datasource.enums.WaveDataExportPriority;
import com.jvscapture.datasource.philips.InputData;
import com.jvscapture.datasource.philips.PhilipsDataSource;
import com.jvscapture.datasource.writer.AeronWriter;
import com.jvscapture.datasource.writer.CSVWriter;
import com.jvscapture.datasource.writer.ConsoleWriter;
import com.jvscapture.util.Utils;

public class Main {

	// static InputData inputData;
	static int[] setarray = { 1000, 12000, 60000, 300000, 0, 100 }; // milliseconds

	public static void main(String args[]) throws InterruptedException, NotConnectedException, ConnectionException {

		Map<String, String> properties = new HashMap<String, String>();

		System.out.println("VitalSignsCaptureMP MIB (C)2017-21 John George K.");
		System.out.println("For command line usage: -help");
		System.out.println();

		properties = commandLineParser(args);
		if (properties.containsKey("help")) {
			help();
			return;
		}

		// get Connection Mode and if connection mode is "Serial Port" then set port
		// name in the properties.
		InputMode inputMode = Utils.getMode(properties);
		if (InputMode.Socket.equals(inputMode)) {
		}
		// Utils.setPort(properties);

		NumericDataExportPriority numericDataExportPriority = Utils.getTimeInterval(properties);

		WaveDataExportPriority waveSet = Utils.getWaveSet(properties);

		Scale scale = Utils.getScale(properties);

		OutputMode outputMode = Utils.getOutputMode(properties);

		DataSourceWriter outputDataSource = outputMode.equals(OutputMode.Aeron) ? new AeronWriter()
				: outputMode.equals(OutputMode.CSV) ? new CSVWriter() : new ConsoleWriter();

	

		Scanner scanner = new Scanner(System.in);
		VSDataSource ws = new PhilipsDataSource();
		ws.connect(inputMode, properties);
		ws.fetch(outputDataSource, new InputData(numericDataExportPriority, waveSet, scale));

		String input = "";
		while (!"q".equalsIgnoreCase(input)) {

			System.out.print("Enter something (q to quite): ");

			input = scanner.nextLine();
			System.out.println("input : " + input);
			Thread.sleep(1000);
		}

		System.out.println("bye bye!");
		scanner.close();
		ws.disconnect();
	}

	private static void help() {
		System.out.println("VSCaptureMP -mode[number] -port [portname] -interval [number]");
		System.out.println(" -waveset[number] -export[number] -scale[number]");
		System.out.println("-mode <Set connection mode MIB or LAN>");
		System.out.println("-port <Set IP address or serial port>");
		System.out.println("-interval <Set numeric transmission interval option>");
		System.out.println("-waveset <Set waveform request priority option>");
		System.out.println("-export <Set data export CSV or JSON option>");
		System.out.println("-scale <Set waveform data scale or calibrate option>");

		System.out.println();
	}

	private static Map<String, String> commandLineParser(String[] args) {

		Map<String, String> properties = new HashMap<String, String>();
		String currentName = "";
		for (String arg : args) {
			if (arg.startsWith("-"))
				currentName = arg.substring(1);
			else if (currentName == "")
				properties.put(arg, null);
			else
				properties.put(currentName, arg);
		}

		return properties;

	}

}
