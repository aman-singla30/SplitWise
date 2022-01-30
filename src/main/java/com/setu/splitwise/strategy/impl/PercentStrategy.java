package com.setu.splitwise.strategy.impl;

import com.setu.splitwise.dto.PercentSplit;
import com.setu.splitwise.dto.Split;
import com.setu.splitwise.model.User;
import com.setu.splitwise.strategy.ExpenseStrategy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author amankumar
 * Percent Expenses Strategy
 */
public class PercentStrategy extends ExpenseStrategy {


    public PercentStrategy(double amount, User paidBy, String description, List<Split> splits) {
        super(amount, paidBy, description, splits);
        if(!validateSplits()) {
            //throw Exception
        }
    }

    @Override
    public boolean validateSplits() {

        List<Split> invalidSplits = getSplits().stream()
                .filter(split ->  !(split instanceof PercentSplit)).collect(Collectors.toList());
        if(!invalidSplits.isEmpty()) {
            return false;
        } else {
            double totalPercentage = 100;
            double sumOfSplitPercentage =0;

            for (Split split : getSplits()) {
                PercentSplit percentSplit = (PercentSplit) split;
                sumOfSplitPercentage  = sumOfSplitPercentage + percentSplit.getPercent();
            }
            if(sumOfSplitPercentage != totalPercentage) {
                return false;
            }
           return true;
        }
    }

    @Override
    public void updateSplitAmount() {
        getSplits().forEach(split -> {
            PercentSplit percentSplit = (PercentSplit) split;
            split.setAmount((getAmount()* percentSplit.getPercent())/100);
        });
    }
}
