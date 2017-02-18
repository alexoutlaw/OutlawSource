package com.outlawsource.filters;

import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.outlawsource.dao.UserDAO;
import com.outlawsource.domain.User;

public class UserFilter implements Filter {
	public void  init(FilterConfig config) throws ServletException{
		
	}
	
	public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {
		String userId = ((HttpServletRequest) request).getRemoteUser();
		
		if(userId == null) {
			throw new RuntimeException("Not Authenticated");
		}
		else {
			try {
				User user = UserDAO.getUser(userId);
				if(user == null) {
					throw new RuntimeException("Unknown User");
				}
				
				HttpSession session = ((HttpServletRequest) request).getSession();
				User sessionUser = (User) session.getAttribute("user");
				if(sessionUser == null) {
					session.setAttribute("user", user);	
				}						
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		chain.doFilter(request,response);
	}
		
	public void destroy( ){
		/* Called before the Filter instance is removed 
		from service by the web container*/
	}
}