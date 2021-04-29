package com.jvscapture.datasource.writer;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jvscapture.datasource.DataSourceWriter;
import com.jvscapture.datasource.philips.domain.NumericData;
import com.jvscapture.datasource.philips.domain.WaveData;
import com.jvscapture.datasource.philips.services.SimplePublisher;

public class AeronWriter extends DataSourceWriter {

	private SimplePublisher aeronPublisher = SimplePublisher.getInstance();
	ObjectMapper mapper = new ObjectMapper();
	private String publisherType;

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws IOException {

	}

	public void write(String jsonString) throws IOException {
		aeronPublisher.send(publisherType, jsonString);
	}

	@Override
	public void write(WaveData waveData) throws IOException {
		String jsonString = mapper.writeValueAsString(waveData);
		setPublisherType(waveData.getPhysioID());
		write(jsonString);

	}

	@Override
	public void write(NumericData numericData) throws IOException {
		String jsonString = mapper.writeValueAsString(numericData);
		setPublisherType("Numeric");
		write(jsonString);

	}

	public String getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(String publisherType) {
		this.publisherType = publisherType;
	}

}
