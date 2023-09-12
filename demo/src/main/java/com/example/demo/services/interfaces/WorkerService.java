package com.example.demo.services.interfaces;

import com.example.demo.dto.WorkerGetDTO;
import com.example.demo.dto.WorkerPostDTO;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.workerExceptions.WorkerBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerConflictException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;

import java.util.List;

public interface WorkerService {
    WorkerPostDTO saveWorker(Worker worker) throws WorkerConflictException, WorkerBadRequestException;
    void deleteWorker(String id);
    WorkerPostDTO riseSalary(String workerDNI, double amount) throws WorkerNotFoundException, WorkerBadRequestException;
    WorkerGetDTO workerInformation(String id) throws WorkerNotFoundException;
    List<WorkerGetDTO> getAllWorkers(String pass);

}
