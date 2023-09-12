package com.example.demo.services.interfaces;

import com.example.demo.dto.TransferCreateDTO;
import com.example.demo.dto.TransferPostDTO;

import java.util.List;

public interface TransferService {
    TransferPostDTO makeTransfer(TransferCreateDTO transfer) throws Exception;
    List<TransferPostDTO> getAll();
    List<TransferPostDTO> failedTransfers();
}
