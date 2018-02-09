package correctTourbasket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class correct_Mysql {
	private Connection con = null; // Database objects
	// 連接object
	private Statement stat = null;
	// 執行,傳入之sql為完整字串
	private ResultSet rs = null;
	// 結果集
	private PreparedStatement pst = null;
	ArrayList<Integer> distance;
	ArrayList<String> threehr;
	ArrayList<ArrayList<String>> place = new ArrayList<ArrayList<String>>();

	public correct_Mysql() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 註冊driver
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/weather_tour?useUnicode=true&characterEncoding=Big5", "root", "");

		} catch (ClassNotFoundException e) {
			System.out.println("DriverClassNotFound :" + e.toString());
		} // 有可能會產生sqlexception
		catch (SQLException x) {
			System.out.println("Exception :" + x.toString());
		}
	}

	public void SelectdistanceTable(String transport, int Aid, int Bid) {
		distance = new ArrayList();
		String selectdisSQL = "select * from  " + transport + " where placeAID='" + Aid + "' and placeBID= '" + Bid
				+ "'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectdisSQL);
			while (rs.next()) {
				distance.add(rs.getInt("distance"));
				distance.add(rs.getInt("time"));
			}
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}

	public void SelectplaceTable() {

		String selectplaceSQL = "select * from main_place";
		try {

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

	public void SelectthreehrTable(String postal_code, String day, String time) {
		threehr = new ArrayList();
		String selectthreehrSQL = "select * from three_hr where postal_code='" + postal_code + "' AND day='" + day
				+ "' AND time='" + time + "'";

		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectthreehrSQL);
			while (rs.next()) {
				threehr.add(rs.getString("status"));
				threehr.add(rs.getString("rainfall_probability"));
				threehr.add(rs.getString("ultraviolet"));
			}
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}

}
