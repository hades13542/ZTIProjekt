package jsf;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Bean to handle login.xhtml and register.xhtml page
 */
@ManagedBean(name = "login")
@SessionScoped
public class Login implements Serializable {

	private static final long serialVersionUID = -1875369553307779330L;
	private String password;
	private String username;
	private String email;

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	private String page = "login.xhtml";

	/**
	 * @return page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param currentPage
	 */
	public void setPage(String currentPage) {
		this.page = currentPage;
	}

	/**
	 * @return admin page if password is valid, otherwise stay on login page.
	 */
	public String validateUsernamePassword() {
		boolean valid = LoginDAO.validate(username, password);
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", username);
			return "admin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect username and password", "Please enter correct username and password"));
			return "login";
		}
	}

	/**
	 * Method to logout and invalidate session 
	 * @return login page
	 */
	public String logout() {
		SessionUtils.getSession().setAttribute("username", null);
		SessionUtils.getSession().invalidate();
		return "login";
	}

	/**
	 * Method to add new User in database
	 * @return login page if created successfully, otherwise stay on register page.
	 * @throws IOException
	 */
	public String register() throws IOException {
		boolean isUsernameTaken = LoginDAO.checkUsernameAvailability(username);
		if (!isUsernameTaken) {
			LoginDAO.createNewUser(username, password, email);
			return "login";
		} else {
			FacesContext.getCurrentInstance().addMessage("registerForm:register",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Username is taken", "Please enter another username"));
			return "register";
		}
	}

	/**
	 * @return string that redirect to addBook.xhtml
	 */
	public String addBook() {
		return "addBook?faces-redirect=true";
	}

	/**
	 * @return string that redirect to findBook.xhtml
	 */
	public String findBook() {
		return "findBook?faces-redirect=true";
	}

	/**
	 * @return string that redirect to yourBook.xhtml
	 */
	public String yourBook() {
		return "yourBook?faces-redirect=true";
	}

}
