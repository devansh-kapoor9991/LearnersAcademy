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


import com.model.Subject;
import com.model.Teacher;
import com.util.HibernateSessionUtil;


@WebServlet("/add-teacher-with-subject")
public class AddTeacherWithSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddTeacherWithSubject() {
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if(session != null) {
		
			
				out.println("<h3 style='color:green'> Welcome to admin access page  </h3>");
		
				request.getRequestDispatcher("add-teacher-with-subject.html").include(request, response);
			} 
			
		else {
			out.println("<h3 style='color:red'>Invalid access, please login to access controls! </h3>");
			request.getRequestDispatcher("login.html").include(request, response);
		}

		response.setContentType("text/html");
		
		request.getRequestDispatcher("index.html").include(request, response);
	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("index.html").include(request, response);
		
		String teacherName = request.getParameter("teachername");
		String teacherCode = request.getParameter("ecode");
	
		String  name = request.getParameter("name1");
		String code = request.getParameter("code1");
		
		
		// build hibernate session factory
		try {
			// 1. load session factory
			SessionFactory factory = HibernateSessionUtil.buildSessionFactory();

			// 2. create a session
			Session session = factory.openSession();
			
			// 3. create transaction
			 Transaction tx = session.beginTransaction();
			 
			 //4. create teacher object & subject details
			 Teacher teacher = new Teacher(teacherName,teacherCode);
			 Subject subject = new Subject(name,code);
			 teacher.setSubject(subject);
			 
			 //5. save teacher
			 session.save(teacher);
			 
			 //6. commit transaction.
			 tx.commit();

			if (session != null) {
				out.print("<h3 style='color:green'> Teacher with subject added successfully ! </h3>");
			}

			// close session
			session.close();
		} catch (Exception e) {
			out.print("<h3 style='color:red'> Hibernate session is failed ! </h3>");
		}
		
	}

}
