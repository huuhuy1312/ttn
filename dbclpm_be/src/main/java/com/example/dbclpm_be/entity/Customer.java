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
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String ordinal_numbers;
    private long waterIndex;
    @ManyToOne
    @JoinColumn(name = "base_id")
    @JsonManagedReference
    private Base base;
    @OneToOne
    @JoinColumn(name = "info_common_id")
    @JsonManagedReference
    private InfoCommon infoCommon;

    @OneToOne(mappedBy = "customer")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "residential_type_id")
    @JsonManagedReference
    private ResidentialType residentialType;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Invoice> invoiceList;
    @OneToOne
    @JoinColumn(name = "address_id")
    @JsonManagedReference
    private Address address;
    private boolean actived;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String front_image;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String back_image;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String certificate_of_poverty;
    public Customer( Base base, ResidentialType residentialType,InfoCommon infoCommon) {
        this.base = base;
        this.residentialType = residentialType;
        this.infoCommon = infoCommon;
    }

    public Customer(Base base, InfoCommon infoCommon, ResidentialType residentialType, Address address, boolean actived, String front_image, String back_image, String certificate_of_poverty) {
        this.base = base;
        this.infoCommon = infoCommon;
        this.residentialType = residentialType;
        this.address = address;
        this.actived = actived;
        this.front_image = front_image;
        this.back_image = back_image;
        this.certificate_of_poverty = certificate_of_poverty;
    }
}
