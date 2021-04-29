package com.jvscapture.datasource.philips;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TooManyListenersException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import com.google.common.primitives.Bytes;
import com.jvscapture.datasource.DataSourceWriter;
import com.jvscapture.datasource.enums.WaveDataExportPriority;
import com.jvscapture.datasource.philips.constants.IntelliVue.DataConstants;
import com.jvscapture.datasource.philips.services.PocketProcessService;
import com.jvscapture.datasource.philips.services.UtilServices;
import com.jvscapture.datasource.philips.utils.CRCMethods;
import com.jvscapture.datasource.philips.utils.Utils;
import com.jvscapture.util.BitConverter;

/**
 * Single ton
 * 
 * @author
 *
 */
public class MPSerialPort implements SerialPortEventListener {

	private static MPSerialPort single_instance = null;

	private MPSerialPort() {
	}

	// static method to create instance of Singleton class
	public static MPSerialPort getInstance() {
		if (single_instance == null)
			single_instance = new MPSerialPort();

		return single_instance;
	}

//
	private PocketProcessService pocketProcessService = PocketProcessService.getInstance();
	private UtilServices utilServices = UtilServices.getInstance();
	private ArrayList<Byte> m_bList = new ArrayList<Byte>();
	public List<byte[]> frameList = new LinkedList<byte[]>();
	Scanner in = new Scanner(System.in);

	private SerialPort serialPort;
	private CommPortIdentifier portId;

	public String m_strTimestamp;
	private OutputStream outputSerialPortWriter;
	private InputStream inputSerialPortReader;
	private int mpPortBufSize = 4096;
	private byte[] mpPort_rxbuf = new byte[mpPortBufSize];

	private boolean m_storeend = false;
	private boolean m_storestart = false;
	private boolean m_bitshiftnext = false;
	private boolean isConnected = false;

	public void connect(String portName) throws UnsupportedCommOperationException, NoSuchPortException,
			PortInUseException, TooManyListenersException, IOException {

		Enumeration<?> ports = CommPortIdentifier.getPortIdentifiers();
		while (ports.hasMoreElements()) {
			CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();
			System.out.println(curPort.getName());
		}
		portId = CommPortIdentifier.getPortIdentifier(portName);
		serialPort = (SerialPort) portId.open("ComControl", 2000);
		serialPort.setDTR(true);
		serialPort.setRTS(true);
		// Set Baud rate, DataBits, StopBits, parity
		serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		serialPort.setInputBufferSize(4096);
		serialPort.enableReceiveTimeout(500);
		serialPort.enableReceiveThreshold(1024);
		OutputStream outputStream = serialPort.getOutputStream();
		InputStream inputStream = serialPort.getInputStream();
		System.out.println("Connected");
		setConnected(true);
		initIOStream(inputStream, outputStream);
		initListener();

	}

	public boolean initIOStream(InputStream inputStream, OutputStream outputStream) {
		// return value for whether opening the streams is successful or not
		boolean successful = false;
		outputSerialPortWriter = outputStream;
		inputSerialPortReader = inputStream;
		successful = true;
		return successful;
	}

	public void initListener() throws TooManyListenersException {
		serialPort.addEventListener(this);
		serialPort.notifyOnDataAvailable(true);
	}

