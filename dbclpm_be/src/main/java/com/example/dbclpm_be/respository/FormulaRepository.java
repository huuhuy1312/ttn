package com.example.dbclpm_be.respository;

import com.example.dbclpm_be.entity.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FormulaRepository extends JpaRepository<Formula,Long> {
    @Query("SELECT f FROM Formula f WHERE f.base.id =:base_id and f.actived=true and f.residentialType.id=:residential_id")
    Formula findByBaseIdAndResidentialTypeIdAndActived(long base_id, long residential_id);
}
