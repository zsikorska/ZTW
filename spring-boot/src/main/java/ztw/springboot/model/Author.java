package ztw.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Author id", example = "1")
    private long id;
    @Schema(description = "First name", example = "Joanne")
    private String firstName;
    @Schema(description = "Last name", example = "Rowling")
    private String lastName;

    @JsonIgnore
    @ManyToMany(mappedBy="authors")
    private Set<Book> books;

    @PreRemove
    private void removeAuthorFromBooks() {
        books.forEach(b -> b.getAuthors().remove(this));
        books.clear();
    }
}
