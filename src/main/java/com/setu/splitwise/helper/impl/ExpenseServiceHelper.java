package com.setu.splitwise.helper.impl;

import com.setu.splitwise.dto.EqualSplit;
import com.setu.splitwise.dto.PercentSplit;
import com.setu.splitwise.dto.Split;
import com.setu.splitwise.enums.ExpenseType;
import com.setu.splitwise.helper.IExpenseServiceHelper;
import com.setu.splitwise.model.User;
import com.setu.splitwise.service.IUserService;
import com.setu.splitwise.strategy.ExpenseStrategy;
import com.setu.splitwise.strategy.impl.EqualStrategy;
import com.setu.splitwise.strategy.impl.PercentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseServiceHelper implements IExpenseServiceHelper {

    @Autowired
    private IUserService userService;

    @Override
    public ExpenseStrategy calculateExpense(double amount, User paidBy, ExpenseType expenseType, List<Split> splits, String description) {
        if(expenseType == ExpenseType.EQUAL) {
            ExpenseStrategy expenseStrategy = new EqualStrategy(amount,paidBy,description,splits);
            expenseStrategy.updateSplitAmount();
            return expenseStrategy;
        } else if(expenseType == ExpenseType.PERCENT) {
            ExpenseStrategy expenseStrategy = new PercentStrategy(amount,paidBy,description, splits);
            expenseStrategy.updateSplitAmount();
            return expenseStrategy;
        } else {
            //throw exception : Expense type not supported as of now
            return  null;
        }

    }

    @Override
    public List<Split> getAllSplits(Map<Long, Integer> splits, ExpenseType expenseType) {
        List<Split> splitsInfo = new ArrayList<>();
        if(!CollectionUtils.isEmpty(splits)) {
            splits.entrySet().forEach(entry -> {
                User user = userService.getUser(entry.getKey());
                if(expenseType == ExpenseType.EQUAL) {
                    splitsInfo.add(new EqualSplit(user));
                } else if(expenseType == ExpenseType.PERCENT) {
                    splitsInfo.add(new PercentSplit(user, entry.getValue()));
                }
            });
            return splitsInfo;
        }

        return splitsInfo;
    }
}
