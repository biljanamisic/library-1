package com.convey.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.convey.api.BookDTO;
import com.convey.client.archive.BookFileWriter;
import com.convey.client.util.BookDTOFactory;
import com.convey.client.util.exceptions.InconsistentBookDataException;

import java.io.IOException;
import java.util.List;

import io.dropwizard.jersey.params.LongParam;

@Path("/client/books")
@Produces(MediaType.APPLICATION_JSON)
public class RestClientController {	
	
	private static Logger logger = Logger.getLogger(RestClientController.class);
	private Client client;
	private static String BASE_URI = "http://localhost:8080/library/api/books/";		
	private WebTarget getBookTarget;
	private WebTarget saveBookTarget;
	List<BookDTO> booksForSaving;
	
	public RestClientController(Client client) throws InconsistentBookDataException {
		this.client = client;
		getBookTarget = client.target(BASE_URI).path("/{bookId}");	
		saveBookTarget = client.target(BASE_URI).path("/save");
	    booksForSaving = BookDTOFactory.createBooksForSaving();  
	}
	
	@GET
	@Path("/{bookId}")
	public BookDTO getBookById(@PathParam("bookId") LongParam bookId) {
		try {
			logger.log(Priority.INFO, "Sending request to API to fetch a book with bookId " + bookId); 
			BookDTO response = getBookTarget
				.resolveTemplate("bookId", bookId)
				.request()
				.get(BookDTO.class); 
			
			try {
				new BookFileWriter().recordBookInFile(BookDTO.toBook(response));
			} catch (IOException e) {
				logger.log(Priority.ERROR, "IOException occurred while writing book into a file");
				e.printStackTrace();
			} 
	        return response;
		} catch(WebApplicationException ex) {
			logger.log(Priority.ERROR, "Error with status " + ex.getResponse().getStatus() + 
					" received from books API");
			throw new WebApplicationException("Cannot fetch book with id " + bookId, ex.getResponse());
		} 
	}
	
	@GET
	@Path("/save-books-in-bulk")
	public Response saveBooksInBulk() {
		try {
			booksForSaving.addAll(BookDTOFactory.createTwoBooksWithMultipleAuthors());
			if (booksForSaving.size() > 10) {
				logger.log(Priority.ERROR, "Cannot save more than 10 books at once");
				return Response.serverError().build();
			}
			logger.log(Priority.INFO, "Sending request to books API to save " + booksForSaving.size() + " books");
			for (BookDTO bookDtoRequest : booksForSaving) {			
				Response response = saveBookTarget
					.request()
					.post(Entity.json(bookDtoRequest), Response.class);
				if (Response.Status.OK.getStatusCode() != response.getStatus()) {
					logger.log(Priority.ERROR, "Error occurred while saving book: " + bookDtoRequest.getTitle());
					return Response.serverError().build();
				}
			}
		} catch (Exception e) {
			logger.log(Priority.INFO, "Exception occurred while saving books in bulk: " + e.getMessage());
		}
		return Response.ok().build();
	}
}
