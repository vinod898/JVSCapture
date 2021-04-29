package com.jvscapture.datasource.enums;

import com.jvscapture.datasource.DataSource;

public enum WaveDataExportPriority implements DataSource {

	None("None", 0), One("ECG I, II, III", 1), Two("ECG II, ABP, ART IBP, PLETH, CVP, RESP", 2),
	Three("ECG AVR, ECG AVL, ECG AVF", 3), Four("ECG V1, ECG V2, ECG V3", 4), Five("ECG V4, ECG V5, ECG V6", 5),
	Six("EEG1, EEG2, EEG3, EEG4", 6), Seven("ABP, ART IBP", 7), Eight("Compound ECG, PLETH, ABP, ART IBP, CVP, CO2", 8),
	Nine("ECG II, ART IBP, ICP, ICP2, CVP, TEMP BLOOD", 9), All("All", 10);

	private final String description;
	private final int value;

	private WaveDataExportPriority(String description, int value) {
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
		for (WaveDataExportPriority mode : WaveDataExportPriority.values()) {
			if (mode.getValue() == value)
				return mode;
		}
		return null;
	}
}
