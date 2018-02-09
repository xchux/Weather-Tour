

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storeDB.Store_Mysql;

/**
 * Servlet implementation class StoreDB
 */
@WebServlet("/storeshareAjaxServlet")
public class storeshareAjaxServlet extends HttpServlet {
	Store_Mysql share_mysql = new Store_Mysql();
	
   	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String encloseid =  request.getParameter("encloseid");		
		String enclosename =  request.getParameter("enclosename");
		String startPosition =  request.getParameter("startPosition");
		String transoprt =  request.getParameter("transoprt");
		String viewpoint =request.getParameter("viewpoint");// id,attractions,weather,transportation,departTime,arrivalTime,spotPicture,playtime,points,place_id
		System.out.println(encloseid+" "+enclosename+" "+ startPosition+" "+transoprt+" "+viewpoint);
		
		share_mysql.insertTable(encloseid,enclosename,startPosition,transoprt, viewpoint);
		PrintWriter out = response.getWriter();
		System.out.println("----end----");
		out.println("{\"success\":\"success\"}");
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
