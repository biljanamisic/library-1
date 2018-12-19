package com.convey.client.util;

import org.apache.commons.lang3.StringUtils;

public class ValidationUtil {

	//ISBN must begin with 978 and contain 13 digits in total, letters not allowed
	public static boolean isIsbnValid(String isbn) {
		if (StringUtils.isEmpty(isbn) || isbn.length() != 13)
			return false;
		String regex = "^978[0-9]+";
		if (!isbn.matches(regex))
			return false;
		else return true;
	}	
}
