package com.example.demo.services.interfaces;


import com.example.demo.dto.TransferDTO;
import com.example.demo.exceptions.transferExceptions.TransferUnauthorizedException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;

import java.util.List;

public interface TransferService {
    TransferDTO makeTransfer(TransferDTO transfer) throws TransferUnauthorizedException, WorkerNotFoundException;

    List<TransferDTO> getAll();

    List<TransferDTO> failedTransfers();
}
