import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class DataConverter {

	public static void main(String[] args) throws IOException, ParseException, SQLException {
		File datei = new File("src/Sources/DSSEPA.RZP.20220630_20221027");
		
		
		Scanner scan = scan = new Scanner(datei);;
		JSONArray myList = new JSONArray(); 
		int LineCounter = 0; 
		JSONObject obj = new JSONObject();
		InsertApp app = new InsertApp();
		app.createNewTable();
		while (scan.hasNext()) {
			String line = scan.nextLine().toString(); 
			LineCounter++; 
			System.out.println("Inhalt der Zeile Nummer " + LineCounter);
			app.greateNewRow(LineCounter);
			JSONParser jsonparser = new JSONParser();
			FileReader reader = new FileReader("src\\Sources\\config.json");
			Object parseObj = jsonparser.parse(reader);
			JSONObject dataJsonobj = (JSONObject)parseObj;
			JSONArray jArray = (JSONArray)dataJsonobj.get("data");
			System.out.println(line);
			for(int k =0; k<jArray.size();k++ ) {
				
				JSONObject config= (JSONObject) jArray.get(k);
				String name = (String) config.get("name");
				String startPosition = (String) config.get("startPosition");
				int IntStartPosition = Integer.parseInt(startPosition);
				int richtigeIntStartposition = IntStartPosition -1;
				String laenge = (String) config.get("laenge");
				int IntLaenge = Integer.parseInt(laenge);
				int richtigeIntLaenge = IntLaenge -1;
				int endPostion =IntStartPosition + richtigeIntLaenge;
				System.out.println(name+" : " + line.substring(richtigeIntStartposition, endPostion));
				System.out.println(richtigeIntStartposition );
				System.out.println( endPostion);
				obj.put(name, line.substring(richtigeIntStartposition, endPostion));
				app.insert(name, line.substring(richtigeIntStartposition, endPostion), LineCounter);
			}
			myList.add(obj);
		}
		System.out.println(myList.toString());
		System.out.println("finish");
		try (FileWriter file = new FileWriter("Inhalt.json")) { 
			file.write(myList.toJSONString());
			file.flush();
		} catch (IOException e) {
			System.out.println("ERROR");
		}
	}
}
