package com.jvscapture.datasource;

import java.io.IOException;

import com.jvscapture.datasource.philips.domain.NumericData;
import com.jvscapture.datasource.philips.domain.WaveData;

public abstract class DataSourceWriter extends java.io.Writer {

	@Override
	public abstract void write(String str) throws IOException;

	@Override
	public abstract void flush() throws IOException;

	@Override
	public abstract void close() throws IOException;

	public abstract void write(WaveData waveData) throws IOException;

	public abstract void write(NumericData numericData) throws IOException;

}
