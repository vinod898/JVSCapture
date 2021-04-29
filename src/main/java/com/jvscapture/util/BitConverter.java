package com.jvscapture.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BitConverter {

	public static byte[] shortToBytes(short value) {
		byte[] returnByteArray = new byte[2];
		returnByteArray[1] = (byte) (value & 0xff);
		returnByteArray[0] = (byte) ((value >>> 8) & 0xff);
		return returnByteArray;
	}

	public static short bytesToShort(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(bytes[0]);
		bb.put(bytes[1]);
		short shortVal = bb.getShort(0);
		return shortVal;
	}

	public static byte[] getBytesU16(long value) {

		ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.nativeOrder());
		buffer.putLong(value);
		return buffer.array();
	}

	public static byte[] getBytesU16(short value) {

		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.putShort(value);
		return buffer.array();
	}

	public static int toInt32_2(byte[] bytes, int index) {
		int a = (int) ((int) (0xff & bytes[index]) << 32 | (int) (0xff & bytes[index + 1]) << 40
				| (int) (0xff & bytes[index + 2]) << 48 | (int) (0xff & bytes[index + 3]) << 56);
		// int a = (int)((int)(0xff & bytes[index]) << 56 | (int)(0xff &
		// bytes[index + 1]) << 48 | (int)(0xff & bytes[index + 2]) << 40 |
		// (int)(0xff & bytes[index + 3]) << 32);
		// Array.Resize;
		return a;
	}

	public static short toInt16(byte[] bytes, int index) // throws Exception
	{
		return (short) ((bytes[index + 1] & 0xFF) | ((bytes[index] & 0xFF) << 0));
		// return (short)(
		// (0xff & bytes[index]) << 8 |
		// (0xff & bytes[index + 1]) << 0
		// );
	}

	public static byte[] getBytesU32(long value) {
		ByteBuffer buffer = ByteBuffer.allocate(16).order(ByteOrder.nativeOrder());
		buffer.putLong(value);
		return buffer.array();
	}

	public static byte[] getBytesU32(int value) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(value);
		return buffer.array();
	}

}
