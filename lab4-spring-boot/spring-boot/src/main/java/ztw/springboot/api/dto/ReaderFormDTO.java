package ztw.springboot.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReaderFormDTO {

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Last name", example = "Smith")
    private String lastName;

    @Schema(description = "Email", example = "john.smith@gmail.com")
    private String email;
}
