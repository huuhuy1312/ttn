package com.example.dbclpm_be.controller;

import com.example.dbclpm_be.entity.*;
import com.example.dbclpm_be.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import payload.request.AddCustomerRequest;
import payload.request.FilterCustomerRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/customer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BaseRespository baseRespository;

    @Autowired
    private ResidentialTypeRepository residentialTypeRepository;
    @Autowired
    private AddressRespository addressRespository;
    @Autowired
    private InfoCommonRespository infoCommonRespository;
    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody AddCustomerRequest addCustomerRequest)
    {
        Optional<Base> optionalBase = baseRespository.findById(addCustomerRequest.baseId);
        Base base = optionalBase.orElseThrow(()-> new RuntimeException("Không tìm thấy base có id="+addCustomerRequest.baseId));
        Optional<ResidentialType> residentialTypeOptional = residentialTypeRepository.findById(addCustomerRequest.residentialTypeId);
        ResidentialType residentialType = residentialTypeOptional.orElseThrow(()-> new RuntimeException("Không tìm thấy hộ dân cư có id="+ addCustomerRequest.residentialTypeId));
        InfoCommon infoCommon = new InfoCommon(addCustomerRequest.email,addCustomerRequest.phoneNumber,addCustomerRequest.name);
        infoCommonRespository.save(infoCommon);
        Address address = new Address(addCustomerRequest.detailsAddress,addCustomerRequest.provinceOrCity,addCustomerRequest.district,addCustomerRequest.wards);
        addressRespository.save(address);
        System.out.println(address);
        Customer customer = new Customer(base,infoCommon,residentialType,address,false,addCustomerRequest.front_image,addCustomerRequest.back_image,addCustomerRequest.certificate_of_poverty);
        customer.setWaterIndex(0L);
        customerRepository.save(customer);
        return ResponseEntity.ok("Thêm khách hàng thành công");
    }
    @GetMapping("/{cus_id}")
    public ResponseEntity<?> getCustomerByID(@PathVariable long cus_id)
    {
        Optional<Customer> customer = customerRepository.findById(cus_id);
        Customer customer1 = customer.orElseThrow(()-> new RuntimeException("Không tìm thấy customer có id="+cus_id));
        return ResponseEntity.ok(customer1);
    }

    @GetMapping("/getNonActivedCustomers")
    public ResponseEntity<?> getAllNonActiveCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String baseName,
            @RequestParam(required = false) String residentialName) {
        // Logic here
        System.out.println(name);
        System.out.println(baseName);
        System.out.println(residentialName);
        List<Customer> customerList = customerRepository.findNonActiveCustomers(name,baseName,residentialName);
        return ResponseEntity.ok(customerList);
    }
    @GetMapping("/getActivedCustomers")
    public ResponseEntity<?> getAllActiveCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String baseName,
            @RequestParam(required = false) String residentialName,
            @RequestParam(required = false) String ordinalNumbers) {
        // Logic here
        System.out.println(name);
        System.out.println(baseName);
        System.out.println(residentialName);
        List<Customer> customerList = customerRepository.findCustomerByOrdinalNumbersAndNameAndBaseNameAndResidentialType(name,baseName,residentialName,ordinalNumbers);
        return ResponseEntity.ok(customerList);
    }
    @PostMapping("/updateIndexWater")
    public ResponseEntity<?> updateIndexWater(@RequestParam long cus_id ,@RequestParam(required = true) long newIndexWater){
        Optional<Customer> customer = customerRepository.findById(cus_id);
        Customer customer1 = customer.orElseThrow(()->new RuntimeException("Khong tim thay customer co id="+cus_id));
        customer1.setWaterIndex(newIndexWater);
        customerRepository.save(customer1);
        return  ResponseEntity.ok("Cập nhật số nước thành công");


    }
}
