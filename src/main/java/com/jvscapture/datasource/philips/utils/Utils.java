package com.jvscapture.datasource.philips.utils;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.common.primitives.Bytes;
import com.jvscapture.datasource.philips.constants.IntelliVue.DataConstants;
import com.jvscapture.datasource.philips.domain.SaCalibData16;
import com.jvscapture.util.BitConverter;

public class Utils {

	public static byte[] createInstruction(byte[] txbuf) {

		byte[] bofframebyte = { DataConstants.ESCAPECHAR, (DataConstants.BOFCHAR ^ DataConstants.BIT5COMPL), 0 };
		byte[] eofframebyte = { DataConstants.ESCAPECHAR, (DataConstants.EOFCHAR ^ DataConstants.BIT5COMPL), 0 };
		byte[] ctrlbyte = { DataConstants.ESCAPECHAR, (DataConstants.ESCAPECHAR ^ DataConstants.BIT5COMPL), 0 };

		List<Byte> temptxbufflist = new LinkedList<Byte>();
		for (byte b : txbuf) {
			switch (b) {
			case DataConstants.BOFCHAR:
				temptxbufflist.add(bofframebyte[0]);
				temptxbufflist.add(bofframebyte[1]);
				break;
			case DataConstants.EOFCHAR:
				temptxbufflist.add(eofframebyte[0]);
				temptxbufflist.add(eofframebyte[1]);
				break;
			case DataConstants.ESCAPECHAR:
				temptxbufflist.add(ctrlbyte[0]);
				temptxbufflist.add(ctrlbyte[1]);
				break;
			default:
				temptxbufflist.add(b);
				break;
			}
		}

		// When the control reacher here. The data you wanted to send is ready.

		// add 4 byte serial header
		byte[] serialframeheader = { 0x11, 0x01, 0x00, 0x00 };
		// ** VJ: Careful
		short serialuserdatalength = (short) txbuf.length;

		byte[] serialData = BitConverter.shortToBytes(serialuserdatalength);

		// if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
		// Collections.reverse(Arrays.asList(seriallength));
		// ArrayUtils.reverse(seriallength);
		// }

		// VJ: Write code to do this
		System.arraycopy(serialData, 0, serialframeheader, 2, 2);
		List<Byte> seriallist = new LinkedList<Byte>();
		for (Byte value : serialframeheader) {
			seriallist.add(value);
		}

		seriallist.addAll(temptxbufflist);

		byte[] inputbuff = new byte[(txbuf.length + 4)];
		// Same function
		System.arraycopy(serialframeheader, 0, inputbuff, 0, 4);
		System.arraycopy(txbuf, 0, inputbuff, 4, txbuf.length);

		// Check the status of inputbuff here

		// Calculate checksum from serial header with input buffer, not
		// transformed with transparency bytes
		CRCMethods cRCMethods = new CRCMethods();
		int checksumcalc = cRCMethods.getFCS(inputbuff);
		byte[] checksumbytes = cRCMethods.onesComplement(checksumcalc);

		for (int i = 0; i < checksumbytes.length; i++) {
			seriallist.add((byte) checksumbytes[i]);
		}

		seriallist.add(DataConstants.EOFCHAR);
		seriallist.add(0, DataConstants.BOFCHAR);

		return Bytes.toArray(seriallist);

	}

	// public static short correctendianshortus(int sValue) {
	//
	// byte[] bArray = BigInteger.valueOf(sValue).toByteArray();
	//
	// return (short) ((int) bArray[0] | (int) bArray[1] << 8);
	// }

	public boolean byteArrayToFile(Path path, byte[] copyarray, int length) {
		try {
			FileOutputStream stream = new FileOutputStream(path.toString());
			stream.write(copyarray, 0, length);
			// close file stream.
			stream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// public static int correctendianuint(int value) {
	// byte[] bArray = BitConverter.getBytesU32(value);
	// int result = BitConverter.toInt32_2(bArray, 0);
	// return result;
	// }

	// public static short correctendianshortus(int i) {
	//
	// byte[] seriallength = BitConverter.GetBytesU16(i);
	// short result = BitConverter.toInt16(seriallength, 0);
	// return result;
	// }

	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static double floattypeToValue(int fvalue) {
		double value = 0;
		if (fvalue != DataConstants.FLOATTYPE_NAN) {
			int exponentbits = (int) (fvalue >> 24);
			int mantissabits = (int) (fvalue << 8);
			mantissabits = (mantissabits >> 8);

			byte signedexponentbits = (byte) exponentbits; // Get Two's
															// complement signed
															// byte
			double exponent = (double) signedexponentbits;

			double mantissa = mantissabits;
			value = mantissa * Math.pow((double) 10, exponent);

			return value;
		} else
			return (double) fvalue;

	}

	public static int binaryCodedDecimalToInteger(byte value) {
		if (value != 0xFF) {
			int lowerNibble = value & 0x0F;
			int upperNibble = value >> 4;

			int multipleOfOne = lowerNibble;
			int multipleOfTen = upperNibble * 10;

			return (multipleOfOne + multipleOfTen);
		} else
			return 0;
	}

	public static Date getAbsoluteTimeFromRelativeTimestamp(long currentRelativeTime, long m_baseRelativeTime,
			Date m_baseDateTime) {
		double ElapsedTimeMilliseconds = Math
				.abs(((double) currentRelativeTime - (double) m_baseRelativeTime) * 125 / 1000);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(m_baseDateTime);
		calendar.set(Calendar.YEAR, 2021);
		calendar.add(Calendar.MILLISECOND, (int) ElapsedTimeMilliseconds);
		return calendar.getTime();
	}

	public static int get16bitLSBfromUInt(int m_idlabelhandle) {
		long lsb = (m_idlabelhandle & 0xFFFF);

		return (int) lsb;
	}

	public static int createMask(byte significant_bits) {
		int mask = 0;

		for (int i = 0; i < significant_bits; i++) {
			mask |= (1 << i);
		}

		return mask;
	}

	public static double calibrateSaValue(double waveval, SaCalibData16 sacalibdata) {

		if (!Double.isNaN(waveval)) {
			if (sacalibdata != null) {
				double prop = 0;
				double value = 0;
				double Wavevalue = waveval;

				// Check if value is out of range
				if (waveval > sacalibdata.getUpperScaledValue())
					waveval = sacalibdata.getUpperScaledValue();
				if (waveval < sacalibdata.getLowerScaledValue())
					waveval = sacalibdata.getLowerScaledValue();

				// Get proportion from scaled values
				if (sacalibdata.getUpperScaledValue() != sacalibdata.getLowerScaledValue()) {
					prop = (waveval - sacalibdata.getLowerScaledValue())
							/ (sacalibdata.getUpperScaledValue() - sacalibdata.getLowerScaledValue());
				}

				if (sacalibdata.getUpperAbsoluteValue() != sacalibdata.getLowerAbsoluteValue()) {
					value = sacalibdata.getLowerAbsoluteValue()
							+ (prop * (sacalibdata.getUpperAbsoluteValue() - sacalibdata.getLowerAbsoluteValue()));
					value = Math.round(value);
				}

				Wavevalue = value;
				return Wavevalue;
			} else
				return waveval;
		} else
			return waveval;
	}
}
