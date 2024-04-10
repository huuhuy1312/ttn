package com.example.dbclpm_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResidentialType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    private String  name;

    @OneToMany(mappedBy = "residentialType")
    @JsonBackReference
    private List<Customer> customerList;

    @OneToMany(mappedBy = "residentialType")
    @JsonIgnore
    private List<Formula> formulaList;
    public ResidentialType(String name) {
        this.name = name;
    }
}
