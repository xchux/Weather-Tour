package Editschedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.*;

import com.google.gson.Gson;

public class Editschedule {
	JSONArray jsonArray;
	public JSONArray responseJSONArray;
	JSONObject jsonObject;
	String latitude, longitude, starttime, transport, startPosition;
	int distance = 0, time = 0;
	String html, api = "AIzaSyBv_MJeP3_NWhpU7QP-CG1hSWnrKytLjDI";
	Calendar c = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	Date departdate, arrivaldate = new Date();
	Editschedule_Mysql editsql;

	// data -> [ {\"id\":\"\", \"playtime\":\"\",\"url\" :\"\"},....]
	public Editschedule(String latitude, String longitude, String starttime, String transport, String data) {
		editsql = new Editschedule_Mysql();
		this.latitude = latitude;
		this.longitude = longitude;
		this.starttime = starttime;
		this.transport = transport;
		responseJSONArray = new JSONArray();
		try {
			jsonArray = new JSONArray(data);
			startPosition = latitude + "," + longitude;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void rebuild() throws Exception {
		String departureTime=starttime,previous_point=startPosition,current_point,arrivalTime,playtime;
		for(int i=0;i<jsonArray.length();i++) {
			
			String place_id= jsonArray.getJSONObject(i).getString("id");
			
			editsql.SelectplaceTable(place_id);
			current_point=editsql.place.get(2)+","+editsql.place.get(3);
			departdate=sdf.parse(departureTime);
			
			decode(previous_point,current_point);
			
			c.setTime(departdate);			
			
			c.add(Calendar.HOUR, Integer.valueOf(time/60/60));//transport_time			
			c.add(Calendar.MINUTE , Integer.valueOf(time/60%60));//transport_time			
			
			arrivalTime=sdf.format(c.getTime());			
			arrivaldate=sdf.parse(sdf.format(c.getTime()));
			String date=(arrivaldate.getMonth()+1<10?"0"+(arrivaldate.getMonth()+1):arrivaldate.getMonth()+1)
					+"/"+(arrivaldate.getDate()<10?"0"+arrivaldate.getDate():arrivaldate.getDate());
			System.out.println(date+"  "+arrivaldate.getHours());
			therehr_judge(editsql.place.get(4),date,arrivaldate.getHours());
			
			c.setTime(arrivaldate);
			playtime=jsonArray.getJSONObject(i).getString("playtime");
			c.add(Calendar.HOUR, Integer.valueOf(playtime));//play_time
								
			// {"departureTime":"2017-09-03
			// 20:40:03","arrivalTime":"13:10","playTime":"1","spotName":" 台北101
			// ","weatherIcon":"images/04.png","transportation":"images/car.png","startPosition":121.323,"spotID":"1","spotPicture":"images/taipei101.jpg"}
		
			JSONObject responseJSONObject = null;
		 HashMap userInfoMap = new HashMap();
			 userInfoMap.put("departureTime", departureTime);
			 userInfoMap.put("arrivalTime",arrivalTime.substring(11,16) );
			 userInfoMap.put("playTime",playtime );
			 userInfoMap.put("spotName", editsql.place.get(1));
			 userInfoMap.put("weatherIcon","images/"+editsql.threehr[0]+".png" );
			 userInfoMap.put("transportation", "images/"+transport+".png");
			 userInfoMap.put("startPosition",startPosition );
			 userInfoMap.put("spotID",place_id );
			 userInfoMap.put("spotPicture", jsonArray.getJSONObject(i).getString("url"));
		 responseJSONObject = new JSONObject(userInfoMap);
		 responseJSONArray.put(responseJSONObject);
		 
		 previous_point=current_point;
		departureTime=sdf.format(c.getTime());
		 }
	}

	public void therehr_judge(String place_postal_code, String date, int arrival_hr) {

		// String day = "";
		// String[] date_split = date.split("-");
		// System.out.println("th: "+date);
		// day = (date_split[1] + "/" + date_split[2]);// 日期
		String threehr_time = "";
		switch (arrival_hr / 3) {
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
		editsql.SelectthreehrTable(place_postal_code, date, threehr_time);

	}

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

	public void decode(String origins, String destinations) {

		try {

			html = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origins + "&destinations="
					+ destinations + "&mode=" + transport + "&key=" + api;
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
