package com.revature.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.service.EmployeeService;
import com.revature.service.EventService;
import com.revature.service.FixedDataService;
import com.revature.utils.StringManip;

/**
 * Servlet implementation class CalcReimbServlet
 */
public class CalcReimbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalcReimbServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();

		Integer type = Integer.parseInt(request.getParameter("eventType"));
		double amount = Double.parseDouble(request.getParameter("amount"));
		Integer userId = (Integer) session.getAttribute("userId");
		
		double percent = FixedDataService.getEventType(type).getPercent()/100.0;
		double available = EmployeeService.getEmployeeReimbursemntAvailable(userId);
		
		double newAmt = Math.min(available, amount*percent);
		
		out.write(StringManip.formatCurrency(newAmt));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
