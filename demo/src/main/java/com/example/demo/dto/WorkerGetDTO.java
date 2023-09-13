package com.example.demo.dto;

import com.example.demo.entities.Payroll;
import com.example.demo.entities.Transfer;
import com.example.demo.entities.Worker;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorkerGetDTO {

    private String name;
    private String lastName;
    private double salary;
    private double balance;
    private List<TransferPostDTO> transfersEmitted;
    private List<TransferPostDTO> transfersReceived;
    private List<PayrollPostDTO> payrolls;

    public WorkerGetDTO(Worker worker) {
        this.balance = worker.getBalance();
        this.lastName = worker.getLastName();
        this.name = worker.getName();
        this.salary = worker.getSalary();
        this.payrolls = new ArrayList<>();
        this.transfersEmitted = new ArrayList<>();
        this.transfersReceived = new ArrayList<>();

        for (Payroll p : worker.getPayrolls()) {
            this.payrolls.add(new PayrollPostDTO(p));
        }
        for (Transfer t : worker.getTransfersEmitted()) {
            if (t.isValid())
                this.transfersEmitted.add(new TransferPostDTO(t));
        }
        for (Transfer t : worker.getTransfersReceived()) {
            if (t.isValid())
                this.transfersReceived.add(new TransferPostDTO(t));
        }
    }
}
