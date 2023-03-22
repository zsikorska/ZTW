package ztw.springboot.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ztw.springboot.api.dto.AuthorDTO;
import ztw.springboot.model.Author;
import ztw.springboot.repository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper mapper;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.mapper = new ModelMapper();
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(long id) {
        return authorRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Author with id: '" + id + "' not found")
        );
    }

    public void addAuthor(AuthorDTO authorDTO) {
        Author newAuthor = mapper.map(authorDTO, Author.class);
        authorRepository.save(newAuthor);
    }

    public void updateAuthor(long authorId, AuthorDTO authorDTO) {
        Author author = getAuthorById(authorId);
        mapper.map(authorDTO, author);
        authorRepository.save(author);
    }

    public void deleteAuthor(long authorId) {
        authorRepository.deleteById(authorId);
    }
}
