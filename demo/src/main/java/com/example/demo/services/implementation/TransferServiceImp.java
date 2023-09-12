package com.example.demo.services.implementation;

import com.example.demo.dto.TransferCreateDTO;
import com.example.demo.dto.TransferPostDTO;
import com.example.demo.entities.Transfer;
import com.example.demo.entities.Worker;
import com.example.demo.repositories.TransferRepository;
import com.example.demo.repositories.WorkerRepository;
import com.example.demo.services.interfaces.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransferServiceImp implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private WorkerRepository workerRepository;

    private static final String BANNED = "Antonio";

    @Override
    public TransferPostDTO makeTransfer(TransferCreateDTO transfer) throws IllegalAccessException, IllegalStateException {
        if (transfer.getAmount() % 10 != 0) throw new IllegalAccessException();
        Worker sourceWorker = workerRepository.findById(transfer.getSourceDNI()).orElse(null);
        if (sourceWorker == null) return null;
        Worker destinyWorker = workerRepository.findById(transfer.getDestinyDNI()).orElse(null);
        if (destinyWorker == null) return null;
        if (sourceWorker.getName().equals("Antonio") || destinyWorker.getName().equals("Antonio"))
            throw new IllegalStateException();
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
