package com.setu.splitwise.controller;


import com.setu.splitwise.dto.ExpenseRequestDto;
import com.setu.splitwise.dto.ExpenseResponseDto;
import com.setu.splitwise.dto.TotalExpenseRequestDto;
import com.setu.splitwise.dto.TotalExpenseResponseDto;
import com.setu.splitwise.service.IExpenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author amankumar
 */
@Api(value = "Expense Controller")
@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private IExpenseService expenseService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @ApiOperation(value = "Add new Expense", notes = "Add new Expense")
    @PostMapping("/add")
    public ResponseEntity<ExpenseResponseDto> addExpense(@RequestBody ExpenseRequestDto expenseRequestDto) {

        return new ResponseEntity<>(expenseService.addExpense(expenseRequestDto), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @ApiOperation(value = "Get Total Expense of User in Group", notes = "Get Total Expense of User in Group")
    @PostMapping("/getTotalExpense")
    public ResponseEntity<TotalExpenseResponseDto> getTotalExpense(@RequestBody TotalExpenseRequestDto totalExpenseRequestDto) {

        return new ResponseEntity<>(expenseService.calculateTotalExpense(totalExpenseRequestDto), HttpStatus.OK);
    }
}
