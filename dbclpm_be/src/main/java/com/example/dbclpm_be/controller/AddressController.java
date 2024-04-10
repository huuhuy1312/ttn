package com.example.dbclpm_be.controller;

import com.example.dbclpm_be.entity.Address;
import com.example.dbclpm_be.respository.AddressRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payload.request.AddAddressRequest;

@RestController
@RequestMapping("api/address")
public class AddressController {
    @Autowired
    private AddressRespository addressRespository;

//    @PostMapping("/add")
//    public ResponseEntity<?> addAddress(@RequestBody AddAddressRequest addAddressRequest)
//    {
//        Address address = new Address(addAddressRequest.street,addAddressRequest.city,addAddressRequest.province,addAddressRequest.district,addAddressRequest.commune,addAddressRequest.state);
//        addressRespository.save(address);
//        return ResponseEntity.ok("Thêm địa chỉ thành công");
//    }

}
