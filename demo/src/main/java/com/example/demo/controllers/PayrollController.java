package com.example.demo.controllers;

import com.example.demo.dto.PayrollDTO;
import com.example.demo.dto.PayrollPostDTO;
import com.example.demo.exceptions.hiberusBankExcpetions.hiberusBankException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.services.interfaces.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        } catch (WorkerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (hiberusBankException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<PayrollDTO>> getPayrolls() {
        return ResponseEntity.ok(payrollService.getAll());
    }
}
