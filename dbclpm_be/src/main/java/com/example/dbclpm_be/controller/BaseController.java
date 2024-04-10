package com.example.dbclpm_be.controller;

import com.example.dbclpm_be.entity.Address;
import com.example.dbclpm_be.entity.Base;
import com.example.dbclpm_be.respository.AddressRespository;
import com.example.dbclpm_be.respository.BaseRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payload.request.AddBaseRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/base")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BaseController {
    @Autowired
    private BaseRespository baseRespository;
    @Autowired
    private AddressRespository addressRespository;
    @GetMapping("/all")
    public ResponseEntity<?> getAllBase()
    {
        List<Base> bases = baseRespository.findAll();
        return ResponseEntity.ok(bases);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addBase(@RequestBody AddBaseRequest addBaseRequest)
    {
        Optional<Address> optionalAddress = addressRespository.findById(addBaseRequest.address_id);
        Address address = optionalAddress.orElseThrow(()-> new RuntimeException("Không tìm thấy address có id="+addBaseRequest.address_id));

        Base base = new Base(addBaseRequest.name,addBaseRequest.ordinal_numbers,address);
        baseRespository.save(base);
        return ResponseEntity.ok("Thêm Base thành công");

    }
}
