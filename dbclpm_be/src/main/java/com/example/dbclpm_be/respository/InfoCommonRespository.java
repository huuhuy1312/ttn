package com.example.dbclpm_be.respository;

import com.example.dbclpm_be.entity.InfoCommon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoCommonRespository extends JpaRepository<InfoCommon,Long> {
}
