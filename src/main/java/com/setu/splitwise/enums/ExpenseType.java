package com.setu.splitwise.enums;

/**
 * @author amankumar
 * ExpenseType : Equal and Percent supported as of now
 */
public enum ExpenseType {

    EQUAL("Equal"),
    PERCENT("Percent");

    private final String description;

    ExpenseType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
