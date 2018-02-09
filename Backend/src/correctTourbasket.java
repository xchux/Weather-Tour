
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import correctTourbasket.CorrectTour;

/**
 * Servlet implementation class breakdownAjaxServlet
 */
@WebServlet("/correctTourbasket")
public class correctTourbasket extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		// 對Post中文參數進行解碼
		request.setCharacterEncoding("UTF-8");
		// 取得Ajax傳入的參數
	
		String schedule = request.getParameter("schedule");
		String data = request.getParameter("data");
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		System.out.println(schedule);
		System.out.println(data);
		System.out.println(lat);
		System.out.println(lon);
		
		CorrectTour correct = new CorrectTour(schedule, data, lat, lon);
		correct.refresh();

		PrintWriter out = response.getWriter();
		System.out.println("----end----");
		out.println(correct.JSONArray);
		System.out.println(correct.JSONArray);

	}

	@Override

	protected void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException {

		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override

	protected void doPost(HttpServletRequest request, HttpServletResponse response)

			throws ServletException {

		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override

	public String getServletInfo() {

		return "Short description";

	}// </editor-fold>
}
