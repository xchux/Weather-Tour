package algorithm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;

public class Algorithm {
	Algorithm_Mysql mysql = new Algorithm_Mysql();
	String transport = "";/* , day = "2017-3-19", time = "06:30:30"; */ // "yyyy-MM-dd;
	public ArrayList<Order> order;

	String weather_status = "", rainfall_probability, arrival_date = "";
	int[] personal = new int[45];
	double[] personal_weight = new double[45];
	SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
	SimpleDateFormat hrft = new SimpleDateFormat("HH:mm");
	Date date, dNow = new Date();

	Calendar now = Calendar.getInstance();
	long nowDate = now.getTime().getTime(); // Date.getTime() 獲得毫秒型日期
	int[][] word_rate = { { 65, 64, 61, 60, 59, 38, 37, 36, 33, 32, 31, 19, 4 },
			{ 63, 57, 52, 51, 35, 30, 28, 27, 26, 25, 18, 17 }, { 62, 58, 56, 50, 49, 48, 21, 20, 14, 11 },
			{ 55, 42, 34, 29, 24, 16, 15, 13, 12 }, { 54, 47, 41, 23, 10 }, { 44, 40, 39, 6, 3 }, { 53, 45, 9, 5, 2 },
			{ 46, 22, 7 }, { 1 } };
	int[][] eight_quadrant;

