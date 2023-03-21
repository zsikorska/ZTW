package ztw.springboot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ztw.springboot.service.interfaces.IBooksService;

@RestController
public class BooksController {
    IBooksService booksService;

    public BooksController(IBooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping(value = "/get/books")
    public ResponseEntity<Object> getBooks() {
        return new ResponseEntity<>(booksService.getBooks(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/book/{id}")
    public ResponseEntity<Object> getBook(@PathVariable("id") int id) {
        return new ResponseEntity<>(booksService.getBook(id), HttpStatus.OK);
    }
}
