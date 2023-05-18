package ztw.springboot.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class BookFormDTO {
    @Schema(description = "Number of pages", example = "120")
    int pages;
    @Schema(description = "Title of the book", example = "Harry Potter and the Philosophers Stone")
    private String title;
    @Schema(description = "List of authors ids", example = "[1,2]")
    private List<Long> authorIds;
}
