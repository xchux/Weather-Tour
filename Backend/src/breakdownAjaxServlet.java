
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

import breakdown.Breakdown;

/**
 * Servlet implementation class breakdownAjaxServlet
 */
@WebServlet("/breakdownAjaxServlet")
public class breakdownAjaxServlet extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		// 對Post中文參數進行解碼
		request.setCharacterEncoding("UTF-8");
		// 取得Ajax傳入的參數

		String date = request.getParameter("date");// yyyy-mm-dd
		String arrivalTime = request.getParameter("arrivalTime") + ":00";// hh:mm
		String place_id = request.getParameter("place_id");
		System.out.println(date);
		System.out.println(arrivalTime);
		System.out.println(place_id);
		Breakdown breakdown = new Breakdown(Integer.valueOf(place_id));
		try {
			breakdown.setweather(arrivalTime, date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 建構要回傳JSON物件
		JSONArray responseJSONArray = new JSONArray();
		JSONObject responseJSONObject = null;

		HashMap userInfoMap = new HashMap();

		userInfoMap.put("place", breakdown.get_place());// ID,name,lat,lng,
		userInfoMap.put("weather", breakdown.get_weather());// location,day,time,status,temperature,rainfall_probability
		userInfoMap.put("img", breakdown.get_img());// url,url,url,
		userInfoMap.put("play", breakdown.get_play());// ArrayList
		userInfoMap.put("food", breakdown.get_food());// ArrayList
		userInfoMap.put("activity", breakdown.get_activity());// ArrayList
		responseJSONObject = new JSONObject(userInfoMap);

		PrintWriter out = response.getWriter();
		System.out.println("----end----");
		out.println(responseJSONObject);
		System.out.println(responseJSONObject);

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
