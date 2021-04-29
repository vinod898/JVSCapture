package com.jvscapture.datasource.philips.domain;

public enum AlarmCodes {

	NOM_EVT_ABSENT(4),

	NOM_EVT_CONTAM(14),

	NOM_EVT_DISCONN(22),

	NOM_EVT_DISTURB(24),

	NOM_EVT_EMPTY(26),

	NOM_EVT_ERRATIC(32),

	NOM_EVT_EXH(36),

	NOM_EVT_FAIL(38),

	NOM_EVT_HI(40),

	NOM_EVT_IRREG(58),

	NOM_EVT_LO(62),

	NOM_EVT_MALF(70),

	NOM_EVT_NOISY(74),

	NOM_EVT_OBSTRUC(80),

	NOM_EVT_REVERSED(96),

	NOM_EVT_SUST(106),

	NOM_EVT_UNAVAIL(110),

	NOM_EVT_UNDEF(112),

	NOM_EVT_WARMING(124),

	NOM_EVT_WEAK(128),

	NOM_EVT_BREATH_ABSENT(136),

	NOM_EVT_CALIB_FAIL(138),

	NOM_EVT_CONFIG_ERR(142),

	NOM_EVT_RANGE_ERR(164),

	NOM_EVT_RANGE_OVER(166),

	NOM_EVT_SRC_ABSENT(174),

	NOM_EVT_SYNCH_ERR(182),

	NOM_EVT_BATT_LO(194),

	NOM_EVT_BATT_PROB(198),

	NOM_EVT_CUFF_NOT_DEFLATED(230),

	NOM_EVT_CUFF_INFLAT_OVER(232),

	NOM_EVT_EQUIP_MALF(242),

	NOM_EVT_TUBE_OCCL(250),

	NOM_EVT_GAS_AGENT_IDENT_MALF(258),

	NOM_EVT_LEAD_DISCONN(268),

	NOM_EVT_LEADS_OFF(274),

	NOM_EVT_O2_SUPPLY_LO(296),

	NOM_EVT_OPTIC_MODULE_ABSENT(298),

	NOM_EVT_OPTIC_MODULE_DEFECT(300),

	NOM_EVT_SENSOR_DISCONN(308),

	NOM_EVT_SENSOR_MALF(310),

	NOM_EVT_SENSOR_PROB(312),

	NOM_EVT_SW_VER_UNK(322),

	NOM_EVT_TUBE_DISCONN(326),

	NOM_EVT_TUBE_OBSTRUC(330),

	NOM_EVT_XDUCR_DISCONN(336),

	NOM_EVT_XDUCR_MALF(338),

	NOM_EVT_INTENS_LIGHT_ERR(350),

	NOM_EVT_MSMT_DISCONN(352),

	NOM_EVT_MSMT_ERR(354),

	NOM_EVT_MSMT_FAIL(356),

	NOM_EVT_MSMT_INOP(358),

	NOM_EVT_MSMT_INTERRUP(362),

	NOM_EVT_MSMT_RANGE_OVER(364),

	NOM_EVT_MSMT_RANGE_UNDER(366),

	NOM_EVT_SIG_LO(380),

	NOM_EVT_SIG_UNANALYZEABLE(384),

	NOM_EVT_TEMP_HI_GT_LIM(394),

	NOM_EVT_UNSUPPORTED(400),

	NOM_EVT_WAVE_ARTIF_ERR(432),

	NOM_EVT_WAVE_SIG_QUAL_ERR(434),

	NOM_EVT_MSMT_INTERF_ERR(436),

	NOM_EVT_WAVE_OSCIL_ABSENT(442),

	NOM_EVT_VOLTAGE_OUT_OF_RANGE(460),

	NOM_EVT_INCOMPAT(600),

	NOM_EVT_ADVIS_CHK(6658),

	NOM_EVT_ADVIS_CALIB_AND_ZERO_CHK(6664),

	NOM_EVT_ADVIS_CONFIG_CHK(6666),

	NOM_EVT_ADVIS_SETTINGS_CHK(6668),

	NOM_EVT_ADVIS_SETUP_CHK(6670),

	NOM_EVT_ADVIS_SRC_CHK(6672),

	NOM_EVT_BATT_COND(6676),

	NOM_EVT_BATT_REPLACE(6678),

	NOM_EVT_ADVIS_CABLE_CHK(6680),

	NOM_EVT_ADVIS_GAS_AGENT_CHK(6688),

	NOM_EVT_ADVIS_LEAD_CHK(6690),

	NOM_EVT_ADVIS_SENSOR_CHK(6696),

	NOM_EVT_ADVIS_GAIN_DECR(6704),

	NOM_EVT_ADVIS_GAIN_INCR(6706),

	NOM_EVT_ADVIS_UNIT_CHK(6710),

	NOM_EVT_APNEA(3072),

	NOM_EVT_ECG_ASYSTOLE(3076),

	NOM_EVT_ECG_BEAT_MISSED(3078),

	NOM_EVT_ECG_BIGEM(3082),

	NOM_EVT_ECG_BRADY_EXTREME(3086),

