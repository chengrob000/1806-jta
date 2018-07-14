package Project1.Servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Project1.Service.*;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
						 HttpServletResponse response) throws ServletException,
															  IOException {
		HttpSession session = null;
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if(EmployeeService.employeeLogin(username, password)){
			session = request.getSession();
			session.setAttribute("username", username);
			RequestDispatcher rd = request.getRequestDispatcher("user/index.html");
			rd.forward(request, response);
		}
		
		else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.write("<head><script>window.history.back();</script></head>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) 
								  throws ServletException, IOException {
		doGet(request, response);
	}
}