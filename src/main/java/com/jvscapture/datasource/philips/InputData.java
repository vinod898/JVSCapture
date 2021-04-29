package com.jvscapture.datasource.philips;

import com.jvscapture.datasource.DataExportPriority;
import com.jvscapture.datasource.enums.NumericDataExportPriority;
import com.jvscapture.datasource.enums.Scale;
import com.jvscapture.datasource.enums.WaveDataExportPriority;

public class InputData implements DataExportPriority {

	private String deviceId;

	private NumericDataExportPriority numericDataExportPriority;

	private WaveDataExportPriority waveDataExportPriority;

	private Scale scale;

	public InputData(NumericDataExportPriority numericDataExportPriority, WaveDataExportPriority waveDataExportPriority,
			Scale scale) {
		super();
		this.numericDataExportPriority = numericDataExportPriority;
		this.waveDataExportPriority = waveDataExportPriority;
		this.scale = scale;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public NumericDataExportPriority getNumericDataExportPriority() {
		return numericDataExportPriority;
	}

	@Override
	public WaveDataExportPriority getWaveDataExportPriority() {
		return waveDataExportPriority;
	}

	@Override
	public Scale getScale() {
		return scale;
	}

}
