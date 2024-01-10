package com.springboot.expensetracker.service;

import com.springboot.expensetracker.entity.Expense;
import com.springboot.expensetracker.exception.ExpenseNotFoundException;
import com.springboot.expensetracker.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;


    public Expense createExpense(Expense expense){

        expense.setCreationDate(LocalDate.now());

        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses(){
        List<Expense> expenses = expenseRepository.findAll();

        return new ArrayList<>(expenses);
    }
    public Expense getExpenseById(long id) throws ExpenseNotFoundException {
        Optional<Expense> expense = expenseRepository.findById(id);

        if(!expense.isPresent()){
            throw new ExpenseNotFoundException("Expense does not exist");
        }
        return expense.get();
    }



}
