package com.example.demo2.controller;

import com.example.demo2.model.Book;
import com.example.demo2.repository.BookRepository;
import com.example.demo2.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/library/v1")
public class BookController {

    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //GET
    @Transactional(readOnly = true)
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllAccounts() {

        return new ResponseEntity<List<Book>>(bookRepository.findAll(), HttpStatus.OK);
    }
    //POST
    @Transactional
    @PostMapping("/books")
    public ResponseEntity<Book> createAccount(@RequestBody Book book) {
        existsBookByTitleOrEditorial(book);
        validateBook(book);
        return new ResponseEntity<Book>(bookRepository.save(book), HttpStatus.CREATED);
    }
    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new ValidationException("El titulo del libro debe ser obligatorio");
        }
        if (book.getTitle().length() > 22) {
            throw new ValidationException("El titulo del libro no debe exceder los 22 caracteres");
        }
        if (book.getEditorial() == null || book.getEditorial().trim().isEmpty()) {
            throw new ValidationException("La editorial del libro debe ser obligatorio");
        }
        if (book.getEditorial().length() > 14) {
            throw new ValidationException("La editorial del libro  debe tener una longitud de 14 caracteres");
        }

    }
    private void existsBookByTitleOrEditorial(Book book) {
        if (bookRepository.existsByTitle(book.getTitle()) && bookRepository.existsByEditorial(book.getEditorial())) {
            throw new ValidationException("No se puede registrar el libro porque existe uno con el mismo título y editorial");
        }
    }
}
