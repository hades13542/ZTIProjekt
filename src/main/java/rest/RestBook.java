package rest;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import hibernate.DatabaseConnection;
import models.Book;

@Path("/book")
public class RestBook {

	@GET
    @Produces({MediaType.APPLICATION_JSON})
    public Book[] get() {
        System.out.println("GET");
        DatabaseConnection db = new DatabaseConnection();
        Session session = db.getSession();
        session.beginTransaction();
        
        List<Object> autorzy = session.createQuery("from Book").list();
 
        session.getTransaction().commit();
        System.out.println(Arrays.toString(autorzy.toArray()));
        Book[] v = Arrays.copyOf(autorzy.toArray(), autorzy.toArray().length, Book[].class);
        return v;
    }
 
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Book get(@PathParam("id") String id) {
        System.out.println("GET");
        Book b = new Book();
        return b;
    }
 
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public String post(Book person) {
        System.out.println("POST");
        return "add record" ;
    }
 
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public String put(Book person) {
        System.out.println("PUT");
        return "update record" ;
    }
 
    @DELETE
    @Path("{id}")
    @Produces({MediaType.TEXT_PLAIN})
    public String delete(@PathParam("id") String id) {
        System.out.println("DELETE");
        return "delete record" ;
    }
    
}