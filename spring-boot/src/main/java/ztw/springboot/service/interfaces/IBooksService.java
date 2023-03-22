package ztw.springboot.service.interfaces;

import ztw.springboot.api.dto.BookDTO;
import ztw.springboot.model.Book;

import java.util.Collection;

public interface IBooksService {
    Collection<Book> getBooks();

    Book getBook(long id);

    void addBook(BookDTO bookDTO);

    void updateBook(long bookId, BookDTO bookDTO);

    void deleteBook(long id);
}
