package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import hibernate.DatabaseActions;
import models.Book;

/**
 * Class to handle rest requests at path /book
 */
@Path("/book")
public class RestBook {

	/**
	 * GET request
	 * @return return all books from database as JSON
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Book[] get() {
		System.out.println("GET");
		return DatabaseActions.getAllBooks();
	}

	/**
	 * GET with ID request
	 * @param id
	 * @return return all books rented by user (username passed as id)
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Book[] get(@PathParam("id") String id) {
		System.out.println("GET with id" + id);
		return DatabaseActions.getYourBooks(id);
	}

	/**
	 * POST request
	 * Add book to database
	 * @param book
	 * @return add record
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public String post(Book book) {
		book.toString();
		DatabaseActions.addBook(book);
		return "add record";
	}

	/**
	 * Put operation is not used
	 * @param person
	 * @return string
	 */
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN })
	public String put(Book person) {
		System.out.println("NotUsed");
		return "update record";
	}

	/**
	 * Delete operation is not used
	 * @param id
	 * @return string
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String delete(@PathParam("id") String id) {
		System.out.println("NotUsed");
		return "delete record";
	}

}
