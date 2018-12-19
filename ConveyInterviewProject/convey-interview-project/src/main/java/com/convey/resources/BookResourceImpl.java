package com.convey.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.convey.api.BookDTO;
import static com.convey.client.util.ValidationUtil.isIsbnValid;
import com.convey.core.Book;
import com.convey.resources.service.BookService;

import io.dropwizard.jersey.params.LongParam;

public class BookResourceImpl implements BookResource {

	private BookService bookService;
	
	public BookResourceImpl() {}
	
	public BookResourceImpl(BookService bookService) {
		this.bookService = bookService;
	}
	
	@Override
	public BookDTO getBookById(LongParam bookId) {
		Book book = bookService.getBookById(bookId.get());
		if (book == null) {
			throw new WebApplicationException("Book with id " + bookId + " does not exist", 
					Response.Status.NOT_FOUND);
		}		
		return new BookDTO(book);
	}

	@Override
	public BookDTO saveBook(BookDTO bookDTO) {
		if (!isIsbnValid(bookDTO.getIsbn())) {
			throw new WebApplicationException("Invalid isbn for book", 
					Response.Status.BAD_REQUEST);
		}
			
		Book book = BookDTO.toBook(bookDTO);
		Book returnedBook = bookService.save(book);
		return new BookDTO(returnedBook);
	}
}
