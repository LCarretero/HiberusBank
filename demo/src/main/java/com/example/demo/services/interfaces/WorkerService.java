package com.example.demo.services.interfaces;

import com.example.demo.dto.WorkerGetDTO;
import com.example.demo.dto.WorkerPostDTO;
import com.example.demo.entities.Worker;

import java.util.List;

public interface WorkerService {
    WorkerPostDTO saveWorker(Worker worker);
    WorkerPostDTO deleteWorker(String id);
    WorkerPostDTO riseSalary(String workerDNI, double amount);
    WorkerGetDTO workerInformation(String id);
    List<WorkerGetDTO> getAllWorkers(String pass);

}
