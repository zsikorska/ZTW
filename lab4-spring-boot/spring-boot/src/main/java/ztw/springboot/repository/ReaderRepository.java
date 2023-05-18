package ztw.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ztw.springboot.model.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
