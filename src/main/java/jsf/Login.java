package jsf;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name="login")
@SessionScoped
public class Login implements Serializable {

	private static final long serialVersionUID = -1875369553307779330L;
	private String password;
	private String username;
	private String email;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    private String page="login.xhtml";
    
    public String getPage() {
        return page;
    }
 
    public void setPage(String currentPage) {
        this.page=currentPage;
    }
	
	public String validateUsernamePassword() {
		boolean valid = LoginDAO.validate(username, password);
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", username);
			return "admin"; // TODO: change ?
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect username and password", "Please enter correct username and password"));
			return "login";
		}
	}
	
	public String logout() {
		SessionUtils.getSession().invalidate();
		return "login";
	}
	
	public String register() throws IOException {
		boolean isUsernameTaken = LoginDAO.checkUsernameAvailability(username);
		if (!isUsernameTaken) {
			LoginDAO.createNewUser(username,password,email);
			return "login";
		} else {
			FacesContext.getCurrentInstance().addMessage("registerForm:register", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Username is taken", "Please enter another username"));
			return "register";
		}
	}

	//./
	public String addBook() {
		System.out.println("dziala");
		return "addBook?faces-redirect=true";
	}
	
//	public void addBookToDatabase() {
//		System.out.println("dziala");
//		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "","Working");
//        FacesContext.getCurrentInstance().addMessage("addBookForm:title", msg);
//	}
	
	public String findBook() {
		System.out.println("findbook");
		return "findBook?faces-redirect=true";
	}
	
	public String yourBook() {
		System.out.println("dziala");
		return "yourBook?faces-redirect=true";
	}

}
