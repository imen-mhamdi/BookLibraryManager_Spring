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

import com.book.book.business.service.AuthorService;
import com.book.book.dao.entities.Author;
import com.book.book.web.models.AuthorForms;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("authorForms", new AuthorForms());
        return "author/create";
    }

    @PostMapping("/create")
    public String createAuthor(@ModelAttribute("authorForms") @Valid AuthorForms authorForms,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "author/create";
        }

        Author author = new Author();
        author.setName(authorForms.getName());

        authorService.addAuthor(author);

        return "redirect:/authors";
    }

    // Read
    @GetMapping
    public String showAuthorList(Model model) {
        model.addAttribute("authors", authorService.getAllAuthor());
        return "author/list";
    }

    // Update
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Author> author = authorService.getAuthor(id);
        if (author.isEmpty()) {
            // Handle author not found
            return "error"; // Add a proper error page
        }

        AuthorForms authorForms = new AuthorForms(author.get().getId(), author.get().getName());

        model.addAttribute("authorForms", authorForms);
        return "author/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateAuthor(@PathVariable("id") Long id,
            @ModelAttribute("authorForms") @Valid AuthorForms authorForms, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }

        Optional<Author> existingAuthor = authorService.getAuthor(id);
        if (existingAuthor.isEmpty()) {
            // Handle Author not found
            return "error"; // Add a proper error page
        }

        Author author = existingAuthor.get();
        author.setName(authorForms.getName());

        authorService.updateAuthor(author);

        return "redirect:/authors";
    }

    // Delete
    @PostMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable("id") Long id) {
        Optional<Author> author = authorService.getAuthor(id);
        if (author.isEmpty()) {
            // Handle Author not found
            return "error"; // Add a proper error page
        }
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
