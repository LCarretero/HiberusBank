package com.example.demo.controllers;

import com.example.demo.dto.TransferDTO;
import com.example.demo.exceptions.transferExceptions.TransferUnauthorizedException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.services.interfaces.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transfer")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @PostMapping()
    public ResponseEntity<TransferDTO> pay(@RequestBody TransferDTO transfer) {
        try {
            return ResponseEntity.ok(transferService.makeTransfer(transfer));
        } catch (WorkerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (TransferUnauthorizedException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping()
    public ResponseEntity<List<TransferDTO>> getAll() {
        return ResponseEntity.ok(transferService.getAll());
    }

    @GetMapping("/failed")
    public ResponseEntity<List<TransferDTO>> failedTransfers() {
        return ResponseEntity.ok(transferService.failedTransfers());
    }
}
