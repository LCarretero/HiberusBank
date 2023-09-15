package com.example.demo.services.implementation;

import com.example.demo.dto.PayrollDTO;
import com.example.demo.entities.Payroll;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.hiberusBankExcpetions.hiberusBankException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.mapper.PayrollMapper;
import com.example.demo.repositories.PayrollRepository;
import com.example.demo.repositories.WorkerRepository;
import com.example.demo.services.interfaces.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class PayrollServiceImp implements PayrollService {
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private PayrollRepository payrollRepository;
    @Value(value = "${keypass}")
    private String KEYPASS;

    @Override
    public List<PayrollDTO> getAll() {
        List<Payroll> payrollList = payrollRepository.findAll();

        return payrollList.stream().map(PayrollMapper.INSTANCE::mapToDTO).toList();
    }

    @Override
    @Transactional
    public PayrollDTO pay(String id, String keyPass) throws hiberusBankException, WorkerNotFoundException {
        if (!KEYPASS.equals(keyPass)) throw new hiberusBankException();
        Worker worker = workerRepository.findById(id).orElse(null);
        if (worker == null) throw new WorkerNotFoundException("The worker does not exist");

        double amountWithTaxes = worker.getSalary() - (worker.getSalary() * 0.0525);
        worker.setBalance(amountWithTaxes);

        Payroll payroll = new Payroll();
        payroll.setAmount(amountWithTaxes);
        payroll.setDate(Instant.now().toString());
        payroll.setWorker(id);

        worker.getPayrolls().add(String.valueOf(payroll.getId()));
        payrollRepository.save(payroll);
        workerRepository.save(worker);
        return PayrollMapper.INSTANCE.mapToDTO(payroll);
    }

}
