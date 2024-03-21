package com.book.book.business.service;
import java.util.List;
import java.util.Optional;
import com.book.book.dao.entities.Author;


public interface AuthorService {
        
    // Récupère une catégorie par son ID.
    Optional<Author> getAuthor(Long id);

    // Ajoute une nouvelle catégorie.
    Author addAuthor(Author author);

    // Met à jour une catégorie existante.
    Author updateAuthor(Author author);

    // Supprime une catégorie par son ID.
    void deleteAuthor(Long id);

    // Récupère toutes les catégories.
    List<Author> getAllAuthor();
}