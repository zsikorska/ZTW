package ztw.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ztw.springboot.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}
