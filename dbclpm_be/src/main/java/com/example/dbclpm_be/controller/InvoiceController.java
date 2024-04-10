package com.example.dbclpm_be.controller;

import com.example.dbclpm_be.entity.Customer;
import com.example.dbclpm_be.entity.Formula;
import com.example.dbclpm_be.entity.Invoice;
import com.example.dbclpm_be.respository.CustomerRepository;
import com.example.dbclpm_be.respository.FormulaRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dbclpm_be.respository.InvoiceRepository;
import payload.request.AddInvoiceRequest;
import payload.response.InvoiceDetailsResponse;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FormulaRepository formulaRepository;


    @GetMapping("/getByCustomer/{cus_id}")
    public ResponseEntity<?> getAllInvoicesByCusId(@PathVariable long cus_id)
    {
        List<Invoice> invoiceList = invoiceRepository.getInvoicesByCustomerId(cus_id);
        return  ResponseEntity.ok(invoiceList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable long id)
    {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById((id));
        Invoice invoice = invoiceOptional.orElseThrow(()->new RuntimeException("Không tìm thấy invoice có id="+id));
        InvoiceDetailsResponse invoiceDetailsResponse = new InvoiceDetailsResponse(invoice,invoice.getCustomer(),invoice.getFormula());
        return ResponseEntity.ok(invoiceDetailsResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addInvoice(@RequestBody AddInvoiceRequest addInvoiceRequest) throws ParseException {
        Customer customer = customerRepository.findById(addInvoiceRequest.customer_id).orElseThrow(()->new RuntimeException("khong tim thay customer"));
        Formula formula = formulaRepository.findByBaseIdAndResidentialTypeIdAndActived(customer.getBase().getId(),customer.getResidentialType().getId());
        long usedNumber= addInvoiceRequest.new_number - customer.getWaterIndex();
        int current=0;
        double totalPrice = 0;
        double[] pricePerThreshold = {
                formula.getPrice_smaller_or_equal_to_10m3(),
                formula.getPrice_from_10m3_to_20m3(),
                formula.getPrice_from_20m3_to_30m3(),
                formula.getPrice_greater_or_equal_30m3()
        };
        System.out.println("Thông tin của mảng pricePerThreshold:");
        for (int i = 0; i < pricePerThreshold.length; i++) {
            System.out.println("Threshold " + i + ": " + pricePerThreshold[i]);
        }
        while (usedNumber >0 && current<4)
        {
            double thresholdUsage = current<3 ? Math.min(10, usedNumber):usedNumber;
            totalPrice+= thresholdUsage* pricePerThreshold[current];
            usedNumber-=thresholdUsage;
            current++;
        }
        System.out.println(totalPrice);
        double totalTax = totalPrice*(formula.getBvmtTax()+formula.getVatTaxPer())/100;
        double totalAll = totalTax+totalPrice;
        Invoice invoice = new Invoice(addInvoiceRequest.new_number,customer.getWaterIndex(),"04/2024",(long) totalAll,(long) totalTax,(long) totalPrice,"Chưa thanh toán",customer,formula);
        invoiceRepository.save(invoice);
        customer.setWaterIndex(addInvoiceRequest.new_number);
        customerRepository.save(customer);
        return ResponseEntity.ok("Thành công");
    }
}
