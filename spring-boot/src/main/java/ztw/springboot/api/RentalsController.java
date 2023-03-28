package ztw.springboot.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztw.springboot.api.dto.RentalFormDTO;
import ztw.springboot.exception.RentedBookException;
import ztw.springboot.model.Rental;
import ztw.springboot.service.interfaces.IRentalService;

import java.util.List;


@RestController
@RequestMapping("/rentals")
public class RentalsController {
    final IRentalService rentalService;

    public RentalsController(IRentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    @Operation(
            summary = "Get rentals",
            description = "Get all rentals in the database"
    )
    public ResponseEntity<List<Rental>> getRentals() {
        return ResponseEntity.ok(rentalService.getRentals());
    }

    @GetMapping("/active")
    @Operation(
            summary = "Get active rentals",
            description = "Get all active rentals in the database"
    )
    public ResponseEntity<List<Rental>> getActiveRentals() {
        return ResponseEntity.ok(rentalService.getActiveRentals());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get rental",
            description = "Get rental by id"
    )
    public ResponseEntity<Rental> getRental(@PathVariable("id") long rentalId) {
        return ResponseEntity.ok(rentalService.getRentalById(rentalId));
    }

    @GetMapping("/reader/{id}")
    @Operation(
            summary = "Get rentals by reader id",
            description = "Get all rentals by reader id"
    )
    public ResponseEntity<List<Rental>> getRentalsByReaderId(@PathVariable("id") long readerId) {
        return ResponseEntity.ok(rentalService.getRentalsByReaderId(readerId));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add new rental",
            description = "Add new rental to database"
    )
    public ResponseEntity<Rental> addRental(@RequestBody RentalFormDTO rentalDTO) throws RentedBookException {
        return new ResponseEntity<>(rentalService.addRental(rentalDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update rental",
            description = "Update rental data by id"
    )
    public ResponseEntity<Rental> updateRental(@PathVariable("id") long rentalId, @RequestBody RentalFormDTO rentalDTO) throws RentedBookException {
        return new ResponseEntity<>(rentalService.updateRental(rentalId, rentalDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete rental",
            description = "Delete rental by id"
    )
    public ResponseEntity<?> deleteRental(@PathVariable("id") long rentalId) {
        return new ResponseEntity<>(rentalService.deleteRental(rentalId), HttpStatus.OK);
    }


}