	public Algorithm(String transport, String personal_list) {// personal_list->1,0,1,2,1...
		try {
			date = ft.parse(ft.format(dNow));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // current time
		order = new ArrayList();
		transportation(transport);
		mysql.SelectplaceTable();
		mysql.SelectactivityTable();
		mysql.SelectbridgeTable();
		mysql.SelectdistanceTable(this.transport);
		String[] temp = personal_list.split(",");
		for (int i = 0; i < temp.length; i++) {
			int[][] temps = new int[15][2];
			personal_weight[i] = 1.0;
			personal[i] = Integer.valueOf(temp[i]);
			temps[i % 15][0] = personal[i];
			temps[i % 15][1] = i;
			for (int j = i % 15; j < 0; j++) {
				if (temps[j][0] > temps[j - 1][0]) {
					int[] tempp = temps[j];
					temps[j] = temps[j - 1];
					temps[j - 1] = tempp;
				} else
					break;
			}
			for (int first = 0; first < 3; first++) {
				if (temps[first][0] > 7)
					personal_weight[temps[first][1]] = 1.2;
				else if (temps[first][0] > 3)
					personal_weight[temps[first][1]] = 1.1;
			}
			if (i % 15 == 0) {
				temps = new int[15][2];
			}
		}
	}

	// 給景點ID
	public void mathematic(String date, String time, int prefer, int place_id) throws Exception {
		// date "yyyy-MM-dd"
		// time "HH:mm:ss"
		// prefer*x+(100-prefer)*y
		System.out.println("mathematic");

		int max_distance = 0, min_distance = 10000, max_time = 0, min_time = 100000, rainfall;
		double max_point = 0.0, min_point = 1e9;
		int j = 0;

		ArrayList<ArrayList<Integer>> dis = mysql.distance;// 0:placeBID,1:distance,2:time
		ArrayList<ArrayList<Integer>> dis_list = new ArrayList<ArrayList<Integer>>();
		// place_id--;// to same as mysql.place.id
		for (int i = 0; i < mysql.place.size(); i++) {// list infro store
			int placeA_id, placeB_id;
			if (i == place_id) {// place_id-0~682
				i++;
				if (i > mysql.place.size())
					break;
			}
			placeA_id = (place_id > i) ? place_id : i;// big
			placeB_id = (place_id < i) ? place_id : i;// small
			dis_list.add(dis.get((placeA_id) * (placeA_id - 1) / 2 + placeB_id));// (bottom+1-1)*height/2
			j++;
			if (max_distance < dis_list.get(j - 1).get(2)) {
				max_distance = dis_list.get(j - 1).get(2);
			} else if (min_distance > dis_list.get(j - 1).get(2)) {
				min_distance = dis_list.get(j - 1).get(2);
			}
			if (max_time < dis_list.get(j - 1).get(3)) {
				max_time = dis_list.get(j - 1).get(3);
			} else if (min_time > dis_list.get(j - 1).get(3)) {
				min_time = dis_list.get(j - 1).get(3);
			}
		}

		for (int i = 0; i < dis_list.size(); i++) {

			int id = dis_list.get(i).get(0) - 1 != place_id ? dis_list.get(i).get(0) - 1 : dis_list.get(i).get(1) - 1;
			ArrayList<String> data = mysql.place.get(id);
			String arrival_time = time_translate(time, dis_list.get(i).get(3));// 抵達時間
//			System.out.println(arrival_time+"   "+time);
			arrival_date = hrft.parse(arrival_time+":00").before(hrft.parse(time)) ? date_translate(date): date ;
			therehr_judge(data.get(4), arrival_date, arrival_time);// get_weather_sql
			weather_status = mysql.threehr[0];
			rainfall_probability = mysql.threehr[1];

			try {
				rainfall = Integer.valueOf(rainfall_probability.substring(0, rainfall_probability.length() - 1));// 100%
			} catch (Exception e) {
				rainfall = 0;// no resource
			}
			double point = 0.0, distance_point, weather_point;
			distance_point = ((max_distance - dis_list.get(i).get(2)) * 1.0 / (max_distance - min_distance) * 100
					+ (max_time - dis_list.get(i).get(3)) * 1.0 / (max_time - min_time) * 100) / 2;
			// System.out.println(weather_status);
			int rate = weather_operates(weather_status);
			weather_point = weather_operate(id, rate, rainfall, Integer.valueOf(mysql.threehr[2]));

			point = personal(distance_point * prefer + (100 - prefer) * weather_point, data.get(7), rate);

			order.add(new Order(weather_status, rainfall_probability, mysql.threehr[2], mysql.distance.get(i).get(2),
					arrival_time, point, time, data));
			seturl(order.get(order.size() - 1));

		}
		bubble_sort();
	}

	// 給經緯度

	public void quadrant(double lat, double lng, String date, String time, int prefer) throws Exception { // 第一次
		System.out.println("quadrant");
		ArrayList<ArrayList<Integer>> four_quadrant = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 8; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			four_quadrant.add(temp);
		}
		for (int num = 0; num < mysql.place.size(); num++) {
			double operate_lat, operate_lng, operate_distance;
			operate_lat = Double.valueOf(mysql.place.get(num).get(2)) - lat;// 緯度
			operate_lng = Double.valueOf(mysql.place.get(num).get(3)) - lng;// 經度
			if (operate_lat >= 0 && operate_lng >= 0) {// No1 //
				if (operate_lat - operate_lng <= 0) {
					four_quadrant.get(0).add(num);
					sorting(operate_lat, operate_lng, four_quadrant, 0, lat, lng);
				} else {
					four_quadrant.get(1).add(num);
					sorting(operate_lat, operate_lng, four_quadrant, 1, lat, lng);
				}
			} else if (operate_lat < 0 && operate_lng > 0) {// No2 //
				if (operate_lat + operate_lng >= 0) {
					four_quadrant.get(2).add(num);
					sorting(operate_lat, operate_lng, four_quadrant, 2, lat, lng);
				} else {
					four_quadrant.get(3).add(num);
					sorting(operate_lat, operate_lng, four_quadrant, 3, lat, lng);
				}
			} else if (operate_lat <= 0 && operate_lng <= 0) {// No3 //
				if (operate_lat - operate_lng <= 0) {
					four_quadrant.get(4).add(num);
					sorting(operate_lat, operate_lng, four_quadrant, 4, lat, lng);
				} else {
					four_quadrant.get(5).add(num);
					sorting(operate_lat, operate_lng, four_quadrant, 5, lat, lng);
				}
			} else {// No4
				if (operate_lat + operate_lng <= 0) {
					four_quadrant.get(6).add(num);
					sorting(operate_lat, operate_lng, four_quadrant, 6, lat, lng);
				} else {
					four_quadrant.get(7).add(num);
					sorting(operate_lat, operate_lng, four_quadrant, 7, lat, lng);
				}
			}
		}
		eight_quadrant = new int[8][2];
		Distancematrix distance_matrix = new Distancematrix();
		for (int x = 0; x < 8; x++) {
			if (!four_quadrant.get(x).isEmpty()) {
				distance_matrix.decode(String.valueOf(lat) + "," + String.valueOf(lng),
						mysql.place.get(four_quadrant.get(x).get(0)).get(2) + ","
								+ mysql.place.get(four_quadrant.get(x).get(0)).get(3),
						transport);
				eight_quadrant[x][0] = distance_matrix.distance; // System.out.println(eight_quadrant[dis]);
				eight_quadrant[x][1] = distance_matrix.time;
			}
		}
		analyize(date, time, four_quadrant, prefer);
	}

