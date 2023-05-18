package ztw.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ztw.springboot.model.Rental;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByReturned(boolean returned);

    boolean existsByBookIdAndReturned(long bookId, boolean returned);

    List<Rental> findByReaderId(long readerId);
}
