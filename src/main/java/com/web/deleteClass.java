package com.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.model.Class;

import com.util.HibernateSessionUtil;

//import sun.print.resources.serviceui_es;

/**
 * Servlet implementation class InitSession
 */
@WebServlet("/delete-class")
public class deleteClass extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public deleteClass() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if(session != null) {
		
			
				out.println("<h3 style='color:green'> Welcome to admin access page  </h3>");
		
				request.getRequestDispatcher("delete-class.html").include(request, response);
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
		
		String classId = request.getParameter("id");
		// build hibernate session factory
		try {
			// 1. load session factory
			SessionFactory factory = HibernateSessionUtil.buildSessionFactory();

			// 2. create a session
			Session session = factory.openSession();
			
			// 3. create transaction
			 Transaction tx = session.beginTransaction();
			 
			 //4. create class object
			Class class1=new Class();
			 class1.setId(Integer.parseInt(classId));
			 
			 //5. delete product
			 session.delete(class1);
			 
			 //6. commit transaction.
			 tx.commit();

			if (session != null) {
				out.print("<h3 style='color:green'> Class is deleted sucessfully ! </h3>");
			}

			// close session
			session.close();
			
		}
		
		catch (Exception e) {
			out.print("<h3 style='color:red'> Hibernate session is failed ! </h3>");
		}
	}

}