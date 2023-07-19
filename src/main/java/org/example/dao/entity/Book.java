package org.example.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "book")
@JsonPOJOBuilder
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotEmpty(message = "Title should not be empty")
    @Column(name = "title")
    private String title;


    @NotEmpty(message = "Author should not be empty")
    @Column(name = "author")
    private String author;

    @NotEmpty(message = "Language should not be empty")
    @Column(name = "language")
    private String language;

    @NotEmpty(message = "category should not be empty")
    @Column(name = "category")
    private String category;

    @Column(name = "is_active")
    @JsonIgnore
    private boolean isActive;
    public Book(){}

    public Book(String title, String author, String language, String category) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.category = category;
    }
}

