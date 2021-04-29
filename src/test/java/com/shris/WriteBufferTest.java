package com.shris;

import java.util.Arrays;

import com.jvscapture.datasource.philips.utils.CRCMethods;

public class WriteBufferTest {
	public static void main(String args[]) {

		byte[] testArray1 = { 12, 15 };
		byte[] testArray2 = { 12, 15, -64 };
		byte[] testArray3 = { 12, 15, (byte) 192 };

		CRCMethods cRCMethods = new CRCMethods();
		short checksumcalc = (short) cRCMethods.getFCS(testArray1);
		byte[] result1 = cRCMethods.onesComplement(checksumcalc);
		System.out.println(result1.length);
		System.out.println(Arrays.toString(result1));

		short checksumcalc2 = (short) cRCMethods.getFCS(testArray2);
		byte[] result2 = cRCMethods.onesComplement(checksumcalc2);
		System.out.println(result2.length);
		System.out.println(Arrays.toString(result2));

		short checksumcalc3 = (short) cRCMethods.getFCS(testArray3);
		byte[] result3 = cRCMethods.onesComplement(checksumcalc3);
		System.out.println(result3.length);
		System.out.println(Arrays.toString(result3));

	}

}
