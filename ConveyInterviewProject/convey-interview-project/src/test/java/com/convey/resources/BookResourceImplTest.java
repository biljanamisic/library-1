package com.convey.resources;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.convey.api.BookDTO;
import com.convey.core.Author;
import com.convey.core.Book;
import com.convey.core.Book.Genre;
import com.convey.resources.service.BookService;
import com.convey.resources.service.BookServiceImpl;

import io.dropwizard.testing.junit.ResourceTestRule;

public class BookResourceImplTest {
	
	private static final BookService mockedBookService = mock(BookServiceImpl.class);
	
	private Book expectedBook = createOneBook();
	
	@ClassRule 
	public static final ResourceTestRule resources = ResourceTestRule.builder()
    .addResource(new BookResourceImpl(mockedBookService))
    .build();
	
	@Before
    public void setup() {
        when(mockedBookService.getBookById(10L)).thenReturn(expectedBook);
        when(mockedBookService.getBookById(15L)).thenReturn(null);
        when(mockedBookService.save(expectedBook)).thenReturn(expectedBook);
    }
	
	@After
    public void tearDown(){
        reset(mockedBookService);
    }
	
	@Test
    public void getBookByIdSuccessfully() {
		BookDTO actualResponse = resources.client().target("/api/books/10")
        		.request().get(BookDTO.class);
        assertThat(actualResponse.getIsbn())
        		.isEqualTo(new BookDTO(expectedBook).getIsbn());
        verify(mockedBookService).getBookById(10L);
    }
	
	@Test(expected = WebApplicationException.class)	
	public void getBookByIdThrowsExceptionIfBookNotFound() {
		resources.client().target("/api/books/15")
				.request().get(BookDTO.class);
	}
	
	@Test
	public void testSaveBookSuccessfully() {
		Entity<BookDTO> entity = Entity.entity(new BookDTO(expectedBook), MediaType.APPLICATION_JSON_TYPE);
		Response actualResponse = resources.client().target("/api/books/save")
				.request().post(entity);
		assertThat(actualResponse.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
	}	
	
	// ====================== helper methods for creating dummy data ====================== //
	
	public static Book createOneBook() {
		Book book = new Book();
		book.setBookId(10L);
		book.setGenre(Genre.ADVENTURE);
		book.setIsbn("9781114145400");
		book.setTitle("Dummy book");
		Author author = new Author();
		author.setAuthorId(10L);
		author.setFirstname("John");
		author.setFirstname("Doe");
		Set<Author> authors = new HashSet<>();
		book.setAuthors(authors);
		
		return book;
	}	
}