package org.example.dao.repo;

import org.example.dao.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends JpaRepository<Book,Long> {

    Book findByTitleIgnoreCase(String title);

    List<Book> findByCategoryIgnoreCaseAndIsActiveTrue(String category);
    //    @Query("select b1_0 from Book b1_0 where b1_0.isActive = true and b1_0.category = :categoryName")
    List<Book> findByCategory(@Param("categoryName") String category);

}
