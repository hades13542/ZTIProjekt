package jsf;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="view")
@SessionScoped
public class View implements Serializable {

	private static final long serialVersionUID = -1236267405331436292L;

//	public String addBook() {
//		System.out.println("dziala");
//		return "addBook?faces-redirect=true";
//	}
//	
//	public String findBook() {
//		System.out.println("dziala");
//		return "findBook";
//	}
//	
//	public String yourBook() {
//		System.out.println("dziala");
//		return "yourBook";
//	}
}
