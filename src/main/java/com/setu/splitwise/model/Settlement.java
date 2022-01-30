package com.setu.splitwise.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "settlement")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groups_id")
    private Group group;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paidBy")
    private User paidBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paidTo")
    private User paidTo;

    @Column(name = "amount")
    private Double amount;
}
