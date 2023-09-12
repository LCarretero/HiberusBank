package com.example.demo.controllers;

import com.example.demo.dto.WorkerGetDTO;
import com.example.demo.dto.WorkerPostDTO;
import com.example.demo.entities.Worker;
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

        WorkerGetDTO result = workerService.workerInformation(id);

        if (result == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkerGetDTO>> getAllWorkers(@RequestHeader(value = "Authorization") String pass) {
        List<WorkerGetDTO> result = workerService.getAllWorkers(pass);
        if (result == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(result);
    }

    @PostMapping()
    public ResponseEntity<WorkerPostDTO> postWorker(@RequestBody Worker worker) {
        WorkerPostDTO result = workerService.saveWorker(worker);
        if (result == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/rise/{id}")
    public ResponseEntity<WorkerPostDTO> riseSalary(@PathVariable(name = "id") String id, @RequestParam(name = "amount") String amount) {
        WorkerPostDTO result = workerService.riseSalary(id, Double.parseDouble(amount));
        if (result == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<WorkerPostDTO> deleteWorker(@PathVariable(name = "id") String id) {
        WorkerPostDTO result = workerService.deleteWorker(id);
        if (result == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }
}
