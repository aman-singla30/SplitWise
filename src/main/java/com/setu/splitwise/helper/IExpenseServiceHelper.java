package com.setu.splitwise.helper;

import com.setu.splitwise.dto.Split;
import com.setu.splitwise.enums.ExpenseType;
import com.setu.splitwise.model.User;
import com.setu.splitwise.strategy.ExpenseStrategy;

import java.util.List;
import java.util.Map;

public interface IExpenseServiceHelper {

    ExpenseStrategy calculateExpense(double amount, User paidBy, ExpenseType expenseType, List<Split> splits, String description);

    List<Split> getAllSplits(Map<Long, Integer> splits, ExpenseType expenseType);

}
