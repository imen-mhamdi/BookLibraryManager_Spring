package com.book.book.business.service;
import java.util.List;
import java.util.Optional;
import com.book.book.dao.entities.Category;

public interface CategoryService {
    
    // Récupère une catégorie par son ID.
    Optional<Category> getCategory(Long id);

    // Ajoute une nouvelle catégorie.
    Category addCategory(Category category);

    // Met à jour une catégorie existante.
    Category updateCategory(Category category);

    // Supprime une catégorie par son ID.
    void deleteCategory(Long id);

    // Récupère toutes les catégories.
    List<Category> getAllCategories();
}


