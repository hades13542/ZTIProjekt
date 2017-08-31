package jsf;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hibernate.DatabaseActions;
import models.Book;

/**
 * Bean to handle addBook.xhtml page
 */
@RequestScoped
@ManagedBean(name = "yourbook")
public class YourBook {

	final static Logger log = Logger.getLogger(YourBook.class);
	private List<Book> yourBookList = getAllBooks();

	/**
	 * Method that use REST interface to catch all rented books from database
	 * Use HTTP GET
	 * @return
	 */
	private List<Book> getAllBooks() {
		HttpSession httpSession = SessionUtils.getSession();
		String username = ((String) httpSession.getAttribute("username"));
		try {
			CloseableHttpClient client = HttpClients.createDefault();

			HttpGet request = new HttpGet("https://ztiprojekttest.mybluemix.net/rest/book/" + username);

			log.debug(request);
			HttpResponse response = (HttpResponse) client.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String result = EntityUtils.toString(entity);
				System.out.println(result);
				Gson gson = new Gson();
				TypeToken<List<Book>> token = new TypeToken<List<Book>>() {
				};
				return gson.fromJson(result, token.getType());
			}

		} catch (IOException | ParseException ex) {
			System.out.println("RESTAuthBean: save user error " + ex.getLocalizedMessage());
		}
		return yourBookList;
	}

	/**
	 * @return yourBookList
	 */
	public List<Book> getYourBookList() {
		return yourBookList;
	}

	/**
	 * @param bookList
	 */
	public void setYourBookList(List<Book> bookList) {
		this.yourBookList = bookList;
	}

	/**
	 * @param book
	 * @throws IOException
	 */
	public void returnBook(Book book) throws IOException {
		log.debug(book);
		DatabaseActions.returnBook(book);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

}
