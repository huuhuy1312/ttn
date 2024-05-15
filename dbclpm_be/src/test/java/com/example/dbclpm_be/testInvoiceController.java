package com.example.dbclpm_be;

import com.example.dbclpm_be.controller.InvoiceController;
import com.example.dbclpm_be.entity.Invoice;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class testInvoiceController {
    @Autowired
    private InvoiceController invoiceController;
    @Test
    public void test_get_invoice_by_cus_id()
    {
        long cus_id =1;
        ResponseEntity<?> responseEntity= invoiceController.getAllInvoicesByCusId(1);
        List<Invoice> invoiceList = (List<Invoice>) responseEntity.getBody();
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(invoiceList.size(),2);
    }

}
