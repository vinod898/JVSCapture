//package com.jvscapture.datasource.philips.services;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import com.jvscapture.datasource.philips.InputData;
//
//public class DataExportService {
//
//	private Map<String, String> properties;
//
//	public DataExportService(Map<String, String> properties) {
//		this.properties = properties;
//	}
//
//	private void sendToAeron() {
//		// TODO Auto-generated method stub
//
//	}
//
//	private void print(List<Map<String, String>> mapList) {
//
//		for (Iterator<Map<String, String>> iterator = mapList.iterator(); iterator.hasNext();) {
//			Map<String, String> map = iterator.next();
//			for (String name : map.keySet()) {
//				String key = name.toString();
//				String value = map.get(name).toString();
//				System.out.print(key + " : " + value + " , ");
//			}
//			System.out.println();
//		}
//
//	}
//
//	private void CSV_JSON_UrlExport() {
//
//	}
//
//	private void csvExport() {
//
//		switch (this.inputData.getCsvExportMode()) {
//		case Single_value_list:
//			SaveNumericValueList();
//			break;
//		case Data_packet_list:
//			SaveNumericValueListRows();
//			break;
//		case Consolidated_data_list:
//			SaveNumericValueListConsolidatedCSV();
//			break;
//		default:
//			break;
//		}
//
//	}
//
//	private void SaveNumericValueListConsolidatedCSV() {
//		// TODO Auto-generated method stub
//
//	}
//
//	private void SaveNumericValueListRows() {
//		// TODO Auto-generated method stub
//
//	}
//
//	private void SaveNumericValueList() {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void export(String string, List<Map<String, String>> mapList) {
//
//		switch (inputData.getOutputMode()) {
//		case Export_CSV_FILES:
//			csvExport();
//			break;
//		case Export_CSV_FILES_JSON_URL:
//
//			break;
//		case Write_Console:
//			print(mapList);
//
//			break;
//		case Write_Aeron:
//
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//}