	public void disconnect() {

		try {
			outputSerialPortWriter.write(DataConstants.assoc_abort_resp_msg);
			serialPort.removeEventListener();
			serialPort.close();
			outputSerialPortWriter.close();
			setConnected(false);
			System.out.println("Disonnected");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void serialEvent(SerialPortEvent evt) {
		if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				read();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void writeData(byte[] byteArray) {
		try {
			outputSerialPortWriter.write(byteArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(byte[] data) {
		writeBuffer(data);
	}

	private void writeBuffer(byte[] txbuf) {
		try {
			byte[] seriallist = Utils.createInstruction(txbuf);
			writeData(seriallist);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void read() {
		readData();
	}

	private void readData() {
		try {
			readBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int readBuffer() {
		int bytesreadtotal = 0;
		try {
			// Path path = Paths.get(System.getProperty("user.dir"),
			// "MPRawoutput1.raw");
			int lenread = 0;
			do {
				clearReadBuffer();
				lenread = inputSerialPortReader.read(mpPort_rxbuf, 0, mpPortBufSize);
				if (lenread == -1) {
					break;
				}
				byte[] copyarray = new byte[lenread];
//				System.out.println("Response : ");
//				System.out.println(Arrays.toString(mpPort_rxbuf));
				for (int i = 0; i < lenread; i++) {
					copyarray[i] = mpPort_rxbuf[i];
					createFrameListFromByte(copyarray[i]);
				}
				// byteArrayToFile(path, copyarray, copyarray.length);
				bytesreadtotal += lenread;
				/*
				 * if (FrameList.Count > 0) { ReadPacketFromFrame();
				 * 
				 * FrameList.RemoveRange(0, FrameList.Count);
				 * 
				 * }
				 */
			} while (lenread != 0);

			if (lenread == 0 || lenread == -1) {
				if (frameList.size() > 0) {
					readPacketFromFrame();
					frameList.clear();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bytesreadtotal;
	}

	private void readPacketFromFrame() {
		if (frameList.size() > 0)
			for (byte[] frameData : frameList)
				pocketProcessService.process(frameData);
	}

	// Clear the buffer
	public void clearReadBuffer() {
		for (int i = 0; i < mpPortBufSize; i++)
			mpPort_rxbuf[i] = 0;

	}

	public void createFrameListFromByte(byte b) {
		switch (b) {
		case (byte) DataConstants.BOFCHAR:
			m_storestart = true;
			m_storeend = false;
			break;
		case (byte) DataConstants.EOFCHAR:
			m_storestart = false;
			m_storeend = true;
			break;
		case DataConstants.ESCAPECHAR:
			m_bitshiftnext = true;
			break;
		default:
			if (m_bitshiftnext == true) {
				b ^= (DataConstants.BIT5COMPL);
				m_bitshiftnext = false;
				m_bList.add(b);
			} else if (m_storestart == true && m_storeend == false)
				m_bList.add(b);

			break;
		}

		if (m_storestart == false && m_storeend == true) {
			int framelen = m_bList.size();
			if (framelen != 0) {
				byte[] bArray = new byte[framelen];
				bArray = Bytes.toArray(m_bList);

				// serial header is 4 bytes and checksum 2 bytes
				int serialheaderwithuserdatalen = (framelen - 2);
				int serialuserdataframelen = (framelen - 6);
				byte[] dataArray = new byte[serialheaderwithuserdatalen];
				byte[] userdataArray = new byte[serialuserdataframelen];

				// Get header and user data without checksum
				System.arraycopy(bArray, 0, dataArray, 0, serialheaderwithuserdatalen);
				// Remove serial header and checksum to get actual packet
				System.arraycopy(bArray, 4, userdataArray, 0, serialuserdataframelen);

				// Read checksum
				byte[] checksumbytes = new byte[2];

				System.arraycopy(bArray, framelen - 2, checksumbytes, 0, 2);

				short checksum = BitConverter.toInt16(checksumbytes, 0);

				// Calculate checksum
				CRCMethods crccheck = new CRCMethods();

				short checksumcalc = (short) crccheck.getFCS(dataArray);
				byte[] checksumbytevalue = crccheck.onesComplement(checksumcalc);

				short checksumcomputed = BitConverter.toInt16(checksumbytevalue, 0);

				if (checksumcomputed == checksum) {
					frameList.add(userdataArray);
				} else {
					System.err.println("Checksum Error");
					System.err.println("checksum : " + checksum + " checksumcomputed :" + checksumcomputed);

				}

				// m_bList.Clear();
				m_bList.clear();
				m_storeend = false;
			}
		}
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public void fetch(DataSourceWriter outputDataWriter, InputData inputData) {
		long pollInterval = inputData.getNumericDataExportPriority().getTimeMills();
		pocketProcessService.init(outputSerialPortWriter, inputData, outputDataWriter);
		sendWaveAssociationRequest();
		Utils.sleep(500);
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate((new Runnable() {
			public void run() {
				sendExtendedPollDataRequest();
			}
		}), 2, pollInterval, TimeUnit.MILLISECONDS);

		Utils.sleep(500);

		WaveDataExportPriority waveDataExportPriority = inputData.getWaveDataExportPriority();
		if (!WaveDataExportPriority.None.equals(waveDataExportPriority)) {
			getRTSAPriorityListRequest();
			if (!WaveDataExportPriority.All.equals(waveDataExportPriority)) {
				setRTSAPriorityList(waveDataExportPriority);
			}
			Executors.newScheduledThreadPool(1).scheduleAtFixedRate((new Runnable() {
				public void run() {
					sendCycledExtendedPollWaveDataRequest();// interruption
				}
			}), 2, pollInterval, TimeUnit.MILLISECONDS);
		}

		// Recheck MDS Attributes

		Executors.newScheduledThreadPool(1).scheduleAtFixedRate((new Runnable() {
			public void run() {
				recheckMDSAttributes();// interruption
			}
		}), 2, pollInterval * 60, TimeUnit.MILLISECONDS);

		// Keep Connection Alive
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate((new Runnable() {
			public void run() {
				keepConnectionAlive();// interruption
			}
		}), 2, pollInterval * 6, TimeUnit.MILLISECONDS);

		String line = "";
		while (!"q".equalsIgnoreCase(line)) {

			System.out.print("Enter something (q to quite): ");

			line = in.nextLine();
			System.out.println("input : " + line);
			Utils.sleep(1000);
		}
	}

	private void setRTSAPriorityList(WaveDataExportPriority waveDataExportPriority) {
		byte[] finaltxbuff = utilServices.setRTSAPriorityList(waveDataExportPriority);
		write(finaltxbuff);
	}

	private void getRTSAPriorityListRequest() {
		write(DataConstants.get_rtsa_prio_msg);

	}

	private void sendWaveAssociationRequest() {
		write(DataConstants.aarq_msg_wave_ext_poll2);
	}

	private void sendExtendedPollDataRequest() {
		write(DataConstants.ext_poll_request_msg5);
	}

	private void keepConnectionAlive() {
		write(DataConstants.mds_create_resp_msg);
	}

	private void recheckMDSAttributes() {

		write(DataConstants.poll_mds_request_msg);
	}

	private void sendCycledExtendedPollWaveDataRequest() {
		write(DataConstants.ext_poll_request_wave_msg);

	}

	public PocketProcessService getPocketProcessService() {
		return pocketProcessService;
	}

	public void setPocketProcessService(PocketProcessService pocketProcessService) {
		this.pocketProcessService = pocketProcessService;
	}

	
	
	
}
