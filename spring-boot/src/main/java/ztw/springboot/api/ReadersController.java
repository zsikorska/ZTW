package ztw.springboot.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztw.springboot.api.dto.ReaderFormDTO;
import ztw.springboot.model.Reader;
import ztw.springboot.service.interfaces.IReaderService;

import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReadersController {

    @Autowired
    IReaderService readerService;

    @GetMapping()
    @Operation(
            summary = "Get readers",
            description = "Get all readers in the database"
    )
    public ResponseEntity<List<Reader>> getReaders() {
        return new ResponseEntity<>(readerService.getReaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get reader",
            description = "Get reader by id"
    )
    public ResponseEntity<Reader> getReader(@PathVariable("id") long readerId) {
        return new ResponseEntity<>(readerService.getReaderById(readerId), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Add new reader",
            description = "Add new reader to database"
    )
    public ResponseEntity<Reader> addReader(@RequestBody ReaderFormDTO readerDTO) {
        return new ResponseEntity<>(readerService.addReader(readerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update reader",
            description = "Update reader data by id"
    )
    public ResponseEntity<Void> updateReader(@PathVariable("id") long readerId, @RequestBody ReaderFormDTO readerDTO) {
        readerService.updateReader(readerId, readerDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete reader",
            description = "Delete reader from database"
    )
    public ResponseEntity<Void> deleteReader(@PathVariable("id") long readerId) {
        readerService.deleteReader(readerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
