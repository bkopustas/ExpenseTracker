package com.springboot.expensetracker.controller;


import com.springboot.expensetracker.entity.Expense;
import com.springboot.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/expenses")
public class ExpenseController {
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense){
        return new ResponseEntity<>(expenseService.createExpense(expense), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllExpenses(){
        List<Expense> expenses = expenseService.getAllExpenses();

        String totalSum = String.format("%.2f", expenseService.getTotalExpenseAmount(Optional.empty(), Optional.empty()));

        Map<String, Object> response = new HashMap<>();
        response.put("expenses", expenses);
        response.put("totalAmount", totalSum);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @GetMapping("/name/{expenseName}")
    public List<Expense> getExpenseByName(@PathVariable(name = "expenseName") String expenseName){
        return expenseService.getExpensesByName(expenseName);
    }

    @GetMapping("/dateRange")
    public ResponseEntity<Map<String, Object>> getExpensesBetweenDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        List<Expense> expenses = expenseService.getExpenseBetweenDates(fromDate, toDate);

        String totalSum = String.format("%.2f", expenseService.getTotalExpenseAmount(Optional.of(fromDate), Optional.of(toDate)));

        Map<String, Object> response = new HashMap<>();
        response.put("expenses", expenses);
        response.put("totalAmount", totalSum);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/price")
    public List<Expense> getExpensesBetweenAmounts(@RequestParam double minAmount,
                                                   @RequestParam double maxAmount){
        return expenseService.getExpenseBetweenAmounts(minAmount, maxAmount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        expenseService.deleteExpense(id);
        return new ResponseEntity<>("Expense deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable(name = "id") long id,
                                                 @RequestBody Expense updatedExpense){
        Expense updated = expenseService.updateExpense(updatedExpense, id);
        return ResponseEntity.ok(updated);
    }

}
