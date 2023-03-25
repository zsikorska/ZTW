package ztw.springboot.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Rental id", example = "1")
    private long id;

    @Schema(description = "Book id", example = "1")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Schema(description = "Reader id", example = "1")
    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @Schema(description = "Date of rental", example = "01-03-2023")
    private String dateOfRental;

    @Schema(description = "Date of return", example = "30-03-2023")
    private String dateOfReturn;

    @Schema(description = "If book was returned", example = "true")
    private boolean returned;
}
