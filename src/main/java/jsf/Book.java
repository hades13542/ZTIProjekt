package jsf;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

@RequestScoped
@ManagedBean
public class Book {

	private String author;
	private String title;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void addBookToDatabase() {
		System.out.println("Authoor " + author);
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			
			
			HttpPost request = new HttpPost("http://localhost:9080/ZTIProjekt/rest/book");
			JSONObject json = new JSONObject();
			json.put("author", author);
			json.put("title", title);

			System.out.println(json);
			StringEntity params = new StringEntity(json.toString(), "UTF-8");
			request.addHeader("content-type", "application/json;charset=UTF-8");
			request.addHeader("charset", "UTF-8");
			request.setEntity(params);
			System.out.println(request);
			HttpResponse response = (HttpResponse) client.execute(request);
			HttpEntity entity = response.getEntity();
			System.out.println(response.toString());
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		    ec.redirect(((HttpServletRequest) ec.getRequest()).getContextPath()+ "/admin.xhtml");
		    System.out.println("CONTEXT PATH" + ((HttpServletRequest) ec.getRequest()).getContextPath());
		    
		} catch (IOException | ParseException ex) {
			System.out.println("RESTAuthBean: save user error " + ex.getLocalizedMessage());
		}
	}
}
