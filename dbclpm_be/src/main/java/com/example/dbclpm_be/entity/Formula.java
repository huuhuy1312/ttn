package com.example.dbclpm_be.entity;

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
public class Formula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double price_smaller_or_equal_to_10m3;

    private double price_from_10m3_to_20m3;

    private double price_from_20m3_to_30m3;

    private double price_greater_or_equal_30m3;

    private double vatTaxPer;
    private double bvmtTax;
    private boolean actived;
    @ManyToOne
    @JoinColumn(name="base_id")
    @JsonIgnore
    private Base base;

    @ManyToOne
    @JoinColumn(name="residential_type_id")
    private ResidentialType residentialType;
    @OneToMany(mappedBy = "formula")
    @JsonIgnore
    private List<Invoice> invoiceList;
}
