package com.jvscapture.datasource.philips;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;

import com.jvscapture.DataSourceRegistry;
import com.jvscapture.datasource.DataExportPriority;
import com.jvscapture.datasource.DataSourceWriter;
import com.jvscapture.datasource.VSDataSource;
import com.jvscapture.datasource.ExceptionHandlers.ConnectionException;
import com.jvscapture.datasource.ExceptionHandlers.NotConnectedException;
import com.jvscapture.datasource.enums.InputMode;
import com.jvscapture.datasource.enums.OutputMode;

public class PhilipsDataSource implements VSDataSource {

	private final List<InputMode> inModes = Arrays.asList(InputMode.Socket);
	private final List<OutputMode> outModes = Arrays.asList(OutputMode.Aeron);
	private boolean isConnected = false;
	private MPSerialPort mpSerialPort = MPSerialPort.getInstance();

	static final String ID = "PhilipsMP70";

	// Object initialization block
	{
		DataSourceRegistry.add(this);
	}

	@Override
	public List<InputMode> getInputModes() {
		return inModes;
	}

	@Override
	public List<OutputMode> getOutputModes() {
		return outModes;
	}

	@Override
	public VSDataSource connect(InputMode inputMode, Map<String, String> properties) throws ConnectionException {
		assert (inputMode != null);
		assert (properties != null);

		if (InputMode.LAN.equals(inputMode)) {
			throw new UnsupportedOperationException("Input mode LAN is not supported");
		}

		connectViaSerialPort(properties.get(PropertyName.SERIALPORT));
		this.isConnected = true;
		return this;
	}

	private void connectViaSerialPort(String portName) throws ConnectionException {
		try {
			// Step 1: get the serial port to connect to
			// Step 2: Connect to the port using certain default parameters
			try {
				mpSerialPort.connect(portName);
			} catch (UnsupportedCommOperationException | NoSuchPortException | PortInUseException
					| TooManyListenersException e) {
				e.printStackTrace();
			}

		} catch (final Exception e) {
			throw new ConnectionException(e.getMessage());
		}

	}

	@Override
	public boolean isConnected() {
		return isConnected;
	}

	@Override
	public VSDataSource disconnect() {
		isConnected = false;
		return this;
	}

	@Override
	public void fetch(DataSourceWriter outputDataWriter, DataExportPriority exportPriority) throws NotConnectedException {
		assert (exportPriority instanceof InputData);
		InputData inputData = (InputData) exportPriority;
		inputData.setDeviceId(getIdentifier());
		mpSerialPort.fetch(outputDataWriter, inputData);

	}

	@Override
	public String getIdentifier() {
		return PhilipsDataSource.ID;
	}

}
