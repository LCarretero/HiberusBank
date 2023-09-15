package com.example.demo.dto;

import com.example.demo.entities.Payroll;
import com.example.demo.entities.Worker;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.patterns.ThisOrTargetPointcut;

import javax.persistence.Column;
import javax.persistence.OneToMany;

@Getter
public class PayrollDTO {

    private final String date;
    private final Double amount;
    private final WorkerPostDTO worker;

    public PayrollDTO(Payroll payroll) {
        this.date = payroll.getDate();
        this.amount = payroll.getAmount();
        this.worker = new WorkerPostDTO(payroll.getWorker());
    }
}
