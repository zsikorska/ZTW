package ztw.springboot.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztw.springboot.api.dto.RentalFormDTO;
import ztw.springboot.exception.RentedBookException;
import ztw.springboot.service.interfaces.IRentalService;


@RestController
@RequestMapping("/rentals")
public class RentalsController {

    @Autowired
    IRentalService rentalService;

    @GetMapping
    @Operation(
            summary = "Get rentals",
            description = "Get all rentals in the database"
    )
    public ResponseEntity<?> getRentals() {
        return ResponseEntity.ok(rentalService.getRentals());
    }

    @GetMapping("/active")
    @Operation(
            summary = "Get active rentals",
            description = "Get all active rentals in the database"
    )
    public ResponseEntity<?> getActiveRentals() {
        return ResponseEntity.ok(rentalService.getActiveRentals());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get rental",
            description = "Get rental by id"
    )
    public ResponseEntity<?> getRental(@PathVariable("id") long rentalId) {
        return ResponseEntity.ok(rentalService.getRentalById(rentalId));
    }

    @GetMapping("/reader/{id}")
    @Operation(
            summary = "Get rentals by reader id",
            description = "Get all rentals by reader id"
    )
    public ResponseEntity<?> getRentalsByReaderId(@PathVariable("id") long readerId) {
        return ResponseEntity.ok(rentalService.getRentalsByReaderId(readerId));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add new rental",
            description = "Add new rental to database"
    )
    public ResponseEntity<?> addRental(@RequestBody RentalFormDTO rentalDTO) throws RentedBookException {
        return new ResponseEntity<>(rentalService.addRental(rentalDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update rental",
            description = "Update rental data by id"
    )
    public ResponseEntity<?> updateRental(@PathVariable("id") long rentalId, @RequestBody RentalFormDTO rentalDTO) throws RentedBookException {
        rentalService.updateRental(rentalId, rentalDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete rental",
            description = "Delete rental by id"
    )
    public ResponseEntity<?> deleteRental(@PathVariable("id") long rentalId) {
        rentalService.deleteRental(rentalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
