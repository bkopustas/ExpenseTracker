package com.springboot.expensetracker.repository;

import com.springboot.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByExpenseName(String expenseName);

    List<Expense> findByCreationDateBetween(LocalDate fromDate, LocalDate toDate);

    List<Expense> findByExpenseAmountBetween(double minAmount, double maxAmount);
}
