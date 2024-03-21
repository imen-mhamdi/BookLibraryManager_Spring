package com.book.book.business.service;
import java.util.List;
import java.util.Optional;
import com.book.book.dao.entities.Document;

public interface DocumentService {
      // Retrieves a product by ID.
      public Optional<Document> getDocument(Long id);
      // Adds a new product.
      public Document addDocument(Document document);
  
      // Updates an existing product.
      public Document updateDocument(Document document);
  
      // Deletes a product by their ID.
      public void deleteDocument(Long id);
  
      // Retrieves all products.
      public List<Document> getAllDocument();
  
      // Retrieves all products by category ID
      List<Document> getDocumentsByCategoryId(Long categoryId);
      List<Document> getDocumentsByAuthosId(Long authoId);
      // search
      List<Document> searchDocumentsByKeywords(String keywords);

}