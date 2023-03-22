package ztw.springboot.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ztw.springboot.api.dto.BookDTO;
import ztw.springboot.model.Book;
import ztw.springboot.repository.BookRepository;
import ztw.springboot.service.interfaces.IBooksService;

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
    public void addBook(BookDTO bookDTO) {
        Book newBook = new Book(bookDTO.getTitle(), bookDTO.getPages());
        bookDTO.getAuthorIds().forEach(id -> newBook.addAuthor(authorService.getAuthorById(id)));
        bookRepository.save(newBook);
    }

    @Override
    public Book getBook(long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Book with id: '" + id + "' not found")
        );
    }

    @Override
    public void updateBook(long bookId, BookDTO bookDTO) {
        Book book = getBook(bookId);
        mapper.map(bookDTO, book);
        book.setAuthors(bookDTO.getAuthorIds().stream().map(authorService::getAuthorById).toList());
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }
}