	NOM_EVT_ECG_PACING_NON_CAPT(3102),

	NOM_EVT_ECG_PAUSE(3108),

	NOM_EVT_ECG_TACHY_EXTREME(3122),

	NOM_EVT_ECG_CARD_BEAT_RATE_IRREG(3158),

	NOM_EVT_ECG_PACER_NOT_PACING(3182),

	NOM_EVT_ECG_SV_TACHY(3192),

	NOM_EVT_ECG_V_P_C_RonT(3206),

	NOM_EVT_ECG_V_P_C_MULTIFORM(3208),

	NOM_EVT_ECG_V_P_C_PAIR(3210),

	NOM_EVT_ECG_V_P_C_RUN(3212),

	NOM_EVT_ECG_V_RHY(3220),

	NOM_EVT_ECG_V_TACHY(3224),

	NOM_EVT_ECG_V_TACHY_NON_SUST(3226),

	NOM_EVT_ECG_V_TRIGEM(3236),

	NOM_EVT_DESAT(3246),

	NOM_EVT_ECG_V_P_C_RATE(3252),

	NOM_EVT_STAT_AL_OFF(6144),

	NOM_EVT_STAT_BATT_CHARGING(6150),

	NOM_EVT_STAT_CALIB_MODE(6152),

	NOM_EVT_STAT_CALIB_RUNNING(6154),

	NOM_EVT_STAT_CALIB_INVIVO_RUNNING(6156),

	NOM_EVT_STAT_CALIB_LIGHT_RUNNING(6158),

	NOM_EVT_STAT_CALIB_PREINS_RUNNING(6160),

	NOM_EVT_STAT_SELFTEST_RUNNING(6164),

	NOM_EVT_STAT_ZERO_RUNNING(6170),

	NOM_EVT_STAT_OPT_MOD_SENSOR_CONN(6172),

	NOM_EVT_STAT_OPT_MOD_SENSOR_WARMING(6174),

	NOM_EVT_STAT_SENSOR_WARMING(6176),

	NOM_EVT_STAT_WARMING(6178),

	NOM_EVT_STAT_ECG_AL_ALL_OFF(6182),

	NOM_EVT_STAT_ECG_AL_SOME_OFF(6184),

	NOM_EVT_STAT_LEARN(6224),

	NOM_EVT_STAT_OFF(6226),

	NOM_EVT_STAT_STANDBY(6228),

	NOM_EVT_STAT_DISCONN(6256),

	NOM_EVT_ADVIS_CALIB_REQD(6662),

	NOM_EVT_ECG_V_FIB_TACHY(61444),

	NOM_EVT_WAIT_CAL(61678),

	NOM_EVT_ADVIS_CHANGE_SITE(61682),

	NOM_EVT_ADVIS_CHECK_SITE_TIME(61684),

	NOM_EVT_STAT_FW_UPDATE_IN_PROGRESS(61688),

	NOM_EVT_EXT_DEV_AL_CODE_1(61690),

	NOM_EVT_EXT_DEV_AL_CODE_2(61692),

	NOM_EVT_EXT_DEV_AL_CODE_3(61694),

	NOM_EVT_EXT_DEV_AL_CODE_4(61696),

	NOM_EVT_EXT_DEV_AL_CODE_5(61698),

	NOM_EVT_EXT_DEV_AL_CODE_6(61700),

	NOM_EVT_EXT_DEV_AL_CODE_7(61702),

	NOM_EVT_EXT_DEV_AL_CODE_8(61704),

	NOM_EVT_EXT_DEV_AL_CODE_9(61706),

	NOM_EVT_EXT_DEV_AL_CODE_10(61708),

	NOM_EVT_EXT_DEV_AL_CODE_11(61710),

	NOM_EVT_EXT_DEV_AL_CODE_12(61712),

	NOM_EVT_EXT_DEV_AL_CODE_13(61714),

	NOM_EVT_EXT_DEV_AL_CODE_14(61716),

	NOM_EVT_EXT_DEV_AL_CODE_15(61718),

	NOM_EVT_EXT_DEV_AL_CODE_16(61720),

	NOM_EVT_EXT_DEV_AL_CODE_17(61722),

	NOM_EVT_EXT_DEV_AL_CODE_18(61724),

	NOM_EVT_EXT_DEV_AL_CODE_19(61726),

	NOM_EVT_EXT_DEV_AL_CODE_20(61728),

	NOM_EVT_EXT_DEV_AL_CODE_21(61730),

	NOM_EVT_EXT_DEV_AL_CODE_22(61732),

	NOM_EVT_EXT_DEV_AL_CODE_23(61734),

	NOM_EVT_EXT_DEV_AL_CODE_24(61736),

	NOM_EVT_EXT_DEV_AL_CODE_25(61738),

	NOM_EVT_EXT_DEV_AL_CODE_26(61740),

	NOM_EVT_EXT_DEV_AL_CODE_27(61742),

	NOM_EVT_EXT_DEV_AL_CODE_28(61744),

	NOM_EVT_EXT_DEV_AL_CODE_29(61746),

	NOM_EVT_EXT_DEV_AL_CODE_30(61748),

