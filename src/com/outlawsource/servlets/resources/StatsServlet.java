package com.outlawsource.servlets.resources;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.outlawsource.dao.FactoryDAO;
import com.outlawsource.domain.User;
import com.outlawsource.servlets.BaseServlet;

@WebServlet("/Resources/Stats")
public class StatsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			super.doGet(request, response);
			request.getRequestDispatcher("/Stats.jsp").forward(request, response);
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			super.doPost(request, response);
			
			User user = getUser(request);
			
			String item = request.getParameter("item");
			try {
				Integer level = Integer.parseInt(request.getParameter("level"));
				
				FactoryDAO.updateFactoryLevel(user.getUserId(), item, level);			
				messages.add("Factory " + item + " successfully upgraded to Level " + level);
			}
			catch(NumberFormatException ex) {
				messages.add("Submitted level is unrecognized, expected format is numbers only");
			}
			
			request.setAttribute("messages", messages);
			request.setAttribute("factories", FactoryDAO.getUserFactories(user.getUserId()));
			request.getRequestDispatcher("/Stats.jsp").forward(request, response);
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
    }
}