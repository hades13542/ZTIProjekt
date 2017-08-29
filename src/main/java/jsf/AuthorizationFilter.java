package jsf;

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

public class AuthorizationFilter implements Filter {

	@Override
	public void destroy() {
		// empty ? 
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(true);
		
		String requestURI = httpRequest.getRequestURI();
		
//		if(requestURI.contains("/login.xhtml") ||
//				(session.getAttribute("username") != null)
//				 || requestURI.contains("rest")) { 
//			chain.doFilter(httpRequest, response);
//		} else {
//			httpResponse.sendRedirect(httpRequest.getContextPath() + "login.xhtml");
//		}
//		
		if ( !(requestURI.contains("login") || requestURI.contains("register") || requestURI.contains("rest")) && session.getAttribute("username") == null) {
			System.out.println("USERNAME" + session.getAttribute("username"));
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
			return;
		}
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// empty ? 
		
	}

}
