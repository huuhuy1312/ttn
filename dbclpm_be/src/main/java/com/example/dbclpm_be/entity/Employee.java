package com.example.dbclpm_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "address_id")
    @JsonManagedReference
    private Address address;

    @OneToOne
    @JoinColumn(name = "info_common_id")
    @JsonManagedReference
    private InfoCommon infoCommon;

    @ManyToOne
    @JoinColumn(name = "base_id")
    @JsonIgnore
    private Base base;

    @OneToOne(mappedBy = "employee")
    @JsonBackReference
    private User user;
}
