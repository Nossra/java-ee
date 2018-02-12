package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Exercise;
import entities.Plan;
import entities.User;
import utilities.EntityUtil;
import utilities.HibernateUtil;
import viewmodel.LoginViewModel;

/**
 * Servlet implementation class CreatePlanServlet
 */
@WebServlet("/createplan")
public class CreatePlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LoginViewModel user;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		HttpSession session = request.getSession(false);
		
		
		if (session != null) {
			user = (LoginViewModel) session.getAttribute("user"); 
		} else {
			response.sendRedirect("login");
		}
		request.getRequestDispatcher("createplan.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String planName = request.getParameter("planName");
		int amountOfDays = Integer.parseInt(request.getParameter("amountOfDays"));
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		final String HQL_USER = "FROM User u WHERE u.id = :id";
		@SuppressWarnings("unchecked")
		User thisUser = (User) s.createQuery(HQL_USER)
				.setParameter("id", user.getId())
				.getSingleResult();
		
		Plan p = new Plan(planName, amountOfDays, thisUser);
		
//		List<Exercise> exercises = Exercise.selectAll();
		
		EntityUtil.save(p);
		response.sendRedirect("viewplans");
	}

}
