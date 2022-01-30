package com.setu.splitwise.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "name")
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Column(name = "phoneNumber")
    @NotBlank(message = "Phone Number is mandatory")
    private String phoneNumber;
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email")
    private String email;
    @Column(name = "creationDate")
    private LocalDate creationDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "users")
    @JsonBackReference
    private Set<Group> groups = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "paidBy")
    private Set<Settlement> paidBySettlement = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "paidTo")
    private Set<Settlement> paidToSettlement = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "paidBy")
    private Set<Transaction> transactions = new HashSet<>();



}

