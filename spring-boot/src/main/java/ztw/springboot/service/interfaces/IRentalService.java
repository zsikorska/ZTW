package ztw.springboot.service.interfaces;

import ztw.springboot.api.dto.RentalFormDTO;
import ztw.springboot.model.Rental;

import java.util.List;

public interface IRentalService {

    List<Rental> getRentals();

    List<Rental> getActiveRentals();

    Rental getRentalById(long id);

    List<Rental> getRentalsByReaderId(long readerId);

    Rental addRental(RentalFormDTO rentalDTO);

    void updateRental(long rentalId, RentalFormDTO rentalDTO);

    void deleteRental(long id);

}
