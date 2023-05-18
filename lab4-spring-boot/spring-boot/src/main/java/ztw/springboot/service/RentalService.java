package ztw.springboot.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ztw.springboot.exception.RentedBookException;
import ztw.springboot.model.Book;
import ztw.springboot.model.Reader;
import ztw.springboot.repository.RentalRepository;
import ztw.springboot.service.BooksService;
import ztw.springboot.api.dto.RentalFormDTO;
import ztw.springboot.model.Rental;
import ztw.springboot.service.interfaces.IRentalService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RentalService implements IRentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BooksService bookService;

    @Autowired
    private ReaderService readerService;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public List<Rental> getActiveRentals() {
        return rentalRepository.findByReturned(false);
    }

    @Override
    public List<Rental> getInactiveRentals() {
        return rentalRepository.findByReturned(true);
    }

    @Override
    public Rental getRentalById(long id) {
        return rentalRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Rental with id: '" + id + "' not found")
        );
    }

    @Override
    public List<Rental> getRentalsByReaderId(long readerId) {
        return rentalRepository.findByReaderId(readerId);
    }


    @Override
    public Rental addRental(RentalFormDTO rentalDTO) throws RentedBookException {
        if (rentalRepository.existsByBookIdAndReturned(rentalDTO.getBookId(), false))
            throw new RentedBookException(rentalDTO.getBookId());

        Book book = bookService.getBook(rentalDTO.getBookId());
        Reader reader = readerService.getReaderById(rentalDTO.getReaderId());

        Rental rental = new Rental(book, reader, rentalDTO.getDateOfRental(),
                rentalDTO.getDateOfReturn(), rentalDTO.isReturned());
        return rentalRepository.save(rental);
    }

    @Override
    public Rental updateRental(long rentalId, RentalFormDTO rentalDTO) throws RentedBookException {
        Rental rental = getRentalById(rentalId);
        if (rentalRepository.existsByBookIdAndReturned(rentalDTO.getBookId(), false) &&
                rentalDTO.getBookId() != rental.getBook().getId())
            throw new RentedBookException(rentalDTO.getBookId());

        rental.setBook(bookService.getBook(rentalDTO.getBookId()));
        rental.setReader(readerService.getReaderById(rentalDTO.getReaderId()));
        rental.setDateOfRental(rentalDTO.getDateOfRental());
        rental.setDateOfReturn(rentalDTO.getDateOfReturn());
        rental.setReturned(rentalDTO.isReturned());
        return rentalRepository.save(rental);
    }

    @Override
    public String deleteRental(long rentalId) {
        rentalRepository.deleteById(rentalId);
        return "Deleted rental with id: " + rentalId;
    }
}
