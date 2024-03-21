package com.book.book.dao.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.book.book.dao.entities.Author;


public interface AuthorRepository extends JpaRepository<Author, Long> {
     
}