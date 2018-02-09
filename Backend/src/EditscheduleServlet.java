
import java.io.IOException;

import java.io.PrintWriter;

import java.util.*;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.json.*;

import Editschedule.Editschedule;

@WebServlet(urlPatterns = { "/EditscheduleServlet" })

public class EditscheduleServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		// 對Post中文參數進行解碼

		request.setCharacterEncoding("UTF-8");

		// 取得Ajax傳入的參數
		String latitude=request.getParameter("latitude");
		String longitude= request.getParameter("longitude");
		String starttime =  request.getParameter("starttime");
		String transport =  request.getParameter("transport");
		String data = request.getParameter("schedule");
		
//		System.out.println(latitude);
//		System.out.println(longitude);
//		System.out.println(starttime);
//		System.out.println(transport);
//		System.out.println(data);
		Editschedule editschedule = new Editschedule(latitude,longitude,starttime,transport,data);

		try {
			editschedule.rebuild();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		PrintWriter out = response.getWriter();
		System.out.println("----end----");
		out.println(editschedule.responseJSONArray);
		System.out.println(editschedule.responseJSONArray);

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