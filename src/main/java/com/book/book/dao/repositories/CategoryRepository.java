package com.book.book.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.book.book.dao.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
  
}