	// 接續quadrant
	public void analyize(String date, String time, ArrayList<ArrayList<Integer>> quadrant, int prefer)
			throws Exception {
		double point = 0.0, weather_point, distance_point;
		double max_point = 0.0, min_point = 1e9;
		int max_distance = 0, min_distance = 10000, max_time = 0, min_time = 100000, rainfall, x = 0;
		ArrayList<ArrayList<Integer>> dis = mysql.distance;
		ArrayList<ArrayList<Integer>> dis_list = new ArrayList<ArrayList<Integer>>();
		String arrival_time = "", day = "";
		for (int i = 0; i < 8; i++) {
			if (min_distance > eight_quadrant[i][0]) {
				min_distance = eight_quadrant[i][0];
			} else if (min_time > eight_quadrant[i][1]) {
				min_time = eight_quadrant[i][1];
			}
			if (!quadrant.get(i).isEmpty()) {
				int placeA_id, placeB_id;
				int A_id = quadrant.get(i).get(0);
				for (int j = 1; j < quadrant.get(i).size(); j++) { // 非最近景點
					int B_id = quadrant.get(i).get(j);
					placeA_id = (A_id > B_id) ? A_id : B_id;// big
					placeB_id = (A_id < B_id) ? A_id : B_id;// small
					dis_list.add(dis.get((placeA_id) * (placeA_id - 1) / 2 + placeB_id));
					if (max_distance < dis_list.get(x).get(2) + eight_quadrant[i][0]) {
						max_distance = dis_list.get(x).get(2) + eight_quadrant[i][0];
					}
					if (max_time < dis_list.get(x).get(3) + eight_quadrant[i][1]) {
						max_time = dis_list.get(x).get(3) + eight_quadrant[i][1];
					}
					x++;
				}
			}
		}
		x = 0;
		for (int i = 0; i < 8; i++) {
			int placeA_id, placeB_id, rate = 0, find = 0;
			if (!quadrant.get(i).isEmpty()) {
				placeA_id = quadrant.get(i).get(0);
				for (int j = 1; j < quadrant.get(i).size(); j++, x++) { // 非最近景點
					placeB_id = quadrant.get(i).get(j);
					int min_to = (int) 1e9, min_position = 0, to_time = -1;
					for (int k = 0; k < 8; k++) { // 與最近8方位比時間
						if (!quadrant.get(k).isEmpty()) {
							int dist, a_id, b_id, headId = quadrant.get(k).get(0);
							a_id = headId > placeB_id ? headId : placeB_id;
							b_id = headId < placeB_id ? headId : placeB_id;
							dist = dis.get((a_id) * (a_id - 1) / 2 + b_id).get(3);// time
							if (dist < min_to) {// 與各方位最近點
								min_position = k;
								to_time = dist;
							}
						}
					}
					arrival_time = time_translate(time, eight_quadrant[min_position][1] + to_time);// 目前位置到A_再到B_時間
					
					arrival_date = hrft.parse(arrival_time+":00").before(hrft.parse(time)) ?  date_translate(date): date ;
//					System.out.println(mysql.place.get(quadrant.get(i).get(j)).get(4)+" "+ arrival_date+" "+ arrival_time);
					therehr_judge(mysql.place.get(quadrant.get(i).get(j)).get(4), arrival_date, arrival_time);
					weather_status = mysql.threehr[0];
					rainfall_probability = mysql.threehr[1];
					if (rainfall_probability != null)
						rainfall = Integer
								.valueOf(rainfall_probability.substring(0, rainfall_probability.length() - 1));// 100%->100
					else
						rainfall = 0; // no_resource

					
					rate = weather_operates(weather_status);
					weather_point = weather_operate(placeB_id, rate, rainfall, Integer.valueOf(mysql.threehr[2]));
					distance_point = ((max_distance - dis_list.get(x).get(2) - eight_quadrant[i][0]) * 1.0
							/ (max_distance - min_distance) * 100
							+ (max_time - dis_list.get(x).get(3) - eight_quadrant[i][1]) * 1.0 / (max_time - min_time)
									* 100)
							/ 2;
					ArrayList<String> data = mysql.place.get(quadrant.get(i).get(j));
					point = personal(distance_point * prefer + (100 - prefer) * weather_point, data.get(7), rate);
					if (max_point < point)
						max_point = point;
					else if (min_point > point)
						min_point = point;
					order.add(new Order(weather_status, rainfall_probability, mysql.threehr[2],
							mysql.distance.get(i).get(2), arrival_time, point, time, data));
					seturl(order.get(order.size() - 1));
					// System.out.println(" name: "+data.get(1)+"distance_point:
					// "+distance_point+" weather_point: "+weather_point);
				} // 非最近景點end //
				arrival_time = time_translate(time, eight_quadrant[i][1]);// 目前位置到A_再到B時間

				arrival_date = hrft.parse(arrival_time+":00").before(hrft.parse(time)) ?   date_translate(date): date ;
				therehr_judge(mysql.place.get(quadrant.get(i).get(0)).get(4), arrival_date, arrival_time);
				weather_status = mysql.threehr[0];
				rainfall_probability = mysql.threehr[1];
				if (rainfall_probability != null)
					rainfall = Integer.valueOf(rainfall_probability.substring(0, rainfall_probability.length() - 1));// 100%->100
				else
					rainfall = 0; // no_resource
				distance_point = ((max_distance - eight_quadrant[i][0]) * 1.0 / (max_distance - min_distance) * 100
						+ (max_time - eight_quadrant[i][1]) * 1.0 / (max_time - min_time) * 100) / 2;
				rate = weather_operates(weather_status);
				weather_point = weather_operate(placeA_id, rate, rainfall, Integer.valueOf(mysql.threehr[2]));
				ArrayList<String> data = mysql.place.get(quadrant.get(i).get(0));
				point = personal(distance_point * prefer + (100 - prefer) * weather_point, data.get(7), rate);
				if (max_point < point)
					max_point = point;
				else if (min_point > point)
					min_point = point;
				order.add(new Order(weather_status, rainfall_probability, mysql.threehr[2],
						mysql.distance.get(i).get(2), arrival_time, point, time, data));
				seturl(order.get(order.size() - 1));
				// System.out.println(" name: "+data.get(1)+"distance_point:
				// "+distance_point+" weather_point: "+weather_point);
			}
		}
		bubble_sort();
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
		mysql.SelectthreehrTable(place_postal_code, date, threehr_time);

	}

