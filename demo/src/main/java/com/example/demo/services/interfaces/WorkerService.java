package com.example.demo.services.interfaces;

import com.example.demo.dto.WorkerDTO;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.transferExceptions.TransferBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerConflictException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.exceptions.workerExceptions.WorkerUnauthorizedException;

import java.util.List;

public interface WorkerService {
    WorkerDTO saveWorker(Worker worker) throws WorkerConflictException, WorkerBadRequestException;

    void deleteWorker(String id);

    WorkerDTO riseSalary(String workerDNI, double amount) throws WorkerNotFoundException, WorkerBadRequestException, TransferBadRequestException;

    WorkerDTO workerInformation(String id) throws WorkerNotFoundException;

    List<WorkerDTO> getAllWorkers(String pass) throws WorkerUnauthorizedException;

}
