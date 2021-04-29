package com.jvscapture.datasource.enums;

import com.jvscapture.datasource.DataSource;

public enum OutputMode implements DataSource {
	Console("Print in Console", 1), CSV("Export CSV", 2), Aeron("Export Aeron", 3);

	private final String description;
	private final int value;

	private OutputMode(String description, int value) {
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
		for (OutputMode mode : OutputMode.values()) {
			if (mode.getValue() == value)
				return mode;
		}
		return null;
	}
}
