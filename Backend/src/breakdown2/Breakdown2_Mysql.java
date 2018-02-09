package breakdown2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Breakdown2_Mysql {
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

	String img="" ;
	String[] sub_place = new String[7];
	String[] place = new String[3];
	
	public Breakdown2_Mysql() {
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


	public void Selectsub_place_imgTable(int ID) {
		selectshareSQL = "select * from sub_viewpoint_img where place_id='"+ID+"'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectshareSQL);
			while (rs.next()) {
				img= rs.getString("url");
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		}
	}
	public void Selectsub_sport_detailTable(int ID) {
		selectshareSQL = "select * from sub_viewpoint where id='"+ID+"'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectshareSQL);
			while (rs.next()) {
				sub_place[0]= rs.getString("main_id");
				sub_place[1]= rs.getString("ID");
				sub_place[2]= rs.getString("name");
				sub_place[3]= rs.getString("lat");
				sub_place[4]= rs.getString("lng");
				sub_place[5]= rs.getString("weekday_text");
				sub_place[6]= rs.getString("phone");
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} 
	}
	public void Selectplace_infoTable(int ID) {
		selectshareSQL = "select * from main_place where id='" + ID + "'";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectshareSQL);
			while (rs.next()) {
				place[0]=rs.getString("name");
				place[1]=rs.getString("lat");
				place[2]=rs.getString("lng");
			}

		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} 
	}

	

}
