package com.example.demo.controllers;

import com.example.demo.dto.WorkerGetDTO;
import com.example.demo.dto.WorkerPostDTO;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.transferExceptions.TransferBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerConflictException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.exceptions.workerExceptions.WorkerUnauthorizedException;
import com.example.demo.services.implementation.WorkerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/worker")
public class WorkerController {
    @Autowired
    private WorkerServiceImp workerService;

    @GetMapping("/{id}")
    public ResponseEntity<WorkerGetDTO> getWorker(@PathVariable(name = "id") String id) {
        try {
            return ResponseEntity.ok(workerService.workerInformation(id));
        } catch (WorkerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkerGetDTO>> getAllWorkers(@RequestHeader(value = "Authorization") String pass) {
        try {
            List<WorkerGetDTO> result = workerService.getAllWorkers(pass);
            return ResponseEntity.ok(result);
        } catch (WorkerUnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping()
    public ResponseEntity<WorkerPostDTO> postWorker(@RequestBody Worker worker) {
        try {
            WorkerPostDTO result = workerService.saveWorker(worker);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (WorkerConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (WorkerBadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/rise/{id}")
    public ResponseEntity<WorkerPostDTO> riseSalary(@PathVariable(name = "id") String id, @RequestParam(name = "amount") String amount) {
        try {
            return ResponseEntity.ok(workerService.riseSalary(id, Double.parseDouble(amount)));
        } catch (WorkerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (TransferBadRequestException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteWorker(@PathVariable(name = "id") String id) {
        workerService.deleteWorker(id);
    }
}
