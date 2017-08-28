package jsf;

import java.io.IOException;
import java.util.List;

import models.Book;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hibernate.DatabaseActions;

@RequestScoped
@ManagedBean(name="yourbook")
public class YourBook {


		private List<Book> yourBookList = getAllBooks();
		
		private List<Book> getAllBooks() {
			HttpSession httpSession = SessionUtils.getSession();
			String username = ((String) httpSession.getAttribute("username"));
			try {
				CloseableHttpClient client = HttpClients.createDefault();
				
				HttpGet request = new HttpGet("http://localhost:9080/ZTIProjekt/rest/book/"+username);
				
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
			return yourBookList;
		}

		public List<Book> getYourBookList() {
			return yourBookList;
		}

		public void setYourBookList(List<Book> bookList) {
			this.yourBookList = bookList;
		}
		
		public void returnBook(Book book) throws IOException {
			System.out.println(book);
			DatabaseActions.returnBook(book);
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		}

}