	public void bubble_sort() {
		for (int i = 1; i < order.size(); i++) {
			int j = i;
			while (0 < j && order.get(j).getPoint() > order.get(j - 1).getPoint()) {
				Collections.swap(order, j, j - 1);
				j--;
			}
		}

	}

	public double transform_score(double min, double max, double point) {
		return 100 - (point - min) / (max - min) * 100;
	}

	public double personal(double point, String stype, int rate) {
		int type = 0, weather_state = 0;
		double a;
		String[] array = { "休憩公園", "觀光工廠", "海岸風情", "湖岸風情", "廟宇建築", "觀光夜市", "步道健身", "森林遊樂區", "博物館", "風景區", "農牧場", "名勝古蹟",
				"瀑布", "遊樂園區", "購物中心" };
		weather_state = (rate / 3) > 1 ? 2 : rate / 3;
		for (int x = 0; x < 15; x++) {
			if (stype.equals(array[x])) {
				type = x;
				break;
			}
		}
		return point * personal_weight[weather_state * 15 + type] / 100;
	}

	// 抵達時間換算
	public String time_translate(String time, int distance_time) throws ParseException { // time
																							// HH:mm:ss

		String arrival_time = "";
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d = df.parse(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, distance_time / 60);
		arrival_time = df.format(cal.getTime());
		return arrival_time;
	}

