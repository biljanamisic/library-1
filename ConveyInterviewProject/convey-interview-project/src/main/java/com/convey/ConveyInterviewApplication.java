package com.convey;

import javax.ws.rs.client.Client;


import com.convey.api.AuthorDTO;
import com.convey.api.BookDTO;
import com.convey.client.RestClientController;
import com.convey.client.util.exceptions.InconsistentBookDataException;
import com.convey.core.Author;
import com.convey.core.Book;
import com.convey.db.BookDAO;
import com.convey.resources.BookResourceImpl;
import com.convey.resources.service.BookService;
import com.convey.resources.service.BookServiceImpl;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ConveyInterviewApplication extends Application<ConveyInterviewConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ConveyInterviewApplication().run(args);
    }

    @Override
    public String getName() {
        return "interview";
    }

	private final HibernateBundle<ConveyInterviewConfiguration> hibernateBundle = new HibernateBundle<ConveyInterviewConfiguration>(
			Book.class, Author.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(ConveyInterviewConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public void initialize(final Bootstrap<ConveyInterviewConfiguration> bootstrap) {
		bootstrap.addBundle(hibernateBundle);
	}
	
	@Override
	public void run(final ConveyInterviewConfiguration configuration, final Environment environment) throws InconsistentBookDataException {
		//BookResource bookResource = new BookResourceImpl(); 
		//environment.jersey().register(BookResourceImpl.class);
		final BookDAO bookDAO = new BookDAO(hibernateBundle.getSessionFactory()); 
		environment.jersey().register(bookDAO);
		//environment.jersey().register(BookServiceImpl.class);
		final BookService bookService = new BookServiceImpl(bookDAO); 
		
		environment.jersey().register(bookService);
		environment.jersey().register(new BookResourceImpl(bookService)); 
		
		
		JerseyClientConfiguration conf = configuration.getJerseyClientConfiguration(); 

        conf.setChunkedEncodingEnabled(false);

        final Client client = new JerseyClientBuilder(environment).using(conf).build(getName());
        environment.jersey().register(new RestClientController(client));
        //environment.jersey().register(new MyPostResource()); 
        environment.jersey().register(AuthorDTO.class);
        environment.jersey().register(BookDTO.class);
	}
}
