package com.book.book.dao.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.book.book.dao.entities.Document;

public interface DocumentRepository  extends JpaRepository<Document, Long>  {
     Optional<Document> findById(Long Id);
     List<Document> findByCategoryId(Long categoryId);
     List<Document> findByAuthorId(Long authoId);
     List<Document> findByKeywordsContaining(String keywords);


   

}