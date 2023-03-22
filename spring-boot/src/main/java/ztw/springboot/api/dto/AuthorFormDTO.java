package ztw.springboot.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AuthorFormDTO {
    @Schema(description = "First name", example = "Joanne")
    private String firstName;
    @Schema(description = "Last name", example = "Rowling")
    private String lastName;
}
