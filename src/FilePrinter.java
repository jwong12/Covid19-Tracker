import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class FilePrinter {

	public static void printToCSV(String jsonData) throws IOException {
		Map<String, List<JSONObject>> jsonMap = new TreeMap<>();
		JSONArray jsonArray = new JSONArray(jsonData);
		File csvFile = new File("covid.csv");
		FileWriter csvWriter;
		System.out.println("Printing to '" + csvFile.toString() + "'...");

		if (!csvFile.exists()) {
			csvWriter = new FileWriter("covid.csv");
			csvWriter.append("Date, Province, Confirmed, Deaths, Recovered, Active, New Cases\n");

		} else {
			csvWriter = new FileWriter("covid.csv", true);
			csvWriter.append("\n");
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);

			if (jsonObj.getString("Province").length() == 0) {
				if (jsonMap.containsKey(" ")) {
					List<JSONObject> list = jsonMap.get(" ");
					list.add(jsonObj);

				} else {
					List<JSONObject> list = new ArrayList<JSONObject>();
					list.add(jsonObj);
					jsonMap.put(" ", list);
				}
				continue;
			}

			if (jsonMap.containsKey(jsonObj.getString("Province"))) {
				List<JSONObject> list = jsonMap.get(jsonObj.getString("Province"));
				list.add(jsonObj);

			} else {
				List<JSONObject> list = new ArrayList<JSONObject>();
				list.add(jsonObj);
				jsonMap.put(jsonObj.getString("Province"), list);
			}
		}

		for (List<JSONObject> list : jsonMap.values()) {
			int prevConfirmed = 0;

			for (int i = 0; i < list.size(); i++) {
				JSONObject jsonObj = list.get(i);
				int newCases = 0;

				csvWriter.append(jsonObj.getString("Date") + "," + //
						jsonObj.getString("Province") + "," + //
						jsonObj.getInt("Confirmed") + "," + //
						jsonObj.getInt("Deaths") + "," + //
						jsonObj.getInt("Recovered") + "," + //
						jsonObj.getInt("Active") + ",");

				if (i > 0) {
					newCases = jsonObj.getInt("Confirmed") - prevConfirmed;
				}

				csvWriter.append(newCases + "\n");
				prevConfirmed = jsonObj.getInt("Confirmed");
			}
		}

		csvWriter.flush();
		csvWriter.close();
		System.out.println("Finished printing to '" + csvFile.toString() + "'.");
	}
}
