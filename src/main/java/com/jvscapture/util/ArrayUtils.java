package com.jvscapture.util;

import java.util.Arrays;

public class ArrayUtils {

	public static byte[] resize(byte[] complementVal, int size) {
		return Arrays.copyOfRange(complementVal, 0, size);
	}

	public static void reverse(byte[] array) {
		for (int i = 0; i < array.length / 2; i++) {
			byte temp = array[i];
			array[i] = array[array.length - i - 1];
			array[array.length - i - 1] = temp;
		}
	}
}
