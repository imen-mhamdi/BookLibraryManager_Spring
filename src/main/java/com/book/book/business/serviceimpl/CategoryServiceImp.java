package com.book.book.business.serviceimpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.book.book.business.service.CategoryService;
import com.book.book.dao.entities.Category;
import com.book.book.dao.repositories.CategoryRepository;

@Service
public class CategoryServiceImp implements  CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}