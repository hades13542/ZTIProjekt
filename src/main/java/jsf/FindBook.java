package jsf;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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
 * Bean to handle findBook.xhtml page
 */
@RequestScoped
@ManagedBean(name = "findbook")
public class FindBook {

	final static Logger log = Logger.getLogger(FindBook.class);

	private List<Book> bookList = getAllBooks();

	/**
	 * Method that use REST interface to catch all books from database
	 * Use HTTP GET
	 * @return
	 */
	private List<Book> getAllBooks() {
		try {
			CloseableHttpClient client = HttpClients.createDefault();

			HttpGet request = new HttpGet("https://ztiprojekttest.mybluemix.net/rest/book");

			log.debug(request);
			HttpResponse response = (HttpResponse) client.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String result = EntityUtils.toString(entity);
				log.debug(result);
				Gson gson = new Gson();
				TypeToken<List<Book>> token = new TypeToken<List<Book>>() {
				};
				return gson.fromJson(result, token.getType());
			}

		} catch (IOException | ParseException ex) {
			log.error("RESTAuthBean: save user error " + ex.getLocalizedMessage(), ex);
		}
		return bookList;
	}

	/**
	 * @return bookList
	 */
	public List<Book> getBookList() {
		return bookList;
	}

	/**
	 * @param bookList
	 */
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	/**
	 * Method that allow to change status of book to rented
	 * @param book
	 * @throws IOException
	 */
	public void rent(Book book) throws IOException {
		DatabaseActions.updateBook(book);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
}
