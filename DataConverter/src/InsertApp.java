

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertApp {
	
	public static void createNewTable() {
		String url = "jdbc:sqlite:/C:\\sqlite\\usersdb.db";

		String dropSQL = "DROP TABLE IF EXISTS tabelle1";
		String sql = "CREATE TABLE tabelle1 (\n" 
						
						+ "	name text NOT NULL,\n" 
						+ "	value text \n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(dropSQL);
			stmt.execute(sql);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/C:\\sqlite\\usersdb.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	 public void insert(String name, String value) {
	        String sql = "INSERT INTO tabelle1(name,value) VALUES(?,?)";

	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, name);
	            pstmt.setString(2, value);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	

}
