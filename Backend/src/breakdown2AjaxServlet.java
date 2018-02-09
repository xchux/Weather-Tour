
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

import breakdown2.Breakdown2;

/**
 * Servlet implementation class breakdownAjaxServlet
 */
@WebServlet("/breakdown2AjaxServlet")
public class breakdown2AjaxServlet extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		// 對Post中文參數進行解碼
		request.setCharacterEncoding("UTF-8");
		// 取得Ajax傳入的參數

		String sub_place_id = request.getParameter("sub_place_id");

		Breakdown2 breakdown2 = new Breakdown2(Integer.valueOf(sub_place_id));
		

		// 建構要回傳JSON物件
		JSONArray responseJSONArray = new JSONArray();
		JSONObject responseJSONObject = null;

		HashMap userInfoMap = new HashMap();

		userInfoMap.put("place", breakdown2.get_place());// name,lat,lng,
		userInfoMap.put("sub_place", breakdown2.get_sub_place());//name,lat,lng,weekday_text,phone
		userInfoMap.put("img", breakdown2.get_img());

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
