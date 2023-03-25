package ztw.springboot.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ztw.springboot.api.dto.BookFormDTO;
import ztw.springboot.model.Book;
import ztw.springboot.repository.BookRepository;
import ztw.springboot.service.interfaces.IBooksService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

@Service
public class BooksService implements IBooksService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final ModelMapper mapper;

    public BooksService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.mapper = new ModelMapper();
    }

    @Override
    public Collection<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book addBook(BookFormDTO bookDTO) {
        Book newBook = new Book(bookDTO.getTitle(), bookDTO.getPages());
        bookDTO.getAuthorIds().forEach(id -> newBook.addAuthor(authorService.getAuthorById(id)));
        return bookRepository.save(newBook);
    }

    @Override
    public Book getBook(long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Book with id: '" + id + "' not found")
        );
    }

    @Override
    public Collection<Book> getBooksByAuthorId(long authorId) {
        return bookRepository.findAllByAuthors_Id(authorId);
    }

    @Override
    public void updateBook(long bookId, BookFormDTO bookDTO) {
        Book book = getBook(bookId);
        mapper.map(bookDTO, book);
        var authors = bookDTO.getAuthorIds().stream().map(authorService::getAuthorById).toList();
        book.setAuthors(new ArrayList<>(authors));
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }
}