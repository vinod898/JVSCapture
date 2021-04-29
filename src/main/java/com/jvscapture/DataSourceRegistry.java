package com.jvscapture;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.jvscapture.datasource.VSDataSource;

public class DataSourceRegistry {

	private static List<VSDataSource> dataSources = new LinkedList<VSDataSource>();

	public static VSDataSource get(String id) {

		if (id == null) {
			return null;
		}

		return dataSources.stream().filter(ds -> id.equals(ds.getIdentifier())).findAny().orElse(null);
	}

	public static List<String> list() {
		return dataSources.stream()
			    .map(VSDataSource::getIdentifier)
			    .collect(Collectors.toList());
	}

	public static void add(VSDataSource ds) {
		dataSources.add(ds);
	}

	public static void remove(VSDataSource ds) {
		dataSources.remove(ds);
	}
}
