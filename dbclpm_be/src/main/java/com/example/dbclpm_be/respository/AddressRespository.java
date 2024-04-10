package com.example.dbclpm_be.respository;

import com.example.dbclpm_be.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRespository  extends JpaRepository<Address,Long> {

}
