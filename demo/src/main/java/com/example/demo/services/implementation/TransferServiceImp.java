package com.example.demo.services.implementation;

import com.example.demo.dto.TransferDTO;
import com.example.demo.entities.Transfer;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.transferExceptions.TransferUnauthorizedException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.mapper.TransferMapper;
import com.example.demo.repositories.TransferRepository;
import com.example.demo.repositories.WorkerRepository;
import com.example.demo.services.interfaces.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferServiceImp implements TransferService {
    @Value(value = "banned")
    private static String banned;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private WorkerRepository workerRepository;

    @Override
    @Transactional
    public TransferDTO makeTransfer(TransferDTO transfer) throws TransferUnauthorizedException, WorkerNotFoundException {
        boolean valid = transfer.amount() % 10 == 0;
        Worker sourceWorker = workerRepository.findById(transfer.source()).orElse(null);
        if (sourceWorker == null) throw new WorkerNotFoundException("The source worker is not valid");
        Worker destinyWorker = workerRepository.findById(transfer.destiny()).orElse(null);
        if (destinyWorker == null) throw new WorkerNotFoundException("The destiny worker is not valid");
        if (sourceWorker.getName().equals(banned) || destinyWorker.getName().equals(banned))
            throw new TransferUnauthorizedException("Antonio is not allowed in our system");
        double amount = transfer.amount();
        Transfer transferToDB = new Transfer(sourceWorker, destinyWorker, amount);

        double sourceBalance = sourceWorker.getBalance();
        valid = valid && sourceBalance >= amount;

        transferToDB.setValid(valid);
        transferRepository.save(transferToDB);

        if (valid) {
            double destinyBalance = destinyWorker.getBalance();
            sourceWorker.setBalance(sourceBalance - amount);
            destinyWorker.setBalance(destinyBalance + amount);
            sourceWorker.getTransfersEmitted().add(transferToDB.getSource());
            destinyWorker.getTransfersReceived().add(transferToDB.getDestiny());
            workerRepository.save(sourceWorker);
            workerRepository.save(destinyWorker);
        }

        return TransferMapper.INSTANCE.mapToDTO(transferToDB);
    }

    @Override
    public List<TransferDTO> getAll() {
        List<Transfer> transferList = transferRepository.findAll();
        return transferList.stream().map(TransferMapper.INSTANCE::mapToDTO).toList();
    }

    @Override
    public List<TransferDTO> failedTransfers() {
        List<Transfer> transferList = transferRepository.findAll();
        List<TransferDTO> result = new ArrayList<>();
        if (transferList.isEmpty()) return result;

        return transferList.stream().filter(Transfer::isValid).map(TransferMapper.INSTANCE::mapToDTO).collect(Collectors.toList());
    }
}
