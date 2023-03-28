package ztw.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ztw.springboot.model.Book;

import java.util.Collection;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Collection<Book> findAllByAuthors_Id(long authorId);
}