	NOM_EVT_EXT_DEV_AL_CODE_31(61750),

	NOM_EVT_EXT_DEV_AL_CODE_32(61752),

	NOM_EVT_EXT_DEV_AL_CODE_33(61754),

	NOM_EVT_ST_MULTI(61756),

	NOM_EVT_ADVIS_BSA_REQD(61760),

	NOM_EVT_ADVIS_PRESUMED_CVP(61762),

	NOM_EVT_MSMT_UNSUPPORTED(61764),

	NOM_EVT_BRADY(61766),

	NOM_EVT_TACHY(61768),

	NOM_EVT_ADVIS_CHANGE_SCALE(61770),

	NOM_EVT_MSMT_RESTART(61772),

	NOM_EVT_TOO_MANY_AGENTS(61774),

	NOM_EVT_STAT_PULSE_SRC_RANGE_OVER(61778),

	NOM_EVT_STAT_PRESS_SRC_RANGE_OVER(61780),

	NOM_EVT_MUSCLE_NOISE(61782),

	NOM_EVT_LINE_NOISE(61784),

	NOM_EVT_IMPED_HI(61786),

	NOM_EVT_AGENT_MIX(61788),

	NOM_EVT_IMPEDS_HI(61790),

	NOM_EVT_ADVIS_PWR_HI(61792),

	NOM_EVT_ADVIS_PWR_OFF(61794),

	NOM_EVT_ADVIS_PWR_OVER(61796),

	NOM_EVT_ADVIS_DEACT(61798),

	NOM_EVT_CO_WARNING(61800),

	NOM_EVT_ADVIS_NURSE_CALL(61802),

	NOM_EVT_COMP_MALF(61804),

	NOM_EVT_AGENT_MEAS_MALF(61806),

	NOM_EVT_ADVIS_WATER_TRAP_CHK(61808),

	NOM_EVT_STAT_AGENT_CALC_RUNNING(61810),

	NOM_EVT_ADVIS_ADAPTER_CHK(61814),

	NOM_EVT_ADVIS_PUMP_OFF(61816),

	NOM_EVT_ZERO_FAIL(61818),

	NOM_EVT_ADVIS_ZERO_REQD(61820),

	NOM_EVT_EXTR_HI(61830),

	NOM_EVT_EXTR_LO(61832),

	NOM_EVT_LEAD_DISCONN_YELLOW(61833),

	NOM_EVT_LEAD_DISCONN_RED(61834),

	NOM_EVT_CUFF_INFLAT_OVER_YELLOW(61835),

	NOM_EVT_CUFF_INFLAT_OVER_RED(61836),

	NOM_EVT_CUFF_NOT_DEFLATED_YELLOW(61837),

	NOM_EVT_CUFF_NOT_DEFLATED_RED(61838),

	NOM_EVT_ADVIS_ACTION_REQD(61840),

	NOM_EVT_OUT_OF_AREA(61842),

	NOM_EVT_LEADS_DISCONN(61844),

	NOM_EVT_DEV_ASSOC_CHK(61846),

	NOM_EVT_SYNCH_UNSUPPORTED(61848),

	NOM_EVT_ECG_ADVIS_SRC_CHK(61850),

	NOM_EVT_ALARM_TECH(61852),

	NOM_EVT_ALARM_TECH_YELLOW(61854),

	NOM_EVT_ALARM_TECH_RED(61856),

	NOM_EVT_ALARM_MED_YELLOW_SHORT(61858),

	NOM_EVT_ALARM_MED_YELLOW(61860), NOM_EVT_ALARM_MED_RED(61862),

	NOM_EVT_TELE_EQUIP_MALF(61874),

	NOM_EVT_SYNCH_ERR_ECG(61876),

	NOM_EVT_SYNCH_ERR_SPO2T(61878),

	NOM_EVT_ADVIS_ACTION_REQD_YELLOW(61880),

	NOM_EVT_ADVIS_NBP_SEQ_COMPLETED(61882),

	NOM_EVT_PACER_OUTPUT_LO(61884),

	NOM_EVT_ALARM_MORE_TECH(61886),

	NOM_EVT_ALARM_MORE_TECH_YELLOW(61888),

	NOM_EVT_ALARM_MORE_TECH_RED(61890),

	NOM_EVT_ADVIS_PATIENT_CONFLICT(61892),

	NOM_EVT_SENSOR_REPLACE(61894),

	NOM_EVT_ECG_ATR_FIB(61896),

	NOM_EVT_LIMITED_CONNECTIVITY(61900),

	NOM_EVT_DISABLED(61924),

	NOM_EVT_ECG_ABSENT(61926),

	NOM_EVT_SRR_INTERF(61928),

	NOM_EVT_SRR_INVALID_CHAN(61930),

	NOM_EVT_EXT_DEV_DEMO(62032),

	NOM_EVT_EXT_DEV_MONITORING(62034);

	private int value;

	private AlarmCodes(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String get(int value) {
		for (AlarmCodes e : AlarmCodes.values()) {
			if (e.value == value)
				return e.name();
		}
		return null;
	}

}
