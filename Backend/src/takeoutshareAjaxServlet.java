

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import tackoutDB.takeout_Mysql;


/**
 * Servlet implementation class StoreDB
 */
@WebServlet("/takeoutshareAjaxServlet")
public class takeoutshareAjaxServlet extends HttpServlet {
	
	
   	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
   		response.setContentType("text/html;charset=UTF-8");
		String encloseid = request.getParameter("encloseid");	
		takeout_Mysql takeout_mysql = new takeout_Mysql();
		takeout_mysql.SelectshareTable(encloseid);
		
		JSONObject responseJSONObject = null;		
		HashMap userInfoMap = new HashMap();
		userInfoMap.put("encloseid",takeout_mysql.encloseid);
		userInfoMap.put("enclosename",takeout_mysql.enclosename);
		userInfoMap.put("startPosition",takeout_mysql.startPosition);
		userInfoMap.put("transoprt",takeout_mysql.transoprt);
		userInfoMap.put("viewpoint",takeout_mysql.viewpoint);
		responseJSONObject = new JSONObject(userInfoMap);	
		PrintWriter out = response.getWriter();
		System.out.println("----end----");
		out.println(responseJSONObject);
		System.out.println(responseJSONObject);
	}

	@Override

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
