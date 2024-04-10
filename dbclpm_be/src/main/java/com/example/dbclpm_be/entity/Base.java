package com.example.dbclpm_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(name = "ordinal_numbers")
    private String ordinalNumbers;
    private long num_of_customer;
    @OneToOne
    @JoinColumn(name = "address_id")
    @JsonIgnore
    private Address address;

    @OneToMany(mappedBy = "base")
    @JsonBackReference
    private List<Customer> customerList;

    @OneToMany(mappedBy = "base")
    @JsonIgnore
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "base")
    @JsonIgnore
    private List<Formula> formulaList;
    public Base(String name, String ordinal_numbers, Address address) {
        this.name = name;
        this.ordinalNumbers = ordinal_numbers;
        this.address = address;
    }
}
