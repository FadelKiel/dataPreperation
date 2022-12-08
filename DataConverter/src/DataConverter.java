import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataConverter {

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		File datei = new File("src/Sources/DSSEPA.RZP.20220630_20221027");
		Scanner scan = null;
		scan = new Scanner(datei);

		JSONArray myList = new JSONArray(); // damit wir späte alle Zeile in einem JSONArray speichern können
		int LineCounter = 0; // gibt am Ende den Zeilenanzahl
		while (scan.hasNext()) {
			String line = scan.nextLine().toString(); // läuft bis zum letzten Element in der Datei.
			LineCounter++; // erhöhen um 1 bei jedem Aufruf
			System.out.println("Inhalt der Zeile Nummer " + LineCounter);
		
			JSONParser jsonparser = new JSONParser();
			FileReader reader = new FileReader("src\\config.json");
			Object parseObj = jsonparser.parse(reader);
			JSONObject dataJsonobj = (JSONObject)parseObj;
			JSONArray jArray = (JSONArray)dataJsonobj.get("data");
			System.out.println(line);
			for(int k =0; k<jArray.size();k++ ) {
				
				
				JSONObject adress= (JSONObject) jArray.get(k);
				String name = (String) adress.get("name");
				String startPosition = (String) adress.get("startPosition");
				int IntStartPosition = Integer.parseInt(startPosition);
				int richtigeIntStartposition = IntStartPosition -1;
				String laenge = (String) adress.get("laenge");
				int IntLaenge = Integer.parseInt(laenge);
				int richtigeIntLaenge = IntLaenge -1;
				int endPostion =IntStartPosition + richtigeIntLaenge;
				JSONObject obj = new JSONObject();
				System.out.println(name+" : " + line.substring(richtigeIntStartposition, endPostion));

				//obj.put("kennzeichen", line.substring(IntStartPosition, IntLaenge));
			}
			
			// Zerlegen den Datensatz -- kann später gelöscht werden
			System.out.println("kennzeichen: " + line.substring(0, 4));
			System.out.println("anwendung: " + line.substring(4, 11));
			System.out.println("voat: " + line.substring(11, 13));
			System.out.println("sammelueb: " + line.substring(13, 14));
			System.out.println("panr: " + line.substring(14, 17));
			System.out.println("prnr: " + line.substring(17, 31));
			System.out.println("bugken: " + line.substring(31, 46));
			System.out.println("rcvgbic: " + line.substring(46, 57));
			System.out.println("sebic: " + line.substring(57, 68));
			System.out.println("ktokenn: " + line.substring(68, 70));
			System.out.println("endtoend: " + line.substring(70, 105));
			System.out.println("instdamt: " + line.substring(105, 116));
			System.out.println("credbic: " + line.substring(116, 127));
			System.out.println("credname: " + line.substring(127, 162));
			System.out.println("crediban: " + line.substring(162, 196));
			System.out.println("dt_last: " + line.substring(196, 204));
			System.out.println("dt_ausg: " + line.substring(204, 212));
			System.out.println("sndginst: " + line.substring(212, 223));
			System.out.println("instgagt: " + line.substring(223, 234));
			System.out.println("filler: " + line.substring(234, 310));
			System.out.println("rmtinf: " + line.substring(310, 450));
			System.out.println("-------------------");
			// 1- erstellen ein JSONObject
			// 2- speichern der Elemente im Object
			// 3- hinzufügen dieses Object zu myList
			JSONObject obj = new JSONObject();

			obj.put("kennzeichen", line.substring(0, 4));
			obj.put("anwendung", line.substring(4, 11));
			obj.put("voat", line.substring(11, 14));
			obj.put("sammelueb", line.substring(13, 14));
			obj.put("panr", line.substring(14, 17));
			obj.put("prnr", line.substring(17, 31));
			obj.put("bugken", line.substring(31, 46));
			obj.put("rcvgbic", line.substring(46, 57));
			obj.put("sebic", line.substring(57, 68));
			obj.put("ktokenn", line.substring(68, 70));
			obj.put("endtoend", line.substring(70, 105));
			obj.put("instdamt", line.substring(105, 116));
			obj.put("credbic", line.substring(116, 127));
			obj.put("credname", line.substring(127, 162));
			obj.put("crediban", line.substring(162, 196));
			obj.put("dt_last", line.substring(196, 204));
			obj.put("dt_ausg", line.substring(204, 212));
			obj.put("sndginst", line.substring(212, 223));
			obj.put("instgagt", line.substring(223, 234));
			obj.put("filler", line.substring(234, 310));
			obj.put("rmtinf", line.substring(310, 450));

			myList.add(obj);
		}
		// Ausgabe der gesamten Inhalt von myList.
		System.out.println(myList.toString());

		try (FileWriter file = new FileWriter("Inhalt.json")) { // create ein neues Json-datei
			file.write(myList.toJSONString()); // speichern myList in der Json-datei
			file.flush();
		} catch (IOException e) {
			System.out.println("ERROR");

		}

	}
}
