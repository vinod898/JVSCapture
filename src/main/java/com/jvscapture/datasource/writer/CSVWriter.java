package com.jvscapture.datasource.writer;

import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.jvscapture.datasource.DataSourceWriter;
import com.jvscapture.datasource.philips.domain.NumericData;
import com.jvscapture.datasource.philips.domain.WaveData;

public class CSVWriter extends DataSourceWriter {

	private String filePath;
	private CsvSchema schema = null;
	CsvMapper mapper = new CsvMapper();

	@Override
	public void flush() throws IOException {

	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public void write(NumericData numericData) throws IOException {
		String[] header = { "time", "relativetime", "systemlocaltime", "nomPressBldNoninvSys", "nomPressBldNoninvDia",
				"nomPressBldNoninvMean", "nomEcgCardBeatRate", "nomEcgAmplStI", "nomEcgAmplStIi", "nomEcgAmplStIii",
				"nomEcgAmplStAvr", "nomEcgAmplStAvl", "nomEcgAmplStAvf", "nomEcgAmplStV", "nomEcgAmplStMcl",
				"nomEcgVPCCnt", "nomPulsOximSatO2", "nomPlethPulsRate", "nomPulsOximPerfRel" };
		CsvSchema.Builder schemaBuilder = CsvSchema.builder();
		for (String col : header) {
			schemaBuilder.addColumn(col);
		}
		setSchema(schemaBuilder.build().withLineSeparator(System.lineSeparator()));
		setFilePath("files/NumericData.csv");
		write(numericData.toCSV());

	}

	@Override
	public void write(WaveData waveData) throws IOException {

		String[] header = { "time", "relativetime", "systemlocaltime", "value", "timeStamp" };
		CsvSchema.Builder schemaBuilder = CsvSchema.builder();
		for (String col : header) {
			schemaBuilder.addColumn(col);
		}
		setSchema(schemaBuilder.build().withLineSeparator(System.lineSeparator()));
		setFilePath("files/" + waveData.getPhysioID() + ".csv");
		write(waveData.toCSV());

	}

	@Override
	public void write(String message) throws IOException {
		String[] record = message.split(",");
		assert (message != null);
		assert (filePath != null);
		assert (schema != null);
		FileWriter writer = new FileWriter(filePath, true);
		if (message != null) {
			mapper.writer(schema).writeValues(writer).write(record);
			writer.flush();

		}

	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {

	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public CsvSchema getSchema() {
		return schema;
	}

	public void setSchema(CsvSchema schema) {
		this.schema = schema;
	}

}
