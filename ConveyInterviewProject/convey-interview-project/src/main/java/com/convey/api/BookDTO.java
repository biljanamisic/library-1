package com.convey.api;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.convey.core.Author;
import com.convey.core.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDTO implements Serializable {
	
	private static final long serialVersionUID = 4310163996110951337L;

	private String title;
	
	private String isbn;
	
	private Set<AuthorDTO> authors = new HashSet<>();
	
	private Integer numberOfPages;
	
	private Book.Genre genre;
	
	public BookDTO() {}
	
	public BookDTO(Book book) {
		this.title = book.getTitle(); 
		this.isbn = book.getIsbn();
		Set<AuthorDTO> authorDTOs = book.getAuthors()
				.stream()
				.map(a -> new AuthorDTO(a))
				.collect(Collectors.toSet());
		this.authors = authorDTOs;		
		this.numberOfPages = book.getNumberOfPages();
		this.genre = book.getGenre();
	}	

	@JsonProperty
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@JsonProperty
	public Set<AuthorDTO> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<AuthorDTO> authors) {
		this.authors = authors;
	}

	@JsonProperty
	public Integer getNumberOfPages() {
		return numberOfPages;
	}
	
	@JsonProperty
	public Book.Genre getGenre() {
		return genre;
	}

	public void setGenre(Book.Genre genre) {
		this.genre = genre;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
	public static Book toBook(BookDTO bookDTO) {
		Book book = new Book();
		book.setIsbn(bookDTO.getIsbn());
		
		Set<Author> authors = bookDTO.getAuthors()
				.stream()
				.map(dto -> AuthorDTO.toAuthor(dto))
				.collect(Collectors.toSet());
		
		book.setAuthors(authors);
		book.setNumberOfPages(bookDTO.getNumberOfPages());
		book.setTitle(bookDTO.getTitle());
		book.setGenre(bookDTO.getGenre());
		
		return book;
	}
	
	public String toString() {
		return title + ", " + isbn + "authors: " + authors;
	}
	
	
}
