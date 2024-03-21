package com.book.book.business.serviceimpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.book.book.business.service.AuthorService;
import com.book.book.dao.entities.Author;
import com.book.book.dao.repositories.AuthorRepository;


@Service
public class AuthorServiceImp implements AuthorService{
      @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Optional<Author> getAuthor(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
       authorRepository.deleteById(id);
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }
}