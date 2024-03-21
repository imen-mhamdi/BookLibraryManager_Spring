package com.book.book.dao.entities;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", length = 30,nullable = true)
    private String title;
    @Column(name = "summary", length =200,nullable = true)
    private String summary;
    @Column(name = "keywords", length =60,nullable = true)
    private String keywords;
    @Column(name = "datePublication")
    private LocalDate datePublication;
    @Column(name = "file_path")
    private String filePath;
    
    


    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}