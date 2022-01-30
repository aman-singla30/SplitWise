package com.setu.splitwise.dto;

import com.setu.splitwise.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PercentSplit extends Split {

    private int percent;

    public PercentSplit(User user, int percent) {
        super(user);
        this.percent = percent;
    }

}
