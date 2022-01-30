package com.setu.splitwise.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;
    @NotBlank(message = "Group name is mandatory")
    @Column(name = "name")
    private String name;
    @NotBlank(message = "Group Type is mandatory")
    @Column(name = "type")
    private String type;
    @Column(name = "creationDate")
    private LocalDate creationDate;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinTable(name = "user_group_mapping",
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"))
    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "group")
    private Set<Settlement> settlements = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "paidInGroup")
    private Set<Transaction> transactions = new HashSet<>();
}
