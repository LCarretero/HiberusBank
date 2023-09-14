package com.example.demo.controllers;

import com.example.demo.dto.TransferCreateDTO;
import com.example.demo.dto.TransferPostDTO;
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
    public ResponseEntity<TransferPostDTO> pay(@RequestBody TransferCreateDTO transfer){
        try {
            TransferPostDTO result = transferService.makeTransfer(transfer);
            if (result == null) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(result);
        } catch (WorkerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (TransferUnauthorizedException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
