package com.setu.splitwise.model;


import com.setu.splitwise.enums.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Description is mandatory")
    @Column(name = "description")
    private String description;
    @Column(name = "amount")
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paid_by")
    private User paidBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paid_in_group")
    private Group paidInGroup;

    @Column(name = "transactionType")
    private ExpenseType transactionType;
    @ElementCollection
    @Column(name = "splits")
    private Map<Long,Integer> splits;
    @Column(name = "transactionDate")
    private LocalDate transactionDate;
}
