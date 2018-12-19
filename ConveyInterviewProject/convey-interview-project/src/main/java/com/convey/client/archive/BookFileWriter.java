package com.convey.client.archive;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.convey.core.Book;

public class BookFileWriter {
	
	private static Logger logger = Logger.getLogger(BookFileWriter.class);
	private static final String FILE_DESTINATION = "src/main/resources/archive/";
	private static final String fileName = "books-fetched-from-api.txt";
	
	public void recordBookInFile(Book book) throws IOException {
		logger.log(Priority.INFO, "Trying to write " + book.getTitle() + " into a file");
		try (Writer fileWriter = new FileWriter(FILE_DESTINATION + fileName, true)){
			fileWriter.write(book.toString() + "\n");
		} catch (IOException e) {
			logger.log(Priority.ERROR, "Problem occurred writing into file : " + FILE_DESTINATION + fileName);
			e.printStackTrace();
		}  		
	}
}
