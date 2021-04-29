package com.jvscapture.datasource.enums;

import com.jvscapture.datasource.DataSource;

public enum Scale implements DataSource {

	Scaled("Export scaled values", 1), Calibrated("Export calibrated values", 2);

	private final String description;
	private final int value;

	private Scale(String description, int value) {
		this.description = description;
		this.value = value;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getValue() {
		return value;
	}


	public static DataSource get(int value) {
		for (Scale mode : Scale.values()) {
			if (mode.getValue() == value)
				return mode;
		}
		return null;
	}

}
