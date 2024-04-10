package com.example.dbclpm_be.respository;

import com.example.dbclpm_be.entity.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseRespository extends JpaRepository<Base,Long> {
    Optional<Base> findByOrdinalNumbers(String o_n);
}
