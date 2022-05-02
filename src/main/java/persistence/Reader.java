package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.JDBCConnection;

public class Reader {

	private static final String SQL_SELECT_ALL = "SELECT * FROM ${table}";
	private static final String TABLE_REGEX = "\\$\\{table\\}";
	
	public static ResultSet read(String tableName) {
		String query = getQueryString(tableName);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = JDBCConnection.getConnection();
		} catch (SQLException e) {
			System.out.println("Failed to get DB connection");
			e.printStackTrace();
		}
		
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("Erorr while creating statement");
			e.printStackTrace();
		}
		
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("Error while executing query");
		}
		
		return rs;
	}
	
	private static String getQueryString(String tableName) {
		return SQL_SELECT_ALL.replaceFirst(TABLE_REGEX, tableName);
		
	}
}
