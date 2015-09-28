package com.jb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;


public class SessionFilter implements Filter{
	
	private FilterConfig config;
	private HttpServletRequest request;
	private HttpServletResponse response;
	String exceptionUrls;
	String[] urls;
	String loginUrl;
	
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.config = config;
		exceptionUrls = config.getInitParameter("exceptionUrl");
		loginUrl = config.getInitParameter("loginUrl");
		urls = exceptionUrls.split(",");
	}
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		this.request = (HttpServletRequest)req;
		this.response = (HttpServletResponse)res;

		String currUrl = request.getServletPath();
		if(StringUtils.isBlank(currUrl))
			currUrl = request.getPathInfo();
//		System.out.println(currUrl);
		for(String url:urls){
			if (currUrl.indexOf(url) > 0){
				chain.doFilter(req, res);
				return;
			}
		}
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("username") != null){
			chain.doFilter(req, res);
			return;
		}
		response.sendRedirect(loginUrl);
		
		
	}
	public void destroy() {
		// TODO Auto-generated method stub
		this.config = null;
	}
	
}
