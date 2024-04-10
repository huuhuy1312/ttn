package com.example.dbclpm_be.controller;

import com.example.dbclpm_be.entity.ResidentialType;
import com.example.dbclpm_be.respository.ResidentialTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/residentialType")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ResidentialTypeController {
    @Autowired
    private ResidentialTypeRepository residentialTypeRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addResidentialType(@RequestParam String nameResidentialType)
    {
        ResidentialType residentialType = new ResidentialType(nameResidentialType);
        residentialTypeRepository.save(residentialType);
        return ResponseEntity.ok("Thêm hộ dân cư thành công");
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllResidentialType (){
        List<ResidentialType> residentialTypeList = new ArrayList<>();
        residentialTypeList= residentialTypeRepository.findAll();
        return ResponseEntity.ok(residentialTypeList);
    }
}
