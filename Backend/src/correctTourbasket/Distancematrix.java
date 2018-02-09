package correctTourbasket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Distancematrix {

	int distance = 0, time = 0;
	String html,api="AIzaSyAqLRk1nIAWQbCnNvZmbcyTy2O8gAm64Ek";

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public void decode(String origins, String destinations,String transport) {

		try {

			html = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origins + "&destinations="
					+ destinations + "&mode="+transport+"&key="+api;
			JSONObject j1 = readJsonFromUrl(html);
			JSONArray rows = j1.getJSONArray("rows");
			for (int i = 0; i < rows.length(); i++) {
				JSONArray temp = rows.getJSONObject(i).getJSONArray("elements");
				for (int j = 0; j < temp.length(); j++) {
					distance = temp.getJSONObject(j).getJSONObject("distance").getInt("value");
					time = temp.getJSONObject(j).getJSONObject("duration").getInt("value");
				}
			}

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
