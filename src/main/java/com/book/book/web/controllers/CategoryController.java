package com.book.book.web.controllers;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.book.book.business.service.CategoryService;
import com.book.book.dao.entities.Category;
import com.book.book.web.models.CategoryForms;

@Controller
@RequestMapping("/categories")

public class CategoryController {


    
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("categoryForms", new CategoryForms());
        return "category/create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute("categoryForms") @Valid CategoryForms categoryForms,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "category/create";
        }

        Category category = new Category();
        category.setName(categoryForms.getName());

        categoryService.addCategory(category);

        return "redirect:/categories";
    }

    // Read
    @GetMapping
    public String showCategoryList(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/list";
    }

    // Update
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Category> category = categoryService.getCategory(id);
        if (category.isEmpty()) {
            // Handle Category not found
            return "error"; // Add a proper error page
        }

        CategoryForms categoryForms = new CategoryForms(category.get().getId(), category.get().getName());

        model.addAttribute("categoryForms", categoryForms);
        return "category/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateCategory(@PathVariable Long id, @ModelAttribute("categoryForms") @Valid CategoryForms categoryForms, BindingResult bindingResult, Model model) {
        // Your existing code
        if (bindingResult.hasErrors()) {
            return "category/edit";
        }

        Optional<Category> existingCategory = categoryService.getCategory(id);
        if (existingCategory.isEmpty()) {
            // Handle Category not found
            return "error"; // Add a proper error page
        }

        Category category = existingCategory.get();
        category.setName(categoryForms.getName());

       categoryService.updateCategory(category);

        return "redirect:/categories";
    }

    // Delete
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable("id") Long id) {
        Optional<Category> category = categoryService.getCategory(id);
        if (category.isEmpty()) {
            // Handle Category not found
            return "error"; // Add a proper error page
        }
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}

