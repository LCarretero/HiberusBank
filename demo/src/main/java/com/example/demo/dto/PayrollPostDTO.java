package com.example.demo.dto;

import com.example.demo.entities.Payroll;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PayrollPostDTO {
    private final String date;
    private final Double amount;

    public PayrollPostDTO(Payroll payroll) {
        this.date = payroll.getDate();
        this.amount = payroll.getAmount();
    }
}
