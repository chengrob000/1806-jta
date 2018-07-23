package com.revature.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Employee;
import com.revature.services.EmployeeService;
import com.revature.services.RFormService;

/**
 * Servlet implementation class DenyRFormServlet
 */
public class DenyRFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DenyRFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text");
		int currFormId = Integer.parseInt(request.getParameter("currFormId"));
		int currempid = Integer.parseInt(request.getParameter("currempid"));
		int currfinalperc = Integer.parseInt(request.getParameter("currfinalperc"));
		Employee emp = EmployeeService.getEmpById(currempid);
		emp.setPending(emp.getPending() - 0.01*currfinalperc);
		emp.setAvailable(emp.getAvailable() + 0.01*currfinalperc);
		EmployeeService.updatePendingReim(emp.getPending(),currempid);
		EmployeeService.updateAvailableReim(emp.getAvailable(),currempid);
		RFormService.approveRForm(9, currFormId);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}