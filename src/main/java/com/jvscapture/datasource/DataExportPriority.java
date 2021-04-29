package com.jvscapture.datasource;

import com.jvscapture.datasource.enums.NumericDataExportPriority;
import com.jvscapture.datasource.enums.Scale;
import com.jvscapture.datasource.enums.WaveDataExportPriority;

public interface DataExportPriority {
	public NumericDataExportPriority getNumericDataExportPriority();

	public WaveDataExportPriority getWaveDataExportPriority();

	public Scale getScale();

}
