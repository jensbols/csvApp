package domain;

import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import persistence.Reader;

public class CSVWriter {

	public void write(File file, String tableName) {
		
		CSVPrinter printer = null;
		CSVFormat format = null;
		FileWriter writer = null;
		
		format = CSVFormat.Builder.create().setSkipHeaderRecord(false).build();
		
		try {
			writer = new FileWriter(file);
			printer = new CSVPrinter(writer, format);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while setting up printer & writer");
		}
		
		
		ResultSet rs = Reader.read(tableName);
		
		
		try {
			printer.printHeaders(rs);
			printer.printRecords(rs);
			writer.flush();		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
		
	}

}
