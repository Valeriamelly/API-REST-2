package com.example.demo2.repository;
import com.example.demo2.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitle(String title);
    boolean existsByEditorial(String editorial);

}
