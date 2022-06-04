package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Publisher publisher1 = new Publisher("publisher1", "address1", "city1", "state1", "zip1");
        publisherRepository.save(publisher1);

        Book book1 = new Book("book1", "2334989234982934");
        Author author1 = new Author("firstName1", "firstName2");
        author1.getBooks().add(book1);
        book1.getAuthors().add(author1);

        book1.setPublisher(publisher1);
        publisher1.getBooks().add(book1);

        // NB order of saving here is important to avoid a TransientObjectException
        authorRepository.save(author1);
        bookRepository.save(book1);
        publisherRepository.save(publisher1);


        Author author2 = new Author("firstName2", "lastName2");
        Book book2 = new Book("book2", "8989232424545");
        author2.getBooks().add(book2);
        book2.getAuthors().add(author2);
        book2.setPublisher(publisher1);
        publisher1.getBooks().add(book2);

        authorRepository.save(author2);
        bookRepository.save(book2);
        publisherRepository.save(publisher1);

        System.out.println("Started in Bootstrap.");
        System.out.println("Number of books initialized: " + bookRepository.count());
        System.out.println("Number of authors initialized: " + authorRepository.count());
        System.out.println("Number of books published by publisher 1: " + publisher1.getBooks().size());


    }
}
