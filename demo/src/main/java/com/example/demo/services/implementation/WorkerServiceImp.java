package com.example.demo.services.implementation;

import com.example.demo.dto.WorkerDTO;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.transferExceptions.TransferBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerConflictException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.exceptions.workerExceptions.WorkerUnauthorizedException;
import com.example.demo.mapper.WorkerMapper;
import com.example.demo.repositories.PayrollRepository;
import com.example.demo.repositories.TransferRepository;
import com.example.demo.repositories.WorkerRepository;
import com.example.demo.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerServiceImp implements WorkerService {
    //region PRIVATE_FIELDS
    @Value(value = "${lore}")
    private String LORE;
    @Autowired
    private WorkerRepository workerRepository;
    private TransferRepository transferRepository;
    private PayrollRepository payrollRepository;

    //endregion

    //region PUBLIC_METHODS
    @Override
    public WorkerDTO saveWorker(Worker worker) throws WorkerConflictException, WorkerBadRequestException {
        Worker workerDb = getWorker(worker.getDni());
        if (workerDb != null) throw new WorkerConflictException("The worker already exist");

        String dni = worker.getDni();
        if (dni.length() != 9 || !Character.isLetter(dni.charAt(dni.length() - 1)))
            throw new WorkerBadRequestException("Dni bad formated");
        String name = worker.getName();
        if (name.isEmpty() || name.isBlank())
            throw new WorkerBadRequestException("The name is not correct");

        return WorkerMapper.INSTANCE.modelToDTO(workerRepository.save(worker));
    }

    @Override
    public void deleteWorker(String id) {
        Worker fromDB = getWorker(id);
        if (fromDB == null) return;
        workerRepository.delete(fromDB);
    }

    @Override
    public WorkerDTO riseSalary(String id, double amount) throws WorkerNotFoundException, TransferBadRequestException {
        Worker fromDB = getWorker(id);
        if (fromDB == null) throw new WorkerNotFoundException("The worker is not found");
        if (amount < 0) throw new TransferBadRequestException("The amount must be a valid number");
        fromDB.setSalary(fromDB.getSalary() + amount);
        workerRepository.save(fromDB);
        return WorkerMapper.INSTANCE.modelToDTO(fromDB);
    }

    @Override
    public WorkerDTO workerInformation(String id) throws WorkerNotFoundException {
        Worker result = getWorker(id);
        if (result == null) throw new WorkerNotFoundException("The provided worker does not exist");
        return WorkerMapper.INSTANCE.modelToDTO(result);
    }

    public List<WorkerDTO> getAllWorkers(String pass) throws WorkerUnauthorizedException {
        if (!LORE.equals(pass)) throw new WorkerUnauthorizedException("Unauthorized");
        return workerRepository.findAll().stream().map(WorkerMapper.INSTANCE::modelToDTO).collect(Collectors.toList());
    }
    //endregion

    //region PRIVATE_METHODS
    private Worker getWorker(String id) {
        return workerRepository.findById(id).orElse(null);
    }
    //endregion
}
