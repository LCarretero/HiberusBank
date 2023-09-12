package com.example.demo.controllers;

import com.example.demo.dto.PayrollDTO;
import com.example.demo.dto.PayrollPostDTO;
import com.example.demo.entities.Payroll;
import com.example.demo.services.interfaces.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/payroll")
public class PayrollController {
    @Autowired
    private PayrollService payrollService;

    @PostMapping("/{id}")
    public ResponseEntity<PayrollPostDTO> payWorker(@RequestHeader(value = "Authorization") String keyPass, @PathVariable String id) {
        try {
            PayrollPostDTO result = payrollService.pay(id, keyPass);
            if (result == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<PayrollDTO>> getPayrolls() {
        return ResponseEntity.ok(payrollService.getAll());
    }
}
