package com.book.book.web.models;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.book.book.dao.entities.Author;
import com.book.book.dao.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DocumentForms {
    private Long id;
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "summary is required")
    private String summary;
    @NotBlank(message = "keywords is required")
    private String keywords;
    private LocalDate datePublication;
    private MultipartFile file;


    private Author author;
    private Category category;
}