package jsf;

import java.io.IOException;
import java.io.InputStream;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hibernate.DatabaseActions;
import models.Book;

@RequestScoped
@ManagedBean(name="findbook")
public class FindBook {

	private List<Book> bookList = getAllBooks();
	
	private List<Book> getAllBooks() {
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			
			HttpGet request = new HttpGet("http://localhost:9080/ZTIProjekt/rest/book");
			
			System.out.println(request);
			HttpResponse response = (HttpResponse) client.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String result = EntityUtils.toString(entity);
				System.out.println(result);
				Gson gson = new Gson();
				TypeToken<List<Book>> token = new TypeToken<List<Book>>(){};
				return gson.fromJson(result, token.getType());
			}
			
		} catch (IOException | ParseException ex) {
			System.out.println("RESTAuthBean: save user error " + ex.getLocalizedMessage());
		}
		return bookList;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	
	public void rent(Book book) throws IOException {
		System.out.println(book);
		DatabaseActions.updateBook(book);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
}
