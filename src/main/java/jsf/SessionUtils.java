package jsf;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class to handle Session
 */
public class SessionUtils {

	private SessionUtils() {
		//Access in static way
	}
	/** 
	 * Method return existing session or create a new one
	 * @return session
	 */
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	/**
	 * @return request
	 */
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * @return username attribute from session
	 */
	public static String getUsername() {
		HttpSession session = getSession();
		return session.getAttribute("username").toString();
	}

	/**
	 * @return user ID
	 */
	public static String getUserId() {
		HttpSession session = getSession();
		if (session != null) {
			return session.getAttribute("userid").toString();
		}
		return null;
	}

}
