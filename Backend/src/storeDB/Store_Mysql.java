package storeDB;

import java.sql.*;
import java.util.*;

public class Store_Mysql {
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
	private String insertdbSQL = "insert into share(id,encloseid,enclosename,startPosition,transoprt,viewpoint) "
			+ "select ifNULL(max(id),0)+1,?,?,?,?,? FROM share";
	
	public Store_Mysql() {
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
	public void insertTable(String string, String string1, String string2, String string3, String string4) {
		try {
			// System.out.println(insertdbSQL);
			pst = con.prepareStatement(insertdbSQL);
			pst.setString(1, string);			
			pst.setString(2, string1);
			pst.setString(3, string2);			
			pst.setString(4, string3);
			pst.setString(5, string4);						
			pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("InsertDB Exception :" + e.toString());
		} 
	}
	public void SelectshareTable(int ID) {
		selectshareSQL = "select * from share ";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectshareSQL);
			while (rs.next()) {
				
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} 
	}
	
	

}