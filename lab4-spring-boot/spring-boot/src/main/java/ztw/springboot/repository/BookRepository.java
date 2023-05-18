package ztw.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ztw.springboot.model.Book;

import java.util.Collection;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Collection<Book> findAllByAuthors_Id(long authorId);

    @Query("SELECT b FROM Book b WHERE b NOT IN (SELECT r.book FROM Rental r WHERE r.returned = false)")
    Collection<Book> findAvailableBooks();
}
