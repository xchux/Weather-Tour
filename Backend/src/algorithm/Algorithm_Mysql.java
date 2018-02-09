package algorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Algorithm_Mysql {
	private Connection con = null; // Database objects
	// 連接object
	private Statement stat = null;
	// 執行,傳入之sql為完整字串
	private ResultSet rs = null;
	// 結果集
	private PreparedStatement pst = null;
	// 執行,傳入之sql為預儲之字申,需要傳入變數之位置
	// 先利用?來做標示
	private String selectplaceSQL = "";
	// private String selectsevendaySQL = "";
	private String selectthreehrSQL = "";
	private String selectimgSQL = "";
	ArrayList<ArrayList<Integer>> distance;

	String[] url = new String[5];
	ArrayList<ArrayList<String>> place ;
	ArrayList<ArrayList<String>> activity ;
	ArrayList<ArrayList<String>> bridge;
	String[] sevenday = new String[3];
	String[] threehr = new String[3];

	public Algorithm_Mysql() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 註冊driver
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/weather_tour?useUnicode=true&characterEncoding=UTF-8", "root", "");
			// 取得connection

			// jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
			// localhost是主機名,test是database名
			// useUnicode=true&characterEncoding=Big5使用的編碼

		} catch (ClassNotFoundException e) {
			System.out.println("DriverClassNotFound :" + e.toString());
		} // 有可能會產生sqlexception
		catch (SQLException x) {
			System.out.println("Exception :" + x.toString());
		}

	}

	// 查詢資料
	// 可以看看回傳結果集及取得資料方式
	public void SelectactivityTable() {
		selectplaceSQL = "select * from  activity";
		try {
			activity = new ArrayList<ArrayList<String>>();
			stat = con.createStatement();
			rs = stat.executeQuery(selectplaceSQL);
		
			while (rs.next()) {
				ArrayList<String> temp = new ArrayList();
				temp.add(rs.getString("id"));
				temp.add(rs.getString("start"));
				temp.add(rs.getString("end"));
				activity.add(temp);
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} 
	}

	public void SelectbridgeTable() {
		selectplaceSQL = "select * from  main_sub_bridge";
		try {
			bridge = new ArrayList<ArrayList<String>>();
			stat = con.createStatement();
			rs = stat.executeQuery(selectplaceSQL);
			
			while (rs.next()) {
				ArrayList<String> temp = new ArrayList();
				temp.add(rs.getString("id"));
				temp.add(rs.getString("activity_bridge"));
				bridge.add(temp);
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} 
	}

	public void SelectdistanceTable(String transport) {
		selectplaceSQL = "select * from  " + transport;
		try {
			distance= new ArrayList<ArrayList<Integer>>();
			stat = con.createStatement();
			rs = stat.executeQuery(selectplaceSQL);
			int i = 0;

			while (rs.next()) {
				ArrayList<Integer> temp = new ArrayList();
				temp.add(rs.getInt("placeAID"));
				temp.add(rs.getInt("placeBID"));
				temp.add(rs.getInt("distance"));
				temp.add(rs.getInt("time"));
				distance.add(temp);
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} 
	}

	public void SelectplaceTable() {
		selectplaceSQL = "select * from main_place";
		try {
			 place = new ArrayList<ArrayList<String>>();
			stat = con.createStatement();
			rs = stat.executeQuery(selectplaceSQL);
			while (rs.next()) {
				ArrayList<String> temp = new ArrayList();
				temp.add(rs.getString("ID"));
				temp.add(rs.getString("name"));
				temp.add(rs.getString("lat"));
				temp.add(rs.getString("lng"));
				temp.add(rs.getString("postal_code"));
				temp.add(rs.getString("place_id"));
				temp.add(rs.getString("In_out_door"));
				temp.add(rs.getString("tag"));
				place.add(temp);
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} 
	}

//	public void SelectsevendayTable(String postal_code, String day, String time) {
//		selectsevendaySQL = "select * from seven_days where postal_code='" + postal_code + "' AND day='" + day
//				+ "' AND time='" + time + "'";
//		try {
//			stat = con.createStatement();
//			rs = stat.executeQuery(selectsevendaySQL);
//			while (rs.next()) {
//				sevenday[0] = rs.getString("status");
//				sevenday[1] = rs.getString("rainfall_probability");
//
//			}
//		} catch (SQLException e) {
//			System.out.println("DropDB Exception :" + e.toString());
//		} finally {
//			Close();
//		}
//	}

	public void SelectthreehrTable(String postal_code, String day, String time) {
		selectthreehrSQL = "select * from three_hr where postal_code='" + postal_code + "' AND day='" + day
				+ "' AND time='" + time + "'";

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectthreehrSQL);
			while (rs.next()) {
				threehr[0] = rs.getString("status");
				threehr[1] = rs.getString("rainfall_probability");
				threehr[2] = rs.getString("ultraviolet");
			}
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}

	public void SelecturlTable(String place_id) {
		String selecurlSQL = "select * from place_img  where place_id='" + place_id + "'";
		try {
			int i = 0;
			stat = con.createStatement();
			rs = stat.executeQuery(selecurlSQL);

			while (rs.next()) {
				url[i] = rs.getString("url");

				i++;
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}

	

}