package correctTourbasket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CorrectTour {
	correct_Mysql correctsql = new correct_Mysql();
	SimpleDateFormat hrft = new SimpleDateFormat("HH:mm");
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();
	String transport = "", lat, lon;

	String[] weather_arry = { "雷雨", "陣雨", "陣雨", "短暫陣雨", "多雲", "多雲時陰", "多雲時晴", "晴時多雲", "晴時多雲", "晴天" };
	int[] weather_box = { 0, 9, 6, 5, 0, 6, 5, 7, 8, 6, 4, 2, 3, 3, 2, 3, 3, 1, 1, 0, 2, 2, 7, 4, 3, 1, 1, 1, 1, 3, 1,
			0, 0, 0, 3, 1, 0, 0, 0, 5, 5, 4, 3, 8, 5, 6, 7, 4, 2, 2, 2, 1, 1, 6, 4, 3, 2, 1, 2, 0, 0, 0, 2, 1, 0, 0 };

	ArrayList<String> past_data = new ArrayList<String>();// departureTime,arrival_time,spotID
	public JSONArray JSONArray;

	public CorrectTour(String past, String data, String latitude, String longitude) throws Exception {
		lat = latitude;
		lon = longitude;
		correctsql.SelectplaceTable();
		JSONArray = new JSONArray(data);// tourbasket

		if (past != "") {// null schedule
			JSONObject JSONObject = new JSONObject(past);// schedule
			String type = JSONObject.getString("transportation").substring(7,
					JSONObject.getString("transportation").length() - 4);
			transportation(type);
			past_data.add(JSONObject.getString("departureTime").split(" ")[0]);

			cal.setTime(hrft.parse(JSONObject.getString("arrivalTime")));
			// System.out.println("cal.getTime(): " + cal.getTime());
			cal.add(Calendar.HOUR, Integer.valueOf(JSONObject.getString("playTime")));
			String arrival_time = hrft.format(cal.getTime());
			past_data.add(arrival_time);
			past_data.add(JSONObject.getString("spotID"));
			// System.out.println("aaaaa");
		} else {
			past_data.add(ft.format(cal.getTime()));
			past_data.add(hrft.format(cal.getTime()));
			past_data.add("-1");
		}

	}

	public void refresh() throws Exception {
		int Aid, Bid;
		String arrivalTime, departureTime;
		Aid = Integer.valueOf(past_data.get(2));

		departureTime = past_data.get(0).split("-")[1] + "/" + past_data.get(0).split("-")[2];//MM/dd
		// set arrival time & distance
		if (Aid > 0) {
			for (int i = 0; i < JSONArray.length(); i++) {
				cal.setTime(hrft.parse(past_data.get(1)));
				Bid = Integer.valueOf(JSONArray.getJSONObject(i).getString("spotID"));// schedule
//				 System.out.println(transport+" "+ Aid+" "+Bid);
				if (Aid > Bid)
					correctsql.SelectdistanceTable(transport, Aid, Bid);
				else
					correctsql.SelectdistanceTable(transport, Bid, Aid);
//				System.out.println(correctsql.distance.get(1));
				cal.add(Calendar.MINUTE, correctsql.distance.get(1) / 60 % 60);
				cal.add(Calendar.HOUR, correctsql.distance.get(1) / 60 / 60);
				
				arrivalTime = hrft.format(cal.getTime());
				JSONArray.getJSONObject(i).put("departureTime", past_data.get(0)+" "+past_data.get(1));
				JSONArray.getJSONObject(i).put("arrivalTime", arrivalTime);

				JSONArray.getJSONObject(i).put("distance",
						String.valueOf(Math.round(correctsql.distance.get(0) / 100) / 10.0));
				// set weather
				therehr_judge(correctsql.place.get(Bid - 1).get(4), departureTime, arrivalTime);
				// System.out.println(correctsql.place.get(Bid - 1).get(4));
				JSONArray.getJSONObject(i).put("weatherIcon", "images/" + correctsql.threehr.get(0) + ".png");
				JSONArray.getJSONObject(i).put("weatherOverview",
						weather_arry[weather_box[Integer.valueOf(correctsql.threehr.get(0))]]);
				JSONArray.getJSONObject(i).put("ultraviolet", correctsql.threehr.get(2));

			}

		} else {// need lat ,lun
			for (int i = 0; i < JSONArray.length(); i++) {
				cal.setTime(new Date());
				// System.out.println(cal.getTime());
				Bid = Integer.valueOf(JSONArray.getJSONObject(i).getString("spotID"));// schedule
				String type = JSONArray.getJSONObject(i).getString("transportation").substring(6,
						JSONArray.getJSONObject(i).getString("transportation").length() - 4);
				Distancematrix dismat = new Distancematrix();
				dismat.decode(lat + "," + lon,
						correctsql.place.get(Bid).get(2) + "," + correctsql.place.get(Bid).get(3), transport);
				cal.add(Calendar.MINUTE, dismat.time / 60 % 60);
				cal.add(Calendar.HOUR, dismat.time / 60 / 60);
				
				arrivalTime = hrft.format(cal.getTime());
				JSONArray.getJSONObject(i).put("departureTime", past_data.get(0)+" "+past_data.get(1));
				JSONArray.getJSONObject(i).put("arrivalTime", arrivalTime);
				JSONArray.getJSONObject(i).put("distance", String.valueOf(Math.round(dismat.distance / 100) / 10.0));
				// set weather
				therehr_judge(correctsql.place.get(Bid - 1).get(4), departureTime, arrivalTime);
				// System.out.println(correctsql.place.get(Bid - 1).get(4)+" "+ departureTime+"
				// "+ arrivalTime);
				JSONArray.getJSONObject(i).put("weatherIcon", "images/" + correctsql.threehr.get(0) + ".png");
				JSONArray.getJSONObject(i).put("weatherOverview",
						weather_arry[weather_box[Integer.valueOf(correctsql.threehr.get(0))]]);
				JSONArray.getJSONObject(i).put("ultraviolet", correctsql.threehr.get(2));
			}

		}

	}

	public void transportation(String type) {
//		System.out.println(type);
		if (type.equals("car"))
			transport = "distance_car";
		else if (type.equals("scooter"))
			transport = "distance_scooter";
		else
			transport = "distance_walk";

	}

	// 3hr 天氣
	public void therehr_judge(String place_postal_code, String date, String arrival_time) {

		String threehr_time = "";
		threehr_time = arrival_time.substring(0, 5);
		switch (Integer.valueOf(arrival_time.substring(0, 2)) / 3) {
		case 0:
			threehr_time = "00:00";
			break;
		case 1:
			threehr_time = "03:00";
			break;
		case 2:
			threehr_time = "06:00";
			break;
		case 3:
			threehr_time = "09:00";
			break;
		case 4:
			threehr_time = "12:00";
			break;
		case 5:
			threehr_time = "15:00";
			break;
		case 6:
			threehr_time = "18:00";
			break;
		case 7:
			threehr_time = "21:00";
			break;
		}
		correctsql.SelectthreehrTable(place_postal_code, date, threehr_time);

	}
}
