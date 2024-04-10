package com.example.dbclpm_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class InfoCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String phoneNumber;
    private String name;
    @OneToOne(mappedBy = "infoCommon")
    @JsonBackReference
    private Customer customer;
    @OneToOne(mappedBy = "infoCommon")
    @JsonBackReference
    private Employee employee;

    public InfoCommon(String email, String phoneNumber, String name) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }
}
