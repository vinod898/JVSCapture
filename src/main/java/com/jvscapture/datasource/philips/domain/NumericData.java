package com.jvscapture.datasource.philips.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NumericData {
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS")
	private Date time;
	private long relativetime;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS")
	private Date systemlocaltime;
	private double nomPressBldNoninvSys;
	private double nomPressBldNoninvDia;
	private double nomPressBldNoninvMean;
	private double nomEcgCardBeatRate;
	private double nomEcgAmplStI;
	private double nomEcgAmplStIi;
	private double nomEcgAmplStIii;
	private double nomEcgAmplStAvr;
	private double nomEcgAmplStAvl;
	private double nomEcgAmplStAvf;
	private double nomEcgAmplStV;
	private double nomEcgAmplStMcl;
	private double nomEcgVPCCnt;
	private double nomPulsOximSatO2;
	private double nomPlethPulsRate;
	private double nomPulsOximPerfRel;

	public NumericData() {

	}

	private Date parseDate(String value) {
		Date date = null;
		try {
			date = simpleDateFormat.parse(value);
		} catch (ParseException e) {
			System.out.println("error while parsing date " + value);
			e.printStackTrace();
		}
		return date;
	}

	private double parseDouble(String value) {
		double val = 0;
		try {
			val = Double.parseDouble(value);
		} catch (NumberFormatException e) {
		} catch (Exception e) {
			System.out.println("error while parsing val to Double " + value);
		}

		return val;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public long getRelativetime() {
		return relativetime;
	}

	public void setRelativetime(long relativetime) {
		this.relativetime = relativetime;
	}

	public Date getSystemlocaltime() {
		return systemlocaltime;
	}

	public void setSystemlocaltime(Date systemlocaltime) {
		this.systemlocaltime = systemlocaltime;
	}

	public double getNomPressBldNoninvSys() {
		return nomPressBldNoninvSys;
	}

	public void setNomPressBldNoninvSys(double nomPressBldNoninvSys) {
		this.nomPressBldNoninvSys = nomPressBldNoninvSys;
	}

	public double getNomPressBldNoninvDia() {
		return nomPressBldNoninvDia;
	}

	public void setNomPressBldNoninvDia(double nomPressBldNoninvDia) {
		this.nomPressBldNoninvDia = nomPressBldNoninvDia;
	}

	public double getNomPressBldNoninvMean() {
		return nomPressBldNoninvMean;
	}

	public void setNomPressBldNoninvMean(double nomPressBldNoninvMean) {
		this.nomPressBldNoninvMean = nomPressBldNoninvMean;
	}

	public double getNomEcgCardBeatRate() {
		return nomEcgCardBeatRate;
	}

	public void setNomEcgCardBeatRate(double nomEcgCardBeatRate) {
		this.nomEcgCardBeatRate = nomEcgCardBeatRate;
	}

	public double getNomEcgAmplStI() {
		return nomEcgAmplStI;
	}

	public void setNomEcgAmplStI(double nomEcgAmplStI) {
		this.nomEcgAmplStI = nomEcgAmplStI;
	}

	public double getNomEcgAmplStIi() {
		return nomEcgAmplStIi;
	}

	public void setNomEcgAmplStIi(double nomEcgAmplStIi) {
		this.nomEcgAmplStIi = nomEcgAmplStIi;
	}

	public double getNomEcgAmplStIii() {
		return nomEcgAmplStIii;
	}

	public void setNomEcgAmplStIii(double nomEcgAmplStIii) {
		this.nomEcgAmplStIii = nomEcgAmplStIii;
	}

	public double getNomEcgAmplStAvr() {
		return nomEcgAmplStAvr;
	}

	public void setNomEcgAmplStAvr(double nomEcgAmplStAvr) {
		this.nomEcgAmplStAvr = nomEcgAmplStAvr;
	}

	public double getNomEcgAmplStAvl() {
		return nomEcgAmplStAvl;
	}

	public void setNomEcgAmplStAvl(double nomEcgAmplStAvl) {
		this.nomEcgAmplStAvl = nomEcgAmplStAvl;
	}

	public double getNomEcgAmplStAvf() {
		return nomEcgAmplStAvf;
	}

	public void setNomEcgAmplStAvf(double nomEcgAmplStAvf) {
		this.nomEcgAmplStAvf = nomEcgAmplStAvf;
	}

	public double getNomEcgAmplStV() {
		return nomEcgAmplStV;
	}

	public void setNomEcgAmplStV(double nomEcgAmplStV) {
		this.nomEcgAmplStV = nomEcgAmplStV;
	}

	public double getNomEcgAmplStMcl() {
		return nomEcgAmplStMcl;
	}

	public void setNomEcgAmplStMcl(double nomEcgAmplStMcl) {
		this.nomEcgAmplStMcl = nomEcgAmplStMcl;
	}

	public double getNomEcgVPCCnt() {
		return nomEcgVPCCnt;
	}

	public void setNomEcgVPCCnt(double nomEcgVPCCnt) {
		this.nomEcgVPCCnt = nomEcgVPCCnt;
	}

	public double getNomPulsOximSatO2() {
		return nomPulsOximSatO2;
	}

	public void setNomPulsOximSatO2(double nomPulsOximSatO2) {
		this.nomPulsOximSatO2 = nomPulsOximSatO2;
	}

	public double getNomPlethPulsRate() {
		return nomPlethPulsRate;
	}

	public void setNomPlethPulsRate(double nomPlethPulsRate) {
		this.nomPlethPulsRate = nomPlethPulsRate;
	}

	public double getNomPulsOximPerfRel() {
		return nomPulsOximPerfRel;
	}

	public void setNomPulsOximPerfRel(double nomPulsOximPerfRel) {
		this.nomPulsOximPerfRel = nomPulsOximPerfRel;
	}

	public void set(String physioID, String value) {

		switch (physioID) {

		case "Time":
			time = parseDate(value);
			break;
		case "RelativeTime":
			relativetime = Long.parseLong(value);
			break;
		case "SystemLocalTime":
			systemlocaltime = parseDate(value);
			break;
		case "NOM_PRESS_BLD_NONINV_SYS":
			nomPressBldNoninvSys = parseDouble(value);
			break;
		case "NOM_PRESS_BLD_NONINV_DIA":
			nomPressBldNoninvDia = parseDouble(value);
			break;
		case "NOM_PRESS_BLD_NONINV_MEAN":
			nomPressBldNoninvMean = parseDouble(value);
			break;
		case "NOM_ECG_CARD_BEAT_RATE":
			nomEcgCardBeatRate = parseDouble(value);
			break;
		case "NOM_ECG_AMPL_ST_I":
			nomEcgAmplStI = parseDouble(value);
			break;
		case "NOM_ECG_AMPL_ST_II":
			nomEcgAmplStIi = parseDouble(value);
			break;
		case "NOM_ECG_AMPL_ST_III":
			nomEcgAmplStIii = parseDouble(value);
			break;
		case "NOM_ECG_AMPL_ST_AVR":
			nomEcgAmplStAvr = parseDouble(value);
			break;
		case "NOM_ECG_AMPL_ST_AVL":
			nomEcgAmplStAvl = parseDouble(value);
			break;
		case "NOM_ECG_AMPL_ST_AVF":
			nomEcgAmplStAvf = parseDouble(value);
			break;
		case "NOM_ECG_AMPL_ST_V":
			nomEcgAmplStV = parseDouble(value);
			break;
		case "NOM_ECG_AMPL_ST_MCL":
			nomEcgAmplStMcl = parseDouble(value);
			break;
		case "NOM_ECG_V_P_C_CNT":
			nomEcgVPCCnt = parseDouble(value);
			break;
		case "NOM_PULS_OXIM_SAT_O2":
			nomPulsOximSatO2 = parseDouble(value);
			break;
		case "NOM_PLETH_PULS_RATE":
			nomPlethPulsRate = parseDouble(value);
			break;
		case "NOM_PULS_OXIM_PERF_REL":
			nomPulsOximPerfRel = parseDouble(value);
			break;

		default:
			break;
		}

	}

	@Override
	public String toString() {
		return "NumericData [time=" + time + ", relativetime=" + relativetime + ", systemlocaltime=" + systemlocaltime
				+ ", nomPressBldNoninvSys=" + nomPressBldNoninvSys + ", nomPressBldNoninvDia=" + nomPressBldNoninvDia
				+ ", nomPressBldNoninvMean=" + nomPressBldNoninvMean + ", nomEcgCardBeatRate=" + nomEcgCardBeatRate
				+ ", nomEcgAmplStI=" + nomEcgAmplStI + ", nomEcgAmplStIi=" + nomEcgAmplStIi + ", nomEcgAmplStIii="
				+ nomEcgAmplStIii + ", nomEcgAmplStAvr=" + nomEcgAmplStAvr + ", nomEcgAmplStAvl=" + nomEcgAmplStAvl
				+ ", nomEcgAmplStAvf=" + nomEcgAmplStAvf + ", nomEcgAmplStV=" + nomEcgAmplStV + ", nomEcgAmplStMcl="
				+ nomEcgAmplStMcl + ", nomEcgVPCCnt=" + nomEcgVPCCnt + ", nomPulsOximSatO2=" + nomPulsOximSatO2
				+ ", nomPlethPulsRate=" + nomPlethPulsRate + ", nomPulsOximPerfRel=" + nomPulsOximPerfRel + "]";
	}

	public String toCSV() {
		return time + "," + relativetime + "," + systemlocaltime + "," + nomPressBldNoninvSys + ","
				+ nomPressBldNoninvDia + "," + nomPressBldNoninvMean + "," + nomEcgCardBeatRate + "," + nomEcgAmplStI
				+ "," + nomEcgAmplStIi + "," + nomEcgAmplStIii + "," + nomEcgAmplStAvr + "," + nomEcgAmplStAvl + ","
				+ nomEcgAmplStAvf + "," + nomEcgAmplStV + "," + nomEcgAmplStMcl + "," + nomEcgVPCCnt + ","
				+ nomPulsOximSatO2 + "," + nomPlethPulsRate + "," + nomPulsOximPerfRel;
	}

}
