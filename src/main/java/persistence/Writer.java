package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import util.JDBCConnection;

public class Writer {

	private static final String SQL_INSERT = "INSERT INTO ${table}(${keys}) VALUES(${values})";
	private static final String TABLE_REGEX = "\\$\\{table\\}";
	private static final String KEYS_REGEX = "\\$\\{keys\\}";
	private static final String VALUES_REGEX = "\\$\\{values\\}";

	public static void write(String tableName, List<String> headerList, Iterator<CSVRecord> iterator, boolean truncateBeforeLoad) {
		String query = getQueryString(headerList, tableName);

		CSVRecord nextLine;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JDBCConnection.getConnection();
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("Failed to get DB connection");
			e.printStackTrace();
		}
		
		truncate(truncateBeforeLoad, tableName);

		try {
			System.out.println(query);
			ps = conn.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error while preparing statement");
		}

		final int batchSize = 1000;
		int count = 0;

		try {
			while (iterator.hasNext()) {
				nextLine = iterator.next();
				Iterator<String> recordIterator = nextLine.iterator();
				int index = 1;
				while (recordIterator.hasNext()) {
					String recordValue = recordIterator.next();
					ps.setString(index++, recordValue);
				}
				ps.addBatch();

				if (++count % batchSize == 0) {
					ps.executeBatch();
				}
			}
			ps.executeBatch(); //insert remaining records
			conn.commit();
			System.out.println("Succesfull");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error while inserting records in DB");
		}

	}

	private static String getQueryString(List<String> headerRow, String tableName) {
		String questionMarks = StringUtils.repeat("?,", headerRow.size());
		String correctQuestionMarks = (String)questionMarks.subSequence(0, questionMarks.length() - 1);
		
		String query = SQL_INSERT.replaceFirst(TABLE_REGEX, tableName);
		query = query.replaceFirst(KEYS_REGEX, String.join(",", headerRow));
		query = query.replaceFirst(VALUES_REGEX, correctQuestionMarks);

		return query;
	}
	
	private static void truncate(boolean bool, String tableName) {
		if(bool) {
			try {
				JDBCConnection.getConnection().createStatement().execute("DELETE FROM " + tableName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
