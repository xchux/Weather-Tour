package Editschedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Editschedule_Mysql {
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
	ArrayList<Integer> distance = new ArrayList();
	String[] url = new String[5];
	ArrayList<String> place;
	String[] threehr;

	public Editschedule_Mysql() {
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
	public void SelectdistanceTable(String transport, String placeAID, String placeBID) {
		selectplaceSQL = "select * from  distance_" + transport + " where placeAID='" + placeAID + "' placeBID='"
				+ placeBID + "'";
		try {

			stat = con.createStatement();
			rs = stat.executeQuery(selectplaceSQL);
			int i = 0;

			while (rs.next()) {

				distance.add(rs.getInt("placeAID"));
				distance.add(rs.getInt("placeBID"));
				distance.add(rs.getInt("distance"));
				distance.add(rs.getInt("time"));

			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	public void SelectplaceTable(String id) {

		selectplaceSQL = "select * from main_place where id='" + id + "'";
		try {
			place = new ArrayList();
			stat = con.createStatement();
			rs = stat.executeQuery(selectplaceSQL);
			while (rs.next()) {

				place.add(rs.getString("ID"));
				place.add(rs.getString("name"));
				place.add(rs.getString("lat"));
				place.add(rs.getString("lng"));
				place.add(rs.getString("postal_code"));

			

			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}


	public void SelectthreehrTable(String postal_code, String day, String time) {
		selectthreehrSQL = "select * from three_hr where postal_code='" + postal_code + "' AND day='" + day
				+ "' AND time='" + time + "'";

		try {
			threehr = new String[3];
			stat = con.createStatement();
			rs = stat.executeQuery(selectthreehrSQL);
			while (rs.next()) {
				threehr[0] = rs.getString("status");
				threehr[1] = rs.getString("rainfall_probability");
				threehr[2] = rs.getString("ultraviolet");
			}
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}


	// 完整使用完資料庫後,記得要關閉所有Object
	// 否則在等待Timeout時,可能會有Connection poor的狀況
	private void Close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stat != null) {
				stat.close();
				stat = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		} catch (SQLException e) {
			System.out.println("Close Exception :" + e.toString());
		}
	}

}