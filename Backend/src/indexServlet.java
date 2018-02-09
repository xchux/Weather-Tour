import java.io.IOException;

import java.io.PrintWriter;

import java.util.*;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.json.*;

import algorithm.Algorithm;

@WebServlet(urlPatterns = { "/indexServlet" })

public class indexServlet extends HttpServlet {
	String[] in_out_judge = { "all_in", "most_in", "most_out", "all_out" };

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	

		
		response.setContentType("text/html;charset=UTF-8");

		// 對Post中文參數進行解碼

		request.setCharacterEncoding("UTF-8");

		// 取得Ajax傳入的參數
		// String datepicker = "2017-08-02";
		// String time = "06:30:00";
		// String points = "40";
		// String lat = "25.011700";
		// String lon = "121.461821";
		// String place_id = "1";

		String datepicker = request.getParameter("datepicker");
		String time = request.getParameter("time") + ":00";
		String points = request.getParameter("points");
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		String place_id = request.getParameter("place_id");
		String translate = request.getParameter("translate");
		
		 System.out.println("datepicker" + datepicker);
		 System.out.println("time" + time);
		 System.out.println("translate" + translate);
		 System.out.println("points" + points);
		 System.out.println("latitude" + lat);
		 System.out.println("longitude" + lon);
		 System.out.println("place_id" + place_id);
		
	
		
		String[] date=datepicker.split("-");
		Algorithm alogram = new Algorithm(translate,
				"1,2,2,0,4,7,0,0,9,0,7,0,0,6,5" + ",1,2,0,6,5,4,7,0,0,2,0,0,9,0,7" + ",0,0,6,5,1,2,5,0,4,7,8,0,9,0,7");
		try {
			if (Integer.valueOf( place_id)>0)
				alogram.mathematic(date[1]+"/"+date[2], time,Integer.valueOf( points),Integer.valueOf( place_id)-1);
			else
				alogram.quadrant(Double.valueOf(lat), Double.valueOf(lon), date[1]+"/"+date[2], time, Integer.valueOf(points));
			alogram.setactivity();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		
		String[] arrayUserInterest = request.getParameterValues("userInterest");

		// 建構要回傳JSON物件
		JSONArray responseJSONArray = new JSONArray();
		JSONObject responseJSONObject = null;

		for (int i = 0; i < alogram.order.size(); i++) {
			HashMap userInfoMap = new HashMap();
			// ID/0,name/1,lat/2,lng/3,postal_code/4,place_id/5,In_out_door/6,tag/7
			ArrayList<String> place_data = alogram.order.get(i).place;
			int in_out = 0;

			for (; in_out < 4; in_out++) {
				if (place_data.get(6).equals(in_out_judge[in_out]))
					break;
			}
			userInfoMap.put("id", place_data.get(0));
			userInfoMap.put("name", place_data.get(1));
			userInfoMap.put("tag", place_data.get(7));
			userInfoMap.put("in_out", in_out);
			userInfoMap.put("lat_lng", place_data.get(2) + "," + place_data.get(3));
			userInfoMap.put("postal_code", place_data.get(4));
			userInfoMap.put("weather", alogram.order.get(i).status);
			userInfoMap.put("uv", alogram.order.get(i).uv);
			userInfoMap.put("arrival_time", String.valueOf(alogram.order.get(i).arrival_time));
			userInfoMap.put("distance", Math.round(alogram.order.get(i).distance/100.0)/10.0);
			userInfoMap.put("rainfall_probability", alogram.order.get(i).rainfall_probability);
			userInfoMap.put("url", alogram.order.get(i).url);
			userInfoMap.put("activity", alogram.order.get(i).activity);
			responseJSONObject = new JSONObject(userInfoMap);
			responseJSONArray.put(responseJSONObject);
			//System.out.println(alogram.order.get(i).getPoint());
		}

		/*
		 * Map paramMap = new HashMap(); paramMap.put("data",responseJSONArray);
		 * JSONObject data=new JSONObject(paramMap);
		 * System.out.println(data.toString());
		 */
		PrintWriter out = response.getWriter();
		System.out.println("----end----");
		out.println(responseJSONArray);
		//System.out.println(responseJSONArray);

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