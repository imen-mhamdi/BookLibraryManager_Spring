package com.book.book.web.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  // Add import for LoggerFactory
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.book.book.business.service.AuthorService;
import com.book.book.business.service.CategoryService;
import com.book.book.business.service.DocumentService;
import com.book.book.dao.entities.Author;
import com.book.book.dao.entities.Category;
import com.book.book.dao.entities.Document;
import com.book.book.web.models.DocumentForms;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private DocumentService documentService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("documentForms", new DocumentForms());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("authors", authorService.getAllAuthor());
        return "create";
    }

    @PostMapping("/create")
    public String createDocument(@ModelAttribute("documentForms") @Valid DocumentForms documentForms,
            @RequestParam("file") MultipartFile file, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("authors", authorService.getAllAuthor());
            return "create";
        }
    
        Document document = new Document();
        document.setTitle(documentForms.getTitle());
        document.setSummary(documentForms.getSummary());
        document.setKeywords(documentForms.getKeywords());
        document.setDatePublication(documentForms.getDatePublication());
        Optional<Category> category = categoryService.getCategory(documentForms.getCategory().getId());
        document.setCategory(category.orElse(null));
        Optional<Author> author = authorService.getAuthor(documentForms.getAuthor().getId());
        document.setAuthor(author.orElse(null));
    
        // Spécifiez ici le chemin absolu où vous souhaitez enregistrer les fichiers téléchargés
        String uploadPath = "C:/Users/user/Desktop/springboot/project2/book/src/main/resources/templates/uploads/";
    
        // Générez un nom de fichier unique
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    
        Path filePath = Paths.get(uploadPath + fileName);
        try {
            // Vérifiez si le dossier existe, sinon, créez-le
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            document.setFilePath(uploadPath + fileName);
        } catch (IOException e) {
            logger.error("Error copying file", e);
            // Gérez l'exception en fonction de vos besoins
        }
    
        documentService.addDocument(document);
    
        return "redirect:/documents";
    }
    // Read
    @GetMapping
    public String showDocumentList(Model model) {
        model.addAttribute("documents", documentService.getAllDocument());
        return "list";
    }

    // Update
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Document> document = documentService.getDocument(id);
        if (document.isEmpty()) {
            // Handle document not found
            return "error"; // Add a proper error page
        }

        DocumentForms documentForms = new DocumentForms();
        documentForms.setId(document.get().getId());
        documentForms.setTitle(document.get().getTitle());
        documentForms.setSummary(document.get().getSummary());
        documentForms.setKeywords(document.get().getKeywords());
        documentForms.setDatePublication(document.get().getDatePublication());

        Author author = document.get().getAuthor();
        if (author != null) {
            documentForms.setAuthor(author);
        }

        Category category = document.get().getCategory();
        if (category != null) {
            documentForms.setCategory(category);
        }

        model.addAttribute("documentForms", documentForms);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("authors", authorService.getAllAuthor());
        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String updateDocument(@PathVariable("id") Long id,
            @ModelAttribute("documentForms") @Valid DocumentForms documentForms, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("authors", authorService.getAllAuthor());
            return "edit";
        }

        Optional<Document> existingDocument = documentService.getDocument(id);
        if (existingDocument.isEmpty()) {
            // Handle document not found
            return "error"; // Add a proper error page
        }

        Document document = existingDocument.get();
        document.setTitle(documentForms.getTitle());
        document.setSummary(documentForms.getSummary());
        document.setKeywords(documentForms.getKeywords());
        document.setDatePublication(LocalDate.now());

        Author author = documentForms.getAuthor();
        if (author != null) {
            document.setAuthor(authorService.getAuthor(author.getId()).orElse(null));
        }

        Category category = documentForms.getCategory();
        if (category != null) {
            document.setCategory(categoryService.getCategory(category.getId()).orElse(null));
        }

        documentService.updateDocument(document);

        return "redirect:/documents";
    }

    // Delete
    @PostMapping("/{id}/delete")
    public String deleteDocument(@PathVariable("id") Long id) {
        Optional<Document> document = documentService.getDocument(id);
        if (document.isEmpty()) {
            // Handle document not found
            return "error"; // Add a proper error page
        }
        documentService.deleteDocument(id);
        return "redirect:/documents";
    }

    // Search
    @GetMapping("/search")
    public String searchDocuments(@RequestParam("keywords") String keywords, Model model) {
        List<Document> searchResults = documentService.searchDocumentsByKeywords(keywords);
        model.addAttribute("documents", searchResults);
        return "list"; // Assuming you have a "list.html" page to display the search results
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<org.springframework.core.io.Resource> viewDocument(@PathVariable("id") Long id) {
        Optional<Document> document = documentService.getDocument(id);
        if (document.isEmpty() || document.get().getFilePath() == null) {
            return ResponseEntity.notFound().build();
        }
    
        Path filePath = Paths.get(document.get().getFilePath());
        org.springframework.core.io.Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            logger.error("Error creating UrlResource for view", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    
        HttpHeaders headers = new HttpHeaders();
        String contentType;
        try {
            contentType = Files.probeContentType(filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename(resource.getFilename())
                .build());
    
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
    
}
