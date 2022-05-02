package domain;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import persistence.Writer;

public class CSVReader {

	public void read(File file, String tableName) {
		CSVParser parser = null;
		CSVFormat format = null;
		
		format = CSVFormat.Builder.create().setHeader().build();
		
		try {
			parser = CSVParser.parse(new FileReader(file), format);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		List<String> headerList = parser.getHeaderNames();
		Iterator<CSVRecord> iterator = parser.iterator();
		
		Writer.write(tableName, headerList, iterator, true);
	}
}
