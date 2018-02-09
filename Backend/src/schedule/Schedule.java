package schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Schedule {
	Schedule_Mysql mysql = new Schedule_Mysql();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar now = Calendar.getInstance();
	long nowDate = now.getTime().getTime();
	long betweenDate;
	String day, playtime, place_id, postal_code;
	ArrayList<String> weather = new ArrayList<String>();

	public Schedule(String date, String time, String place_id) throws Exception {

		long specialDate = sdf.parse(date + " " + time).getTime();
		betweenDate = (specialDate - nowDate) / (1000 * 60 * 60 * 24);// 日子差

		String[] date_split = date.split("-");
		day = (date_split[1] + "/" + date_split[2]);// 日期, time, playtime,
													// place_id
		this.place_id = place_id;
		mysql.SelectplaceTable(Integer.valueOf(place_id));

		postal_code = mysql.place[2];
	}

	public void weatherList(long time) {
		if (betweenDate > 3) {
			String sevenday_time = "白天";
			if (time > 12)
				sevenday_time = "晚上";
			mysql.SelectsevendayTable(postal_code, day, sevenday_time);
			weather.add(mysql.sevenday);
			System.out.print("sevenday  "+mysql.sevenday);
		} else {
			String three_time="";
			if(time<10)
				three_time="0"+time+":00";
			else
				three_time=time+":00";
			mysql.SelectthreehrTable(postal_code, day, three_time);
			weather.add(mysql.threehr);
			//System.out.print("threehr  "+mysql.threehr);
		}
		//System.out.print(weather.get(weather.size()).toString());
	}

	public void set_time(String time, int playtime) throws Exception {

		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

		Date d = df.parse(time);
		for (int i = 0; i < playtime; i++) {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.HOUR, i);
			long hour = cal.getTimeInMillis() / (3600 * 1000);
			if (hour / 3 == 0) {
				weatherList(hour);
			}

		}
	}

	public ArrayList get_weather() {
		return weather;
	}
}
