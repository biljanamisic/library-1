package com.convey.resources.service;

import com.convey.core.Book;
import com.convey.db.BookDAO;

public class BookServiceImpl implements BookService {

	private BookDAO bookDAO;
	
	public BookServiceImpl(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	@Override
	public Book getBookById(Long bookId) {
		return bookDAO.getBookById(bookId);
	}
	
	@Override
	public Book save(Book book) {
		Book savedBook = bookDAO.save(book);
		return savedBook;
	}



}
