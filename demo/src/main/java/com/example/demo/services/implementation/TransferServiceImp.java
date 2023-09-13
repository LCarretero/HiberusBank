package com.example.demo.services.implementation;

import com.example.demo.dto.TransferCreateDTO;
import com.example.demo.dto.TransferPostDTO;
import com.example.demo.entities.Transfer;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.transferExceptions.TransferBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.repositories.TransferRepository;
import com.example.demo.repositories.WorkerRepository;
import com.example.demo.services.interfaces.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransferServiceImp implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private WorkerRepository workerRepository;
    @Value(value = "banned")
    private static String banned;

    @Override
    @Transactional
    public TransferPostDTO makeTransfer(TransferCreateDTO transfer) throws TransferBadRequestException, WorkerNotFoundException {
        if (transfer.getAmount() % 10 != 0) throw new TransferBadRequestException("The amount must be a multiple of 10");
        Worker sourceWorker = workerRepository.findById(transfer.getSourceDNI()).orElse(null);
        if (sourceWorker == null) throw new WorkerNotFoundException("The source worker is not valid");
        Worker destinyWorker = workerRepository.findById(transfer.getDestinyDNI()).orElse(null);
        if (destinyWorker == null) throw new WorkerNotFoundException("The destiny worker is not valid");
        if (sourceWorker.getName().equals(banned) || destinyWorker.getName().equals(banned))
            throw new TransferBadRequestException("Antonio is not allowed in our system");
        double amount = transfer.getAmount();
        Transfer transferToDB = new Transfer(sourceWorker, destinyWorker, amount);

        double sourceBalance = sourceWorker.getBalance();
        boolean valid = sourceBalance >= amount;
        transferToDB.setValid(valid);
        transferRepository.save(transferToDB);

        if (valid) {
            double destinyBalance = destinyWorker.getBalance();
            sourceWorker.setBalance(sourceBalance - amount);
            destinyWorker.setBalance(destinyBalance + amount);
            transferToDB.setValid(true);
            sourceWorker.getTransfersEmitted().add(transferToDB);
            destinyWorker.getTransfersReceived().add(transferToDB);
            workerRepository.save(sourceWorker);
            workerRepository.save(destinyWorker);
        }

        return new TransferPostDTO(transferToDB);
    }

    @Override
    public List<TransferPostDTO> getAll() {
        List<Transfer> transferList = transferRepository.findAll();
        if (transferList.isEmpty()) return null;

        List<TransferPostDTO> result = new ArrayList<>();

        for (Transfer tr : transferList)
            result.add(new TransferPostDTO(tr));

        return result;
    }

    @Override
    public List<TransferPostDTO> failedTransfers() {
        //Maybe a query for this
        List<Transfer> transferList = transferRepository.findAll();
        if (transferList.isEmpty()) return null;

        List<TransferPostDTO> result = new ArrayList<>();

        for (Transfer tr : transferList) {
            if (!tr.isValid()) result.add(new TransferPostDTO(tr));
        }
        return result;
    }
}
