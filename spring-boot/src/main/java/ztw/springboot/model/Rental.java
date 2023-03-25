package ztw.springboot.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Rental id", example = "1")
    private long id;

    @Schema(description = "Book")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Schema(description = "Reader")
    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @Schema(description = "Date of rental", example = "2023-03-01")
    private LocalDate dateOfRental;

    @Schema(description = "Date of return", example = "2023-03-30")
    private LocalDate dateOfReturn;

    @Schema(description = "If book was returned", example = "true")
    private boolean returned;

    public Rental(Book book, Reader reader, LocalDate dateOfRental, LocalDate dateOfReturn, boolean returned) {
        this.book = book;
        this.reader = reader;
        this.dateOfRental = dateOfRental;
        this.dateOfReturn = dateOfReturn;
        this.returned = returned;
    }

}
