package ztw.springboot.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ztw.springboot.model.Book;
import ztw.springboot.model.Reader;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class RentalFormDTO {

    @Schema(description = "Book id", example = "1")
    private long bookId;

    @Schema(description = "Reader id", example = "1")
    private Reader readerId;

    @Schema(description = "Date of rental", example = "01-03-2023")
    private String dateOfRental;

    @Schema(description = "Date of return", example = "30-03-2023")
    private String dateOfReturn;

    @Schema(description = "If book was returned", example = "true")
    private boolean returned;
}
