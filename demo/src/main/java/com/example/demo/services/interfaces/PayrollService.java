package com.example.demo.services.interfaces;

import com.example.demo.dto.PayrollDTO;
import com.example.demo.exceptions.HiberusBankException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;

import java.util.List;

public interface PayrollService {
    List<PayrollDTO> getAll();

    PayrollDTO pay(String id, String keyPass) throws HiberusBankException, WorkerNotFoundException;
}
