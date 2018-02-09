package breakdown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Breakdown {
	Breakdown_Mysql breakdown_Mysql = new Breakdown_Mysql();
	String weather_data = "";
	SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
	Date date;

	public Breakdown(int place_id) {
		breakdown_Mysql.Selectplace_infoTable(place_id);
		breakdown_Mysql.Selectplace_imgTable(breakdown_Mysql.place[5]);
		breakdown_Mysql.Selectmain_sub_bridgeTable(Integer.valueOf(breakdown_Mysql.place[0]));
		date = new Date();
	}

	public void setweather(String arrival_time, String date) throws Exception {
		// breakdown_Mysql.sevenday()
		breakdown_Mysql.SelectthreehrTable(breakdown_Mysql.place[4]);
		String day = "";
		String[] date_split = date.split("-");
		day = (date_split[1] + "/" + date_split[2]);// 日期
		String threehr_time = "";
		threehr_time = arrival_time.substring(0, 5);
		int threehr = Integer.valueOf(arrival_time.substring(0, 2)) / 3 * 3;///////////// error
		ArrayList<ArrayList<String>> threehrtemp = breakdown_Mysql.threehr;
		for (int i = 0; i < breakdown_Mysql.threehr.size(); i++) {
			// day & time equal
			// System.out.println(threehr);
			if (threehrtemp.get(i).get(1).equals(day)
					&& Integer.valueOf(threehrtemp.get(i).get(2).split(":")[0]) == threehr) {
//				System.out.println("Yes");
				for (int j = 0; j < 3; j++, i++) {
					if (j != 0)
						weather_data += "-";
					weather_data += breakdown_Mysql.threehr.get(i).toString();
				}
				break;
			}
		}

	}

	public String get_place() {// ID,name,lat,lng,
		String data = "";
		for (int i = 0; i < 4; i++) {
			data += breakdown_Mysql.place[i] + ",";
		}
		return data;
	}

	public String get_activity() throws Exception {
		String act = "";
		try {
			String activity = breakdown_Mysql.activity;
			JSONArray JSONArray = new JSONArray(activity);
			JSONArray temp = new JSONArray("[]");
			if (activity != "[]") {
				for (int i = 0; i < JSONArray.length(); i++) {
					Date act_start_date = null, act_end_date = null;
					breakdown_Mysql.SelectactivityTable(Integer.valueOf(JSONArray.getJSONObject(i).getString("id")));
					String[] data = breakdown_Mysql.activity_data;
					try {
						act_start_date = ft.parse(data[1].split(" ")[0]);
						act_end_date = ft.parse(data[2].split(" ")[0]);
						if (date.after(act_start_date) && date.before(act_end_date)) {
							System.out.println(breakdown_Mysql.place[2] + "," + breakdown_Mysql.place[3] + "   "
									+ breakdown_Mysql.activity_data[4] + "," + breakdown_Mysql.activity_data[5]);
							JSONObject tempObj = new JSONObject("{\"name\":\"" + data[0] + "\",\"info\":\"" + data[3]
									+ "\",\"distance\":\""
									+ distance(breakdown_Mysql.place[2] + "," + breakdown_Mysql.place[3],
											breakdown_Mysql.activity_data[4] + "," + breakdown_Mysql.activity_data[5])
									+ "\"}");
							System.out.println(tempObj.toString());
							temp.put(tempObj);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			act = temp.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return act;
	}

	public String get_play() {

		return breakdown_Mysql.play;
	}

	public String get_food() {

		return breakdown_Mysql.food;
	}

	public String get_weather() {

		return weather_data;
	}

	public String get_img() {
		String img = breakdown_Mysql.img[0];
		for (int i = 1; i < 5; i++) {
			img += ",";
			img += breakdown_Mysql.img[i];
		}
		return img;
	}

	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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

	public double distance(String father, String sub) throws Exception {
		String distance_html = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + father
				+ "&destinations=" + sub + "&key=AIzaSyCYRGQ5GgC54mTIXU-_C_b-PYIpgT4bv2c";
		double distance = 0.0;
		// System.out.println("0000");
		JSONObject d1 = readJsonFromUrl(distance_html);
		JSONArray rows = d1.getJSONArray("rows");

		JSONArray temp = rows.getJSONObject(0).getJSONArray("elements");
//		System.out.println(temp.getJSONObject(0).getJSONObject("distance").getInt("value"));
		distance = temp.getJSONObject(0).getJSONObject("distance").getInt("value");

		return Math.floor(distance / 10) / 100;
	}

}
