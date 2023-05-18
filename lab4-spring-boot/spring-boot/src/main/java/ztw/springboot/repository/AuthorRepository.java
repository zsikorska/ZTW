package ztw.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ztw.springboot.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
