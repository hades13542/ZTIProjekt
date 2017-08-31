package jsf;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * Bean to handle addBook.xhtml page
 */
@RequestScoped
@ManagedBean
public class Book {

	private String author;
	private String title;

	final static Logger log = Logger.getLogger(Book.class);

	/**
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Method to create Http POST request and prepare JsonObject with information
	 * about Book and then send it via Rest interface.
	 */
	public void addBookToDatabase() {
		log.debug("Authoor " + author);
		try {
			CloseableHttpClient client = HttpClients.createDefault();

			HttpPost request = new HttpPost("https://ztiprojekttest.mybluemix.net/rest/book");
			JSONObject json = new JSONObject();
			json.put("author", author);
			json.put("title", title);

			StringEntity params = new StringEntity(json.toString(), "UTF-8");
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.addHeader("charset", "UTF-8");
			request.setEntity(params);
			HttpResponse response = (HttpResponse) client.execute(request);
			log.debug(response.toString());
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getContextPath() + "/admin.xhtml");
		} catch (IOException | ParseException ex) {
			log.error("RESTAuthBean: save user error " + ex.getLocalizedMessage(), ex);
		}
	}
}
