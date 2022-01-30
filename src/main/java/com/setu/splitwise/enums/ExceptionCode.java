package com.setu.splitwise.enums;

/**
 * @author amankumar
 * Exception Code declaration
 */
public enum ExceptionCode {

    U01("User not found"),
    U02("Request processing error"),
    G01("Group not found");

    private final String description;

    ExceptionCode(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }
}
