package com.example.demo.services.implementation;

import com.example.demo.dto.WorkerGetDTO;
import com.example.demo.dto.WorkerPostDTO;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.transferExceptions.TransferBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerConflictException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.exceptions.workerExceptions.WorkerUnauthorizedException;
import com.example.demo.repositories.WorkerRepository;
import com.example.demo.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerServiceImp implements WorkerService {
    @Autowired
    private WorkerRepository workerRepository;
    private static final String PASSWORD = "É0wyn";

    //region PUBLIC_METHODS
    @Override
    public WorkerPostDTO saveWorker(Worker worker) throws WorkerConflictException, WorkerBadRequestException {
        Worker workerDb = getWorker(worker.getDni());
        if (workerDb != null) throw new WorkerConflictException("The worker already exist");

        String dni = worker.getDni();
        if (dni.length() != 9 || !Character.isLetter(dni.charAt(dni.length() - 1)))
            throw new WorkerBadRequestException("Dni bad formated");
        String name = worker.getName();
        if (name.isEmpty() || name.isBlank())
            throw new WorkerBadRequestException("The name is not correct");

        Worker workerForDb = Worker.builder().balance(0.0).
                dni(worker.getDni()).
                name(worker.getName()).
                lastName(worker.getLastName()).
                salary(worker.getSalary()).build();

        return new WorkerPostDTO(workerRepository.save(workerForDb));
    }

    @Override
    public void deleteWorker(String id) {
        Worker fromDB = getWorker(id);
        if (fromDB == null) return;
        workerRepository.delete(fromDB);
    }

    @Override
    public WorkerPostDTO riseSalary(String id, double amount) throws WorkerNotFoundException, TransferBadRequestException {
        Worker fromDB = getWorker(id);
        if (fromDB == null) throw new WorkerNotFoundException("The worker is not found");
        if (amount < 0) throw new TransferBadRequestException("The amount must be a valid number");
        fromDB.setSalary(fromDB.getSalary() + amount);
        workerRepository.save(fromDB);
        return new WorkerPostDTO(fromDB);
    }

    @Override
    public WorkerGetDTO workerInformation(String id) throws WorkerNotFoundException {
        Worker result = getWorker(id);
        if (result == null) throw new WorkerNotFoundException("The provided worker does not exist");
        return new WorkerGetDTO(result);
    }

    public List<WorkerGetDTO> getAllWorkers(String pass) throws WorkerUnauthorizedException {
        if (!PASSWORD.equals(pass)) throw new WorkerUnauthorizedException("Unauthorized");
        return workerRepository.findAll().stream().map(WorkerGetDTO::new).collect(Collectors.toList());
    }
    //endregion

    private Worker getWorker(String id) {
        return workerRepository.findById(id).orElse(null);
    }
}
