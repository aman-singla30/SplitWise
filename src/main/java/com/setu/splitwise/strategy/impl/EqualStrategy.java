package com.setu.splitwise.strategy.impl;

import com.setu.splitwise.dto.EqualSplit;
import com.setu.splitwise.dto.Split;
import com.setu.splitwise.model.User;
import com.setu.splitwise.strategy.ExpenseStrategy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author amankumar
 * Eaual Expenses Strategy
 */
public class EqualStrategy extends ExpenseStrategy {

    public EqualStrategy(double amount, User paidBy, String description, List<Split> splits) {
        super(amount, paidBy, description, splits);
        if(!validateSplits()) {
            //throw Exception
        }
    }

    @Override
    public boolean validateSplits() {
        List<Split> invalidSplits = getSplits().stream()
                .filter(split ->  !(split instanceof EqualSplit)).collect(Collectors.toList());
         return invalidSplits.isEmpty();
    }

    @Override
    public void updateSplitAmount() {
        int numberOfSplits = getSplits().size();
        double individualAmount = ((double) Math.round(getAmount()*100/numberOfSplits))/100.0;
        getSplits().forEach(split -> split.setAmount(individualAmount));
        getSplits().get(0).setAmount(individualAmount + (getAmount() - individualAmount*numberOfSplits));
    }
}
