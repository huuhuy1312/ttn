package com.example.dbclpm_be.respository;

import com.example.dbclpm_be.entity.ResidentialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentialTypeRepository extends JpaRepository<ResidentialType,Long> {

}
