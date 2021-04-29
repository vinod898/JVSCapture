package com.jvscapture.datasource.enums;

import com.jvscapture.datasource.DataSource;

public enum InputMode implements DataSource {

	Socket("Connect via MIB RS232 port", 1), LAN("Connect via LAN port", 2);

	private final String description;
	private final int value;

	private InputMode(String description, int value) {
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
		for (InputMode mode : InputMode.values()) {
			if (mode.getValue() == value)
				return mode;
		}
		return null;
	}

}
