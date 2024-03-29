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
        if(expense.getCreationDate() == null) {
            expense.setCreationDate(LocalDate.now());
        }

        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses(){
        List<Expense> expenses = expenseRepository.findAll();

        return new ArrayList<>(expenses);
    }
    public Expense getExpenseById(long id) throws ExpenseNotFoundException {

        return expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException("Expense does not exist"));
    }
    public List<Expense> getExpensesByName(String expenseName) throws ExpenseNotFoundException{
        List<Expense> expenses = expenseRepository.findByExpenseName(expenseName);

        if(expenses.isEmpty()){
            throw new ExpenseNotFoundException("There are no existing expenses by this name");
        }
        return new ArrayList<>(expenses);
    }
    public List<Expense> getExpenseBetweenDates(LocalDate fromDate, LocalDate toDate) throws ExpenseNotFoundException{
        List<Expense> expenses = expenseRepository.findByCreationDateBetween(fromDate, toDate);

        if(expenses.isEmpty()){
            throw new ExpenseNotFoundException("No expenses found within the specified date range");
        }
        return new ArrayList<>(expenses);
    }

    public List<Expense> getExpenseBetweenAmounts(double minAmount, double maxAmount) throws ExpenseNotFoundException{
        List<Expense> expenses = expenseRepository.findByExpenseAmountBetween(minAmount, maxAmount);

        if(expenses.isEmpty()){
            throw new ExpenseNotFoundException("No expenses found within the specified price range");
        }
        return new ArrayList<>(expenses);
    }

    public void deleteExpense(long id){
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException("Expense does not exist"));

        expenseRepository.delete(expense);
    }

    public Expense updateExpense(Expense updatedExpense, long id){
        Expense existingExpense = expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException("Expense does not exist"));

        existingExpense.setExpenseName(updatedExpense.getExpenseName());
        existingExpense.setExpenseAmount(updatedExpense.getExpenseAmount());

        LocalDate updatedCreationDate = updatedExpense.getCreationDate();
        if(updatedCreationDate != null){
            existingExpense.setCreationDate(updatedCreationDate);
        }

        return expenseRepository.save(existingExpense);
    }

    public double getTotalExpenseAmount(Optional<LocalDate> fromDate, Optional<LocalDate> toDate){

        List<Expense> expenses;

        if(fromDate.isPresent() && toDate.isPresent()){
            expenses = expenseRepository.findByCreationDateBetween(fromDate.get(), toDate.get());
        }
        else{
            expenses = expenseRepository.findAll();
        }

        if(expenses.isEmpty()){
            return 0.0;
        }
        return expenses.stream().mapToDouble(Expense::getExpenseAmount).sum();
    }




}
