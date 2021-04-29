package com.jvscapture.datasource.philips.services;

import org.agrona.BitUtil;
import org.agrona.BufferUtil;
import org.agrona.concurrent.UnsafeBuffer;

import io.aeron.Aeron;
import io.aeron.Publication;

/**
 * A very simple Aeron publisher application which publishes a fixed size
 * message on a fixed channel and stream.
 */
public class SimplePublisher {

	UnsafeBuffer buffer = new UnsafeBuffer(BufferUtil.allocateDirectAligned(65536, BitUtil.CACHE_LINE_LENGTH));

	private static Publication nom_ecg_elec_plotl_avr_Datapublication;
	private static Publication nom_ecg_elec_plotl_V_Datapublication;
	private static Publication nomPlethDatapublication;
	private static Publication numericDatapublication;
	private static Publication nom_ecg_elec_plotl_II_DataPublication;

	private static SimplePublisher single_instance = null;

	private SimplePublisher() {
	}

	public static SimplePublisher getInstance() {
		if (single_instance == null) {
			try {
				init();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		single_instance = new SimplePublisher();

		return single_instance;
	}

	/**
	 * Main method for launching the process.
	 *
	 * @param args passed to the process.
	 * @throws InterruptedException if the thread sleep delay is interrupted.
	 */
	public static void init() throws InterruptedException {

		// Allocate enough buffer size to hold maximum message length
		// The UnsafeBuffer class is part of the Agrona library and is used for
		// efficient buffer management

		// The channel (an endpoint identifier) to send the message to
		final String channel = "aeron:ipc?endpoint=localhost:40123";

		// A unique identifier for a stream within a channel. Stream ID 0 is reserved
		// for internal use and should not be used by applications.
		final int streamId = 42;

		System.out.println("Publishing to " + channel + " on stream id " + streamId);

		// Create a context, needed for client connection to media driver
		// A separate media driver process needs to be running prior to starting this
		// application
		final Aeron.Context ctx = new Aeron.Context();

		// Create an Aeron instance with client-provided context configuration and
		// connect to the
		// media driver, and create a Publication. The Aeron and Publication classes
		// implement
		// AutoCloseable, and will automatically clean up resources when this try block
		// is finished.

		try {
			Aeron aeron = Aeron.connect(ctx);
			nom_ecg_elec_plotl_II_DataPublication = aeron.addPublication(channel, 43);
//
//			numericDatapublication = aeron.addPublication(channel, 42);
//			nom_ecg_elec_plotl_V_Datapublication = aeron.addPublication(channel, 44);
//			nom_ecg_elec_plotl_avr_Datapublication = aeron.addPublication(channel, 45);
//			nomPlethDatapublication = aeron.addPublication(channel, 46);
			System.out.println("initialized.");
		} catch (Exception e) {
			System.err.println("Exception while initializing the Aeron");
		}

	}

	public long send(String publisherType, String message) {
		

		long result = 0;
		final byte[] messageBytes = message.getBytes();
	
		buffer.putBytes(0, messageBytes);

		switch (publisherType) {
		case "Numeric":
			// result = numericDatapublication.offer(buffer, 0, messageBytes.length);
			break;
		case "NOM_ECG_ELEC_POTL_II":
			result = nom_ecg_elec_plotl_II_DataPublication.offer(buffer, 0, messageBytes.length);
			break;
		case "NOM_ECG_ELEC_POTL_AVR":
			// result = nom_ecg_elec_plotl_avr_Datapublication.offer(buffer, 0,
			// messageBytes.length);
			break;
		case "NOM_ECG_ELEC_POTL_V":
			// result = nom_ecg_elec_plotl_V_Datapublication.offer(buffer, 0,
			// messageBytes.length);
			break;
		case "NOM_PLETH":
			// result = nomPlethDatapublication.offer(buffer, 0, messageBytes.length);
			break;
		default:
			break;
		}

		// Try to publish the buffer. 'offer' is a non-blocking call.
		// If it returns less than 0, the message was not sent, and the offer should be
		// retried.

		if (result < 0L)

		{
			if (result == Publication.BACK_PRESSURED) {
				System.out.println(" Offer failed due to back pressure");
			} else if (result == Publication.NOT_CONNECTED) {
				System.out.println(" Offer failed because publisher is not connected to subscriber");
			} else if (result == Publication.ADMIN_ACTION) {
				System.out.println("Offer failed because of an administration action in the system");
			} else if (result == Publication.CLOSED) {
				System.out.println("Offer failed publication is closed");
			} else if (result == Publication.MAX_POSITION_EXCEEDED) {
				System.out.println("Offer failed due to publication reaching max position");
			} else {
				System.out.println(" Offer failed due to unknown reason");
			}
		} else {
			// System.out.println(" yay !!");
		}
		return result;

	}
}
