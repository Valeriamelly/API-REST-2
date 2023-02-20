package com.example.demo2.repository;

import com.example.demo2.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByCodeStudentAndBook_Id(String studentCode, Long bookId);
    //Listado de libros por editorial
    @Query("SELECT l FROM Loan l WHERE l.book.editorial=:editorial")
    List<Loan> findLoanByEditorial(@Param("editorial")String editorial);



}
