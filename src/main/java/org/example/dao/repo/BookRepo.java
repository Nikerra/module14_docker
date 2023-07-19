package org.example.dao.repo;

import org.example.dao.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {
    Book findByTitleIgnoreCase(String title);
}