	// 抵達時間換算
	public String date_translate(String date) throws ParseException { // time
																		// HH:mm:ss
//		System.out.println("IN  "+date);
		String day = "";
		String[] date_split = date.split("/");
		day = (date_split[0] + "/" + date_split[1]);// 日期
		String arrival_date = "";
		SimpleDateFormat df = new SimpleDateFormat("MM/dd");
		Date d = df.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, 1);
		arrival_date = df.format(cal.getTime());
//		System.out.println("out  "+arrival_date);
		return arrival_date;
	}

	// quadrant用
	public void sorting(double operate_lat, double operate_lon, ArrayList<ArrayList<Integer>> four_quadrant, int num,
			double Oringlat, double Oringlng) {
		double operate_distance, head_distance, lat, lon;
		operate_distance = Math.pow(Math.abs(operate_lat), 2) + Math.pow(Math.abs(operate_lon), 2);
		int placeNum = four_quadrant.get(num).get(0);
		lat = Double.valueOf(mysql.place.get(placeNum).get(2)) - Oringlat;
		lon = Double.valueOf(mysql.place.get(placeNum).get(3)) - Oringlng;
		head_distance = Math.pow(Math.abs(lat), 2) + Math.pow(Math.abs(lon), 2);

		if (operate_distance < head_distance) {// 距離最短
			Collections.swap(four_quadrant.get(num), 0, four_quadrant.get(num).size() - 1);// 最後一個跟第一個交換

		}
	}

	public void setactivity() throws JSONException {
		for (int i = 0; i < order.size(); i++) {
			JSONArray temp = new JSONArray("[]");
			String act = mysql.bridge.get(Integer.valueOf(order.get(i).place.get(0)) - 1).get(1);
			if (act != "[]") {
				JSONArray jsonArray = new JSONArray(act);
				for (int j = 0; j < jsonArray.length(); j++) {
					Date act_start_date = null, act_end_date = null;
					ArrayList<String> data = mysql.activity
							.get(Integer.valueOf((String) jsonArray.getJSONObject(j).get("id")) - 1);
					try {
						act_start_date = ft.parse(data.get(1).substring(0, 11));// judge
																				// weather
																				// during
																				// activity
																				// time
						act_end_date = ft.parse(data.get(2).substring(0, 11));
						if (date.after(act_start_date) && date.before(act_end_date)) {
							temp.put(jsonArray.get(j));
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			order.get(i).setactivity(temp.toString());
		}

	}

	public void seturl(Order order) {
		mysql.SelecturlTable(order.place.get(5));
		order.setUrl(mysql.url[0]);// 設定背景圖

	}

	public void transportation(String type) {
		if (type.equals("car"))
			transport = "distance_car";
		else if (type.equals("scooter"))
			transport = "distance_scooter";
		else
			transport = "distance_walk";
		System.out.println(transport);
	}

	public int weather_operates(String weather_status) {
		int rate = 0;
		for (rate = 0; rate < word_rate.length; rate++) {
			for (int a = 0; a < word_rate[rate].length; a++) {
				if (Integer.valueOf(weather_status) == word_rate[rate][a])
					return rate;
			}
		}
		return 0;
	}

	public double weather_operate(int i, int rate, int rain, int uv) {
		int c_uv = 0;
		double ratio = 1.0, c_rain = 0, index = 0.0, fix_rate = rate, fix_rain = rain;

		ArrayList<String> data = mysql.place.get(i);

		if (data.get(6).equals("all_in")) {
			double[] point = { 1.0, 1.1, 1.2, 1.3, 1.3 };
			ratio = point[(rain - 10) / 20];
			fix_rate = rate / 1.2 + 1.5;
			fix_rain = 100 - rain / 1.2;
		} else if (data.get(6).equals("most_in")) {
			if (uv > 10)
				c_uv = uv - 10;
			double[] point = { 1.0, 1.0, 1.1, 1.2, 1.3 };
			c_rain = point[(rain - 10) / 20];
			ratio = c_rain + 0.01 * c_uv;
			fix_rate = rate / 1.125 + 1;
			fix_rain = 100 - rain / 1.125;
		} else if (data.get(6).equals("most_out")) {
			if (uv > 10)
				c_uv = uv - 10;
			double[] point = { 1.0, 1.0, 0.9, 0.8, 0.7 };
			c_rain = point[(rain - 10) / 20];
			ratio = c_rain + 0.01 * c_uv;
			fix_rate = rate / 1.058 + 0.5;
			fix_rain = 100 - rain / 1.058;
		} else {
			if (uv > 10)
				c_uv = uv - 10;
			double[] point = { 1.0, 0.9, 0.8, 0.7, 0.7 };
			c_rain = point[(rain - 10) / 20];
			ratio = c_rain + 0.01 * c_uv;
		}
		return (ratio * (fix_rain + (fix_rate + 1) * 10) / 2) * 0.4 + 60;

	}
}
