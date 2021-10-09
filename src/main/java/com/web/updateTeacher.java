package com.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import com.model.Student;
import com.model.Teacher;
import com.util.HibernateSessionUtil;

//import sun.print.resources.serviceui_es;

/**
 * Servlet implementation class InitSession
 */
@WebServlet("/update-teacher")
public class updateTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public updateTeacher() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if(session != null) {
		
			
				out.println("<h3 style='color:green'> Welcome to admin access page  </h3>");
		
				request.getRequestDispatcher("update-teacher.html").include(request, response);
			} 
			
		else {
			out.println("<h3 style='color:red'>Invalid access, please login to access controls! </h3>");
			request.getRequestDispatcher("login.html").include(request, response);
		}

		response.setContentType("text/html");
		
		request.getRequestDispatcher("index.html").include(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("index.html").include(request, response);
		
		int teacherId = Integer.parseInt(request.getParameter("id"));
		String teacherName = request.getParameter("name");
		String teacherCode = request.getParameter("code");
		
		// build hibernate session factory
		try {
			// 1. load session factory
			SessionFactory factory = HibernateSessionUtil.buildSessionFactory();

			// 2. create a session
			Session session = factory.openSession();
			
			// 3. create transaction
			 Transaction tx = session.beginTransaction();
			 
			 //4. create student object
			 Teacher teacher = new Teacher(teacherId,teacherName,teacherCode);
			 
			 //5. update student
			 session.update(teacher);
			 
			 //6. commit transaction.
			 tx.commit();

			if (session != null) {
				out.print("<h3 style='color:green'> Teacher is updated sucessfully ! </h3>");
			}

			// close session
			session.close();
		} catch (Exception e) {
			out.print("<h3 style='color:red'> Hibernate session is failed ! </h3>");
		}
	}

}