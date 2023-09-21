package com.example.demo.dto;

import com.example.demo.entities.Worker;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class WorkerDTO {
    private final String name;
    private final String lastName;
    private final String dni;
    private final double salary;
    private final double balance;
    private final List<TransferDTO> transfersEmitted;
    private final List<TransferDTO> transfersReceived;
    private final List<PayrollDTO> payrolls;

    public WorkerDTO(Worker worker, List<TransferDTO> transfersEmitted,
                     List<TransferDTO> transfersReceived,
                     List<PayrollDTO> payrolls){
        this.name = worker.getName();
        this.lastName = worker.getLastName();
        this.dni = worker.getDni();
        this.salary= worker.getSalary();
        this.balance= worker.getBalance();
        this.transfersReceived = transfersReceived;
        this.transfersEmitted = transfersEmitted;
        this.payrolls = payrolls;
    }
}

