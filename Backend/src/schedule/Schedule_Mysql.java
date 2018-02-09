package schedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Schedule_Mysql {
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
	private String selectsevendaySQL = "";
	private String selectthreehrSQL = "";
	
	String[] place = new String[3];
	String sevenday = new String();
	String threehr = new String();

	public Schedule_Mysql() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 註冊driver
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/weather_tour?useUnicode=true&characterEncoding=Big5", "root", "");
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

	

	public void SelectplaceTable(int ID) {

		selectplaceSQL = "select * from place_info where ID='" + ID + "'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectplaceSQL);
			while (rs.next()) {
				place[0] = rs.getString("ID");
				place[1] = rs.getString("name");				
				place[2] = rs.getString("postal_code");
			
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}

	public void SelectsevendayTable(String postal_code, String day, String time) {
		selectsevendaySQL = "select * from seven_days where postal_code='" + postal_code + "' AND day='" + day
				+ "' AND time='" + time + "'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectsevendaySQL);
			while (rs.next()) {
				sevenday = rs.getString("status");

			}
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}

	public void SelectthreehrTable(String postal_code, String day, String time) {
		selectthreehrSQL = "select * from three_hr where postal_code='" + postal_code + "' AND day='" + day
				+ "' AND time='" + time + "'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectthreehrSQL);

			while (rs.next()) {
				threehr = rs.getString("status");
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}

	

	

}