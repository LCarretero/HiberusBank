package com.example.demo.services.interfaces;

import com.example.demo.dto.PayrollDTO;
import com.example.demo.dto.PayrollPostDTO;
import com.example.demo.entities.Payroll;
import com.example.demo.exceptions.hiberusBankExcpetions.hiberusBankException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;

import java.util.List;

public interface PayrollService {
    List<PayrollDTO> getAll();
    PayrollPostDTO pay(String id, String keyPass) throws hiberusBankException, WorkerNotFoundException;
}
