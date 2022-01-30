package com.setu.splitwise.strategy;

import com.setu.splitwise.dto.Split;
import com.setu.splitwise.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public abstract class ExpenseStrategy {

    private double amount;
    private User paidBy;
    private String description;
    private List<Split> splits;
    private LocalDate paidDate;

    public ExpenseStrategy(double amount, User paidBy, String description, List<Split> splits) {
        this.amount = amount;
        this.paidBy = paidBy;
        this.description = description;
        this.splits = splits;
        this.paidDate = LocalDate.now();
    }

    public abstract boolean validateSplits();

    public abstract void updateSplitAmount();

}
