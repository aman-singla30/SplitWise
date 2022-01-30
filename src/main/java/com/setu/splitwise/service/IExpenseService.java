package com.setu.splitwise.service;

import com.setu.splitwise.dto.ExpenseRequestDto;
import com.setu.splitwise.dto.ExpenseResponseDto;
import com.setu.splitwise.dto.TotalExpenseRequestDto;
import com.setu.splitwise.dto.TotalExpenseResponseDto;

/**
 * @author amankumar
 * Expense is managed under this service
 */
public interface IExpenseService {

    /**
     * Expense made by user in particular group is added
     * @param expenseRequestDto
     * @return added Expense
     * @throws com.setu.splitwise.exceptions.UserNotFoundException
     */
    ExpenseResponseDto addExpense(ExpenseRequestDto expenseRequestDto);

    /**
     *
     * @param totalExpenseRequestDto
     * @return All expense detail for a particular user in group
     * @throws com.setu.splitwise.exceptions.UserNotFoundException
     * @throws com.setu.splitwise.exceptions.GroupNotFoundException
     */
    TotalExpenseResponseDto calculateTotalExpense(TotalExpenseRequestDto totalExpenseRequestDto);
}
