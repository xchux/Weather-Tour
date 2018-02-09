package breakdown2;

import java.util.ArrayList;

import breakdown2.Breakdown2_Mysql;

public class Breakdown2 {
	Breakdown2_Mysql breakdown2_Mysql = new Breakdown2_Mysql();
	
	String weather_data = "";
	 String[] week_en = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	 String[] week_ch = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };


	public Breakdown2(int place_id) {

		breakdown2_Mysql.Selectsub_sport_detailTable(place_id);

		breakdown2_Mysql.Selectsub_place_imgTable(Integer.valueOf(breakdown2_Mysql.sub_place[1]));
		breakdown2_Mysql.Selectplace_infoTable(Integer.valueOf(breakdown2_Mysql.sub_place[1]));
		
	}

	public String get_place() {// name,lat,lng,
		String data = "";

		for (int i = 0; i < 3; i++) {
			if(i!=0)
				data+=",";
			data += breakdown2_Mysql.place[i]  ;
		}
		return data;
	}

	public String get_sub_place() {//name,lat,lng,weekday_text,phone
		String data = "";

		for (int i = 1; i < 7; i++) {
			if(i!=1)
				data+=",";
			data += breakdown2_Mysql.sub_place[i] ;
		}
		System.out.println(data);
		return data;
	}
	public String get_img() {// name,lat,lng,
		
		return breakdown2_Mysql.img;
	}


}
