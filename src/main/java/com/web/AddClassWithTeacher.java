package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
import com.model.Student;
import com.model.Subject;
import com.model.Teacher;
import com.util.HibernateSessionUtil;

@WebServlet("/add-class-with-teacher")
public class AddClassWithTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddClassWithTeacher() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if(session != null) {
		
			
				out.println("<h3 style='color:green'> Welcome to admin access page  </h3>");
		
				request.getRequestDispatcher("add-class-with-teacher.html").include(request, response);
			} 
			
		else {
			out.println("<h3 style='color:red'>Invalid access, please login to access controls! </h3>");
			request.getRequestDispatcher("login.html").include(request, response);
		}

		response.setContentType("text/html");
		
		request.getRequestDispatcher("index.html").include(request, response);
	

	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("index.html").include(request, response);

		// get class params
		String clas1 = request.getParameter("classpos");
		String sch =request.getParameter("school");
		

		// get teacher details
		String t1  = request.getParameter("teachername1");
		String  c1=request.getParameter("teachercode1");
		
		
		String t2 = request.getParameter("teachername2");
		String c2 =request.getParameter("teachercode2");

		
		
		
		
		// build hibernate session factory
		try {
			// 1. load session factory
			SessionFactory factory = HibernateSessionUtil.buildSessionFactory();

			// 2. create a session
			Session session = factory.openSession();

			// 3. create transaction
			Transaction tx = session.beginTransaction();

			// 4. create order object
			Class clas = new Class(clas1,sch);
			
			Set<Teacher> teacher1= new HashSet<>();
			Teacher teach1 = new Teacher(t1,c1);
			Teacher teach2 = new Teacher(t2,c2);
			
			teacher1.add(teach1);
			teacher1.add(teach2);
		
			// add products list to order
			clas.setTeacher1(teacher1);
			
			// 5. save product
			session.save(clas);

			// 6. commit transaction.
			tx.commit();

			if (session != null) {
				out.print("<h3 style='color:green'> Class with Teacher Details added sucessfully ! </h3>");
			}

			// close session
			session.close();
		} catch (Exception e) {
			out.print("<h3 style='color:red'> Hibernate session is failed ! </h3>"+e);
		}

	}

}