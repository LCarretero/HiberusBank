package com.example.demo.services.interfaces;

import com.example.demo.dto.TransferCreateDTO;
import com.example.demo.dto.TransferPostDTO;
import com.example.demo.exceptions.transferExceptions.TransferUnauthorizedException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;

import java.util.List;

public interface TransferService {
    TransferPostDTO makeTransfer(TransferCreateDTO transfer) throws TransferUnauthorizedException, WorkerNotFoundException;

    List<TransferPostDTO> getAll();

    List<TransferPostDTO> failedTransfers();
}
