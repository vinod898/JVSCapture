package com.jvscapture.datasource.enums;

import com.jvscapture.datasource.DataSource;

public enum NumericDataExportPriority implements DataSource {

	None("None", 0, 1), OneSecondRealTime("1 second (Real time)", 1000, 2),
	TwelveSecondAveraged("12 second (Averaged)", 12000, 3), OneMinuteAveraged("1 minute (Averaged)", 60000, 4),
	FiveMinuteAveraged("5 minute (Averaged)", 30000, 5), SinglePoll("Single poll", 100, 6);

	private final String description;
	private final int value;
	private final long timeMills;

	private NumericDataExportPriority(String description, long timeMills, int value) {
		this.description = description;
		this.timeMills = timeMills;
		this.value = value;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public long getTimeMills() {
		return timeMills;
	}

	@Override
	public int getValue() {
		return value;
	}

	public static DataSource get(int value) {
		for (NumericDataExportPriority mode : NumericDataExportPriority.values()) {
			if (mode.getValue() == value)
				return mode;
		}
		return null;
	}

}
