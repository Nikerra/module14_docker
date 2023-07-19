package org.example.service;

import org.example.dao.entity.Book;
import org.example.dao.repo.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepo repository;
    private final Book book;
    public BookService(BookRepo repository, Book book) {
        this.repository = repository;
        this.book = book;
    }


    public Book createOrUpdate(String title, String author, String lang, String category, boolean active){
        book.setTitle(title);
        book.setAuthor(author);
        book.setLanguage(lang);
        book.setCategory(category);
        book.setActive(active);
        return repository.save(book);
    }
    public Optional<Book> delete(Long id){
        Optional<Book> bookEntity = repository.findById(id);
        repository.deleteById(id);
        return bookEntity;
    }
    public Optional<Book> findBookTitle(String title){
        return Optional.ofNullable(repository.findByTitleIgnoreCase(title));
    }
    public List<Book> getBooksAll(){
        return repository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return (repository.findById(id));
    }
}
