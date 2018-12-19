package com.convey.db;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.hibernate.SessionFactory;

import com.convey.core.Book;

import io.dropwizard.hibernate.AbstractDAO;

public class BookDAO extends AbstractDAO<Book> {
	
	private Logger logger = Logger.getLogger(BookDAO.class);

	public BookDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public Book getBookById(Long bookId) {
		logger.log(Priority.INFO, "About to retrieve book with id " + bookId);
		return get(bookId);
	}
	
	public Book save(Book book) {
		logger.log(Priority.INFO, "About to save book with isbn " + book.getIsbn());		
		return persist(book);		
	}
}
