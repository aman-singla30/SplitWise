package com.setu.splitwise.dto;

import com.setu.splitwise.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EqualSplit extends Split {

    public EqualSplit(User user) {
        super(user);
    }
}
