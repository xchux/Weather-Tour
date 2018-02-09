package breakdown;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Breakdown_Mysql {
	private Connection con = null; // Database objects
	// 連接object
	private Statement stat = null;
	// 執行,傳入之sql為完整字串
	private ResultSet rs = null;
	// 結果集
	private PreparedStatement pst = null;
	// 執行,傳入之sql為預儲之字申,需要傳入變數之位置
	// 先利用?來做標示
	private String selectshareSQL = "";
	String play="",food="",activity="";
	ArrayList<ArrayList<String>> threehr = new ArrayList<ArrayList<String>>();
	String[] img = new String[5];
	String[] place = new String[6];
	String[] activity_data = new String[6];


	public Breakdown_Mysql() {
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

	// 新增資料
	// 可以看看PrepareStatement的使用方式

	public void Selectplace_infoTable(int ID) {
		selectshareSQL = "select * from main_place where ID='" + ID + "'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectshareSQL);
			while (rs.next()) {
				place[0] = rs.getString("ID");
				place[1] = rs.getString("name");
				place[2] = rs.getString("lat");
				place[3] = rs.getString("lng");
				place[4] = rs.getString("postal_code");
				place[5] = rs.getString("place_id");
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}

	public void Selectplace_imgTable(String place_id) {
		selectshareSQL = "select * from place_img where place_id='" + place_id + "'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectshareSQL);
			int i = 0;
			while (rs.next()) {
				img[i++] = rs.getString("url");
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} 
	}

	public void Selectmain_sub_bridgeTable(int main_id) {
		selectshareSQL = "select * from main_sub_bridge where main_id='" + main_id + "'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectshareSQL);
			while (rs.next()) {
				play=rs.getString("play_bridge"); 
				food=rs.getString("food_bridge");
				activity=rs.getString("activity_bridge");
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}
	public void SelectactivityTable(int id) {
		selectshareSQL = "select * from activity where id='" + id + "'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectshareSQL);
			while (rs.next()) {
				activity_data[0]=rs.getString("name"); 
				activity_data[1]=rs.getString("start");
				activity_data[2]=rs.getString("end");
				activity_data[3]=rs.getString("info");
				activity_data[4]=rs.getString("lat");
				activity_data[5]=rs.getString("lng");
				
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}

	public void SelectthreehrTable(String postal_code) {
		String selectthreehrSQL = "select * from three_hr where postal_code='" + postal_code + "'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectthreehrSQL);
			int i=0;
			while (rs.next()) {
				ArrayList<String> temp=new ArrayList<String>();
				temp.add( rs.getString("location"));
				temp.add( rs.getString("day"));
				temp.add( rs.getString("time"));
				temp.add( rs.getString("status"));
				temp.add( rs.getString("temperature"));
				temp.add( rs.getString("rainfall_probability"));
				threehr.add(temp);
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}


}
