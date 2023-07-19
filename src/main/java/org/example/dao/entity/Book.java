package org.example.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private boolean isActive;
    public Book(){}

}

