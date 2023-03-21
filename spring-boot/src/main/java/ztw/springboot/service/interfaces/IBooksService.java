package ztw.springboot.service.interfaces;

import ztw.springboot.model.Book;

import java.util.Collection;

public interface IBooksService {
    Collection<Book> getBooks();
    Book getBook(int id);
}
