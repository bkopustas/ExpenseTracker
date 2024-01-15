package com.springboot.expensetracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExpenseNotFoundException extends RuntimeException{

    public ExpenseNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
