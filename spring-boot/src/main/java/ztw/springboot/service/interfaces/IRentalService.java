package ztw.springboot.service.interfaces;

import ztw.springboot.api.dto.RentalFormDTO;
import ztw.springboot.model.Rental;

import java.util.List;

public interface IRentalService {

    List<Rental> getRentals();

    Rental getRentalById(long id);

    Rental addRental(RentalFormDTO rentalDTO);

    void updateRental(long rentalId, RentalFormDTO rentalDTO);

    void deleteRental(long id);
}
