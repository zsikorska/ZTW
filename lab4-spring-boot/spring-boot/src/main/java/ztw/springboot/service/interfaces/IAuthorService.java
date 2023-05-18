package ztw.springboot.service.interfaces;

import ztw.springboot.api.dto.AuthorFormDTO;
import ztw.springboot.model.Author;

import java.util.List;

public interface IAuthorService {
    List<Author> getAuthors();

    Author getAuthorById(long id);

    Author addAuthor(AuthorFormDTO authorDTO);

    void updateAuthor(long authorId, AuthorFormDTO authorDTO);

    void deleteAuthor(long id);
}
