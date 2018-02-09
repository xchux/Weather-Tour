import java.io.IOException;

import java.io.PrintWriter;

import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.json.*;

import schedule.Schedule;

@WebServlet(urlPatterns = { "/shareAjaxServlet.do" })

public class scheduleAjaxServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject responseJSONObject = null;

		String datepicker ="2017-04-22" ;//request.getParameter("datepicker");
		String time ="08:00:00" ;//request.getParameter("time")+":00";//HH:MM
		String playtime = "2";//request.getParameter("playtime");
		String place_id = "1";//request.getParameter("place_id");
		
		Schedule schedule = null;
		try {
			schedule = new Schedule(datepicker,time,place_id);
			schedule.set_time(time,Integer.valueOf(playtime));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap weatherMap = new HashMap();
		
		weatherMap.put("weather", schedule.get_weather());
		responseJSONObject = new JSONObject(weatherMap);
		
		PrintWriter out = response.getWriter();
		System.out.println("----end----");
		out.println(responseJSONObject);
		
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