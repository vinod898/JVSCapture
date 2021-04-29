package com.jvscapture.datasource.philips.config;

import java.io.Writer;

public interface WaveSource {
	public void connect();
	public void get(Writer writer, WaveDataProperties type);
	public void stream(Writer writer, WaveDataProperties type, long pollInterval);
	public void disconnect();
	
}
