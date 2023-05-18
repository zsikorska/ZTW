package ztw.springboot.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class RentalFormDTO {

    @Schema(description = "Book id", example = "1")
    private long bookId;

    @Schema(description = "Reader id", example = "1")
    private long readerId;

    @Schema(description = "Date of rental", example = "2023-03-01")
    private LocalDate dateOfRental;

    @Schema(description = "Date of return", example = "2023-03-30")
    private LocalDate dateOfReturn;

    @Schema(description = "If book was returned", example = "true")
    private boolean returned;
}
