package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Employee;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeTypeService;
import com.revature.services.EventTypeService;
import com.revature.services.RFormService;

/**
 * Servlet implementation class NewFormServlet
 */
public class NewFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = null;
		response.setContentType("text/html");
		session = request.getSession();
		Employee employee = (Employee)session.getAttribute("employee");
		String d = request.getParameter("date");
		Date date = java.sql.Date.valueOf(d);
		String place = request.getParameter("place");
		String info = request.getParameter("info");
		int propreim = Integer.parseInt(request.getParameter("propreim"));
		String justification = request.getParameter("justification");
		int timemissed = Integer.parseInt(request.getParameter("timemissed"));
		int gradeformat =  Integer.parseInt(request.getParameter("gradeformat"));
		int cutoff;
		if(request.getParameter("cutoff") == "") {
			cutoff = 0;
		}else{
			cutoff = Integer.parseInt(request.getParameter("cutoff"));
		}
		int eventtype =  Integer.parseInt(request.getParameter("eventtype"));
		double eventcost =  Double.parseDouble(request.getParameter("eventcost"));
		int empid = employee.getEmpid();
		int supid = employee.getDirSupId();
		String eventtypename = EventTypeService.eventtypes.getEventTypeNameMap().get(eventtype);
		String eventname = request.getParameter("eventname");
		int finalperc = EventTypeService.eventtypes.getEventTypeMap().get(eventtypename).get(1);
		
		if(RFormService.registerRForm(empid, date, place, info, propreim, justification,
										timemissed, gradeformat, cutoff, eventtype,
										eventcost,supid,eventname,employee.getDepId(),finalperc)){
			employee.setPending(employee.getPending()
					+ (0.01*eventcost)*EventTypeService.eventtypes.getEventTypeMap().get(eventtypename).get(1));
			employee.setAvailable(employee.getAvailable()
					- employee.getPending()
					- employee.getAwarded());
			session.setAttribute("employee", employee);
			EmployeeService.updatePendingReim(employee.getPending(),empid);
			EmployeeService.updateAvailableReim(employee.getAvailable(), empid);
			
			RequestDispatcher rd = request.getRequestDispatcher("user/emphome.html");
			rd.forward(request, response);
		}else{
			response.sendError(418);
		}
	}

}