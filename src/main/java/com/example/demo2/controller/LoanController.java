package com.example.demo2.controller;

import com.example.demo2.exception.ValidationException;
import com.example.demo2.model.Book;
import com.example.demo2.model.Loan;
import com.example.demo2.exception.ResourceNotFoundException;
import com.example.demo2.repository.BookRepository;
import com.example.demo2.repository.LoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/library/v1")
public class LoanController {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public LoanController(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }
    @Transactional
    @PostMapping("/books/{id}/loans")
    public ResponseEntity<Loan> createLoan(@PathVariable(value = "id") Long bookId,
                                                  @RequestBody Loan loan){
        //validaciones
        validateLoan(loan);
        loan.setDevolutionDate(LocalDate.now().plusDays(3));
        loan.setLoanDate(LocalDate.now());
        loan.setBookLoan(true);

        return new ResponseEntity<Loan>(loanRepository.save(loan), HttpStatus.CREATED);
    }
    @Transactional(readOnly = true)
    @GetMapping("/books/filterByEditorial")
    public ResponseEntity<List<Loan>> getAllLoanByEditorial(@RequestParam(value = "editorial") String editorial){
        List<Loan> loans;
        loans=loanRepository.findLoanByEditorial(editorial);

        return new ResponseEntity<List<Loan>>(loans,HttpStatus.OK);
    }
    private void validateLoan(Loan loan) {
        if (loan.getCodeStudent() == null || loan.getCodeStudent().trim().isEmpty()) {
            throw new ResourceNotFoundException("El código del alumno debe ser obligatorio");
        }
        if (loan.getCodeStudent().length() < 10) {
            throw new ResourceNotFoundException("El código del alumno debe tener 10 caracteres");
        }
        if (loanRepository.existsByCodeStudentAndBook_Id(loan.getCodeStudent(), loan.getBook().getId())) {
            throw new ValidationException("El préstamo del libro con código " + loan.getBook().getId()+ " no es posible porque ya fue prestado por el alumno " +loan.getCodeStudent());
        }
    }


}


