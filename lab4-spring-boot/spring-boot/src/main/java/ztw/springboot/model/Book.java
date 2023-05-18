package ztw.springboot.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Book id", example = "1")
    private long id;

    @Schema(description = "Number of pages", example = "120")
    int pages;

    @Schema(description = "Title of the book", example = "Harry Potter and the Philosophers Stone")
    private String title;
    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    @Schema(description = "List of authors")
    private List<Author> authors;

    public Book(String title, int pages) {
        this.title = title;
        this.pages = pages;
        this.authors = new ArrayList<>();
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    @PreRemove
    private void removeBookFromAuthors() {
        authors.forEach(b -> b.getBooks().remove(this));
        authors.clear();
    }
}
