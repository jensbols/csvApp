package domain;

import java.io.File;

public class Software365 {
	
	private CSVReader csvReader;
	private CSVWriter csvWriter;

	public Software365() {
		this.csvReader = new CSVReader();
		this.csvWriter = new CSVWriter();
	}
	public void writeCsvToDb(File file, String tableName) {
		csvReader.read(file, tableName);
	}
	public void writeToCsvFromDb(File file, String tableName) {
		csvWriter.write(file, tableName);
		
	}

}
