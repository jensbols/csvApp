package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {

	private static Connection conn;

	public static Connection getConnection() throws SQLException {
		if (conn == null || conn.isClosed()) {
			conn = establishNewConnection();
		}
		return conn;
	}

	private static Connection establishNewConnection() {
		Connection conn = null;
		String url = "jdbc:postgresql://localhost:5432/CSV";
		Properties props = new Properties();
		props.put("user", "postgres");
		props.put("password", "root");
		props.put("currentSchema", "csv");
		props.put("stringtype", "unspecified");

		try {
			conn = DriverManager.getConnection(url, props);
			System.out.println("Connected to db");
		} catch (Exception e) {
			System.out.println("something went wrong connecting to db");
			e.printStackTrace();
		}
		
		return conn;
	}
}
