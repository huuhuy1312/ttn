package com.example.dbclpm_be.respository;

import com.example.dbclpm_be.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> getInvoicesByCustomerId(long cus_id);
}
