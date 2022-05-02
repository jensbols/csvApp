package domain;

import java.io.File;

public class DomainController {

	private Software365 software365;
	
	public DomainController() {
		// TODO Auto-generated constructor stub
		this.software365 = new Software365();
	}
	
	public void readCsvToDb(File file, String tableName) {
		software365.writeCsvToDb(file, tableName);
	}
	
	public void writeToCsvFromDb(File file, String tableName) {
		software365.writeToCsvFromDb(file, tableName);
	}
}
