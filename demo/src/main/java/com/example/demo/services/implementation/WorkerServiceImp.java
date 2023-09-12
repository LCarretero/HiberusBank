package com.example.demo.services.implementation;

import com.example.demo.dto.WorkerGetDTO;
import com.example.demo.dto.WorkerPostDTO;
import com.example.demo.entities.Worker;
import com.example.demo.repositories.PayrollRepository;
import com.example.demo.repositories.WorkerRepository;
import com.example.demo.services.interfaces.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerServiceImp implements WorkerService {
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private PayrollRepository payrollRepository;

    //region PUBLIC_METHODS
    @Override
    public WorkerPostDTO saveWorker(Worker worker) {
        Worker workerDb = getWorker(worker.getDni());
        if (workerDb != null) return null;
        String name = worker.getName();
        if (name.isEmpty() || name.isBlank()) return null;
        worker.setBalance(0.0);
        worker.setPayrolls(new ArrayList<>());
        worker.setTransfersReceived(new ArrayList<>());
        worker.setTransfersEmitted(new ArrayList<>());
        return new WorkerPostDTO(workerRepository.save(worker));
    }

    @Override
    public WorkerPostDTO deleteWorker(String id) {
        Worker fromDB = getWorker(id);
        if (fromDB != null) {
            workerRepository.delete(fromDB);
            return new WorkerPostDTO(fromDB);
        }
        return null;
    }

    @Override
    public WorkerPostDTO riseSalary(String id, double amount) {
        Worker fromDB = getWorker(id);
        if (fromDB != null) {
            fromDB.setSalary(fromDB.getSalary() + amount);
            workerRepository.save(fromDB);
            return new WorkerPostDTO(fromDB);
        }
        return null;
    }

    @Override
    public WorkerGetDTO workerInformation(String id) {
        return new WorkerGetDTO(getWorker(id));
    }

    public List<WorkerGetDTO> getAllWorkers(String pass) {
        if (!pass.equals("É0wyn")) return null;

        List<WorkerGetDTO> result = new ArrayList<>();
        List<Worker> workerList = workerRepository.findAll();

        for (Worker w : workerList)
            result.add(new WorkerGetDTO(w));

        return result;
    }
    //endregion

    private Worker getWorker(String id) {
        return workerRepository.findById(id).orElse(null);
    }
}
