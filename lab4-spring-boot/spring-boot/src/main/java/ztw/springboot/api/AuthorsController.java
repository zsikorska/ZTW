package ztw.springboot.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztw.springboot.api.dto.AuthorFormDTO;
import ztw.springboot.model.Author;
import ztw.springboot.service.interfaces.IAuthorService;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {
    IAuthorService authorService;

    public AuthorsController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    @Operation(
            summary = "Get authors",
            description = "Get all authors in the database"
    )
    public ResponseEntity<List<Author>> getAuthors() {
        return new ResponseEntity<>(authorService.getAuthors(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get author",
            description = "Get author by id"
    )
    public ResponseEntity<Author> getAuthor(@PathVariable("id") long authorId) {
        return new ResponseEntity<>(authorService.getAuthorById(authorId), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Add new author",
            description = "Add new author to database"
    )
    public ResponseEntity<Author> addAuthor(@RequestBody AuthorFormDTO authorDTO) {
        return new ResponseEntity<>(authorService.addAuthor(authorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update author",
            description = "Update author data by id"
    )
    public ResponseEntity<Void> updateAuthor(@PathVariable("id") long authorId, @RequestBody AuthorFormDTO authorDTO) {
        authorService.updateAuthor(authorId, authorDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete author",
            description = "Delete author from database"
    )
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") long authorId) {

        authorService.deleteAuthor(authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
