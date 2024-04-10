package com.example.dbclpm_be.respository;

import com.example.dbclpm_be.entity.Customer;
import com.example.dbclpm_be.entity.ResidentialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("SELECT c FROM Customer c " +
            "WHERE (:name IS NULL OR c.infoCommon.name LIKE %:name%) " +
            "AND (:baseName IS NULL OR c.base.name = :baseName) " +
            "AND (:residentialName IS NULL OR c.residentialType.name = :residentialName) " +
            "AND c.actived = false")
    List<Customer> findNonActiveCustomers(String name, String baseName, String residentialName);

    @Query("SELECT c FROM Customer c " +
            "WHERE (:name IS NULL OR c.infoCommon.name LIKE %:name%) " +
            "AND (:baseName IS NULL OR c.base.name = :baseName) " +
            "AND (:residentialName IS NULL OR c.residentialType.name = :residentialName) " +
            "AND (:ordinal_numbers is null or c.ordinal_numbers LIKE %:ordinal_numbers%) " +
            "AND c.actived = true")
    List<Customer> findCustomerByOrdinalNumbersAndNameAndBaseNameAndResidentialType(String name, String baseName, String residentialName,String ordinal_numbers);
}
