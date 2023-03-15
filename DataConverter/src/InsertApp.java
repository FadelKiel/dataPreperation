

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class InsertApp {
	

	
	public static void createNewTable() throws IOException, ParseException, SQLException {
		String url = "jdbc:sqlite:/C:\\sqlite\\usersdb.db";

		String dropSQL = "DROP TABLE IF EXISTS tabelle1";
		String sql = "CREATE TABLE tabelle1(\n"
				 + "rowid integer\n"
				 + ");";
		Connection conn = DriverManager.getConnection(url);
		Statement stmt = conn.createStatement();
		try (conn; stmt) {
			stmt.execute(dropSQL);
			stmt.execute(sql);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {

			JSONParser jsonparser = new JSONParser();
			FileReader reader = new FileReader("src\\Sources\\config.json");
			Object parseObj = jsonparser.parse(reader);
			JSONObject dataJsonobj = (JSONObject)parseObj;
			JSONArray jArray = (JSONArray)dataJsonobj.get("data");
			for(int k =0; k<jArray.size();k++ ) {
				
				JSONObject config= (JSONObject) jArray.get(k);
				String header = (String) config.get("name");
				String alterSQL = "ALTER TABLE tabelle1 ADD COLUMN "+ header +";";
				
				Connection connectHeader = DriverManager.getConnection(url);
				Statement statmentHeader = connectHeader.createStatement();
				
				try (connectHeader; statmentHeader) {
					statmentHeader.execute(alterSQL);

				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				
			
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private Connection connect() {
        String url = "jdbc:sqlite:/C:\\sqlite\\usersdb.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	 public void insert(String name, String value, int LineCounter) {
	        String sql = "UPDATE tabelle1 SET "+ name +" = '"+value+"' WHERE rowid= "+LineCounter+" ;";
	        

	        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.executeUpdate();
	            System.out.println("done "+sql);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	 
	 
	 public void greateNewRow(int LineCounter) throws SQLException {
		    String url = "jdbc:sqlite:/C:\\sqlite\\usersdb.db";
			String sqlRowid = "insert into tabelle1 ( rowid ) VALUES(\""+LineCounter+"\");";
			System.out.println(sqlRowid);
			Connection connRowid = DriverManager.getConnection(url);
			Statement stmtRowid = connRowid.createStatement();
			try (connRowid; stmtRowid) {
				
				stmtRowid.execute(sqlRowid);

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		 
	 }


}
