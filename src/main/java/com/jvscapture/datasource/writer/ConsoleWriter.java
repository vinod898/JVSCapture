package com.jvscapture.datasource.writer;

import java.io.IOException;

import com.jvscapture.datasource.DataSourceWriter;
import com.jvscapture.datasource.philips.domain.NumericData;
import com.jvscapture.datasource.philips.domain.WaveData;

public class ConsoleWriter extends DataSourceWriter {

	@Override
	public void write(String str) throws IOException {
		System.out.println(str);

	}

	@Override
	public void write(WaveData waveData) throws IOException {
		write(waveData.toString());

	}

	@Override
	public void write(NumericData numericData) throws IOException {
		write(numericData.toString());

	}

	@Override
	public void flush() throws IOException {

	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {

	}

}
