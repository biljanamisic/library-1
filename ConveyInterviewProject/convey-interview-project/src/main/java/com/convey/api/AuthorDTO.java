package com.convey.api;

import java.io.Serializable;

import com.convey.core.Author;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorDTO implements Serializable {

	private static final long serialVersionUID = 7663586332968044903L;

	private Long authorId;

	private String firstname;
	
	private String lastname;
	
	public AuthorDTO() {}
	
	public AuthorDTO(Author author) {
		this.authorId = author.getAuthorId();
		this.firstname = author.getFirstname();
		this.lastname = author.getLastname();
	}
	
	@JsonProperty
	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	@JsonProperty
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@JsonProperty
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public static Author toAuthor(AuthorDTO authorDTO) {
		Author author = new Author();
		author.setAuthorId(authorDTO.getAuthorId());
		author.setFirstname(authorDTO.getFirstname());
		author.setLastname(authorDTO.getLastname());
		
		return author;
	}	
	
	public String toString() {
		return firstname + " " + lastname;
	}
}
