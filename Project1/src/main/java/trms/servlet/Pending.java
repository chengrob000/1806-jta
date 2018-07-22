package trms.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import trms.beans.User;
import trms.dao.ApplicationFormDAO;
import trms.dao.ApplicationFormDAOImpl;
import trms.dao.UserDAO;
import trms.dao.UserDAOImpl;

/**
 * Servlet implementation class Pending
 */
public class Pending extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pending() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*UserDAO userDAO = new UserDAOImpl();
		ApplicationFormDAO applicationFormDAO = new ApplicationFormDAOImpl();
		List<User> users = userDAO.getAllUsers();
		List<trms.beans.ApplicationForm> applicationForms = applicationFormDAO.getAllForms();
		request.setAttribute("users", users);
		RequestDispatcher dispatcher = request.getRequestDispatcher("pending.jsp");
		dispatcher.forward(request, response);*/
		request.removeAttribute("users");
		request.removeAttribute("forms");
		listContent(request, response);
	}
	
	private void listContent (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDAO = new UserDAOImpl();
		List<User> users = userDAO.getAllUsers();/*
		String json = new Gson().toJson(users);
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");*/
		request.setAttribute("users", users);
		RequestDispatcher dispatcher = request.getRequestDispatcher("pending.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDAO = new UserDAOImpl();
		ApplicationFormDAO applicationFormDAO = new ApplicationFormDAOImpl();
		List<User> users = userDAO.getAllUsers();
		List<trms.beans.ApplicationForm> applicationForms = applicationFormDAO.getAllForms();
		String userUUID = request.getParameter("user");
		applicationForms = applicationFormDAO.getUserApplicationForms(userUUID);
		
		request.setAttribute("forms", applicationForms);
		listContent(request, response);
	}

}
