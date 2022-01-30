package com.setu.splitwise.dto;

import com.setu.splitwise.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Split {

    private User user;
    protected double amount;

    public Split(User user) {
        this.user = user;
    }

}
