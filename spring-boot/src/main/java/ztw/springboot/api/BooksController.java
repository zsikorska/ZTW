package ztw.springboot.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztw.springboot.api.dto.BookDTO;
import ztw.springboot.service.interfaces.IBooksService;

@RestController
@RequestMapping("/books")
public class BooksController {
    IBooksService booksService;

    public BooksController(IBooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping()
    public ResponseEntity<Object> getBooks() {
        return new ResponseEntity<>(booksService.getBooks(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getBook(@PathVariable("id") int id) {
        return new ResponseEntity<>(booksService.getBook(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addBook(@RequestBody BookDTO bookDTO) {
        booksService.addBook(bookDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
