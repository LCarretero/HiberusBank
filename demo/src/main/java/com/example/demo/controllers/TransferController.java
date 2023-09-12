package com.example.demo.controllers;

import com.example.demo.dto.TransferCreateDTO;
import com.example.demo.dto.TransferPostDTO;
import com.example.demo.services.interfaces.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transfer")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @PostMapping()
    public ResponseEntity<TransferPostDTO> pay(@RequestBody TransferCreateDTO transfer) throws IllegalAccessException,IllegalStateException   {
        try {
            TransferPostDTO result = transferService.makeTransfer(transfer);
            if (result == null) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            if (e.getClass().equals(IllegalStateException.class))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            if (e.getClass().equals(IllegalAccessException.class))
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        return null;
    }

    @GetMapping()
    public ResponseEntity<List<TransferPostDTO>> getAll() {
        return ResponseEntity.ok(transferService.getAll());
    }

    @GetMapping("/failed")
    public ResponseEntity<List<TransferPostDTO>> failedTransfers() {
        return ResponseEntity.ok(transferService.failedTransfers());
    }
}
