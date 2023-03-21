package ztw.springboot.service;

import org.springframework.stereotype.Service;
import ztw.springboot.model.Book;
import ztw.springboot.repository.BookRepository;
import ztw.springboot.service.interfaces.IBooksService;

import java.util.Collection;

@Service
public class BooksService implements IBooksService {
    private final BookRepository bookRepository;

    public BooksService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Collection<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(long id) {
        return bookRepository.findById(id).orElse(null);
    }
}