package com.example.dbclpm_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String detailsAddress;
    //Tỉnh
    private String provinceOrCity;
    //Huyện

    private String district;
    //Xã

    private String wards;
    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Base base;
    @OneToOne(mappedBy = "address")
    @JsonBackReference
    private Customer customer;
    @OneToOne(mappedBy = "address")
    @JsonBackReference
    private Employee employee;
    public Address(String detailsAddress, String provinceOrCity, String district, String wards) {
        this.detailsAddress = detailsAddress;
        this.provinceOrCity = provinceOrCity;
        this.district = district;
        this.wards = wards;
    }
}
