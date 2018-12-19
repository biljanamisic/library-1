package com.convey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.convey.api.BookDTO;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/api/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BookResource {
		
	@GET
	@UnitOfWork
	@Path("/{bookId}")
	BookDTO getBookById(@PathParam("bookId") LongParam bookId);
	
	@POST
	@UnitOfWork
	@Path("/save")
	BookDTO saveBook(BookDTO bookDTO);
}
