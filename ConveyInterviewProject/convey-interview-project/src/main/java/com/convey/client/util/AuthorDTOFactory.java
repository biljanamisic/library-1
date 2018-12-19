package com.convey.client.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.convey.api.AuthorDTO;

public class AuthorDTOFactory {

	public Map<String, String> bookAuthorPairs = new HashMap<>();

	private void populateBookAuthorPairs() {
		bookAuthorPairs.put("Na Drini cuprija", "Ivo Andric");
		bookAuthorPairs.put("Konstantinovo raskrsce", "Dejan Stojiljkovic");
		bookAuthorPairs.put("Zlocin i kazna", "Fjodor Dostojevski");
		bookAuthorPairs.put("Gorski vijenac", "P.P Njegos");
		bookAuthorPairs.put("Veliki rat", "Aleksandar Gatalica");
		bookAuthorPairs.put("Misliti na javi", "Brus Ekel");
		bookAuthorPairs.put("Seobe", "Milos Crnjanski");
		bookAuthorPairs.put("Hazarski recnik", "Milorad Pavic");
	}

	private String getFirstname(String fullName) {
		String[] fullNameDivided = fullName.split(" ");
		return fullNameDivided[0];
	}

	private String getLastname(String fullName) {
		String[] fullNameDivided = fullName.split(" ");
		return fullNameDivided[1];
	}

	public Set<AuthorDTO> createAuthorsPerBook(String bookTitle) {
		populateBookAuthorPairs();
		Set<AuthorDTO> authorsPerBook = new HashSet<>();
		String authorFullName = bookAuthorPairs.get(bookTitle);
		if (authorFullName != null) {
			AuthorDTO authorDTO = new AuthorDTO();
			authorDTO.setFirstname(getFirstname(authorFullName));
			authorDTO.setLastname(getLastname(authorFullName));
			authorsPerBook.add(authorDTO);
		}

		return authorsPerBook;
	}
}