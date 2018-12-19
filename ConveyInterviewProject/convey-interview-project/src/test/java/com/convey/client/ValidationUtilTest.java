package com.convey.client;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.convey.client.util.ValidationUtil;

public class ValidationUtilTest {
	
	@Test
	public void isbnValidIfContainsDigitsOnlyAndBeginsWith978() {
		String isbn = "9781254145111";
		assertTrue(ValidationUtil.isIsbnValid(isbn));
	}
	
	@Test
	public void isbnNotValidIfContainsLetters() {
		String isbn = "97812541AD111";
		assertFalse(ValidationUtil.isIsbnValid(isbn));
	}
	
	@Test
	public void isbnNotValidIfDoesNotBeginIn978() {
		String isbn = "9851254145111";
		assertFalse(ValidationUtil.isIsbnValid(isbn));
	}

	@Test
	public void isbnNotValidIfNull() {
		String isbn = null;
		assertFalse(ValidationUtil.isIsbnValid(isbn));
	}
	
	@Test
	public void isbnNotValidIfContainsMoreThan13Digits() {
		String isbn = "97852545554455555";
		assertFalse(ValidationUtil.isIsbnValid(isbn));
	}	
}
