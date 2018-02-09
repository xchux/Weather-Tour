package tackoutDB;

import java.sql.*;
import java.util.*;

public class takeout_Mysql {
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
	
	public String encloseid,enclosename,startPosition,transoprt,viewpoint;
	public takeout_Mysql() {
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

	public void SelectshareTable(String encloseids) {
		selectshareSQL = "select * from share where encloseid='"+encloseids+"'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectshareSQL);
			while (rs.next()) {				
				encloseid=rs.getString("encloseid");
				enclosename=rs.getString("enclosename");
				startPosition=rs.getString("startPosition");
				transoprt=rs.getString("transoprt");
				viewpoint=rs.getString("viewpoint");
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} 
	}
	
	

}