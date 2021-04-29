package com.jvscapture.datasource;

import java.io.Writer;
import java.util.List;
import java.util.Map;

import com.jvscapture.datasource.ExceptionHandlers.ConnectionException;
import com.jvscapture.datasource.ExceptionHandlers.NotConnectedException;
import com.jvscapture.datasource.enums.InputMode;
import com.jvscapture.datasource.enums.OutputMode;

/**
 * A data source emitting vital signs should implements this interface.
 *
 *
 * The {@code DataSource} interface provides methods to connect, disconnect 
 * and to fetch data from the data source capable of giving vital sign information.
 *	
 * The data source's {@link #write()} writes data fetched to a {@link Writer} passed. 
 * 
 * 
 * 
 * @author Jagannadh
 * @author Vinod
 * 
 * @see InputMode
 * @see OutputMode
 * @see ConnectionException
 * @see NotConnectedException
 * 
 * @since 1.0
 */
public interface VSDataSource {
	
	/**
	 * Returns list of {@code InputMode}'s supported.
	 * @return list of {@code InputMode}'s supported.
	 */
	public List<InputMode> getInputModes();
	
	/**
	 * Returns list of {@code OutputMode}'s supported.
	 * @return list of {@code OutputMode}'s supported.
	 */
	public List<OutputMode> getOutputModes();
	
	/**
	 * Connects to the data source and returns instance of {@code DataSource}
	 * 
	 * @param inputMode
	 * @param properties that can be used to connect
	 * @throws ConnectionException When connection could not be established.
	 */
	public VSDataSource connect(InputMode inputMode, Map<String, String> properties) throws ConnectionException;
	
	/**
	 * Returns {@code true} if connected to the {@code DataSource}
	 * 
	 * @return {@code true} if connected to the {@code DataSource}
	 */
	public boolean isConnected();
	
	/**
	 * Disconnects from the {@code DataSource}
	 */
	public VSDataSource disconnect();
	
	/**
	 * Fetches the data and writes it to the writer passed. 
	 * Data will be written as a String to as Map of key value pairs {@link Map#toString()}.
	 * 
	 * 
	 * @param writer
	 * @param exportPriority
	 * @throws NotConnectedException
	 */
	public void fetch(DataSourceWriter outputDataWriter, DataExportPriority exportPriority) throws NotConnectedException;
	
	/**
	 * returns a string that uniquely identifies a data source
	 * 
	 * @return string that uniquely identifies a data source
	 */
	public String getIdentifier();
}
