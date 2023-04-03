package ztw.springboot.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztw.springboot.api.dto.BookFormDTO;
import ztw.springboot.model.Book;
import ztw.springboot.service.interfaces.IBooksService;

@RestController
@RequestMapping("/books")
public class BooksController {
    IBooksService booksService;

    public BooksController(IBooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping()
    @Operation(
            summary = "Get books",
            description = "Get all books in the database"
    )
    public ResponseEntity<Object> getBooks() {
        return new ResponseEntity<>(booksService.getBooks(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get book",
            description = "Get book by id"
    )
    public ResponseEntity<Object> getBook(@PathVariable("id") long bookId) {
        return new ResponseEntity<>(booksService.getBook(bookId), HttpStatus.OK);
    }

    @GetMapping(value = "/author/{id}")
    @Operation(
            summary = "Get books by author id",
            description = "Get all books by author id"
    )
    public ResponseEntity<Object> getBooksByAuthorId(@PathVariable("id") long authorId) {
        return new ResponseEntity<>(booksService.getBooksByAuthorId(authorId), HttpStatus.OK);
    }

    @GetMapping(value = "available")
    @Operation(
            summary = "Get available books",
            description = "Get all books that are currently available in the library"
    )
    public ResponseEntity<Object> getAvailableBooks() {
        return new ResponseEntity<>(booksService.getAvailableBooks(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add new book",
            description = "Add new book to database with assigned authors"
    )
    public ResponseEntity<Book> addBook(@RequestBody BookFormDTO bookDTO) {
        return new ResponseEntity<>(booksService.addBook(bookDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update book",
            description = "Update book data and authors by id"
    )
    public ResponseEntity<Void> updateBook(@PathVariable("id") long bookId, @RequestBody BookFormDTO bookDTO) {
        booksService.updateBook(bookId, bookDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete book",
            description = "Delete book from database"
    )
    public ResponseEntity<Void> deleteBook(@PathVariable("id") long bookId) {
        booksService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
