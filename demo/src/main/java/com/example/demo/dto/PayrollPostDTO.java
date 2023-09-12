package com.example.demo.dto;

import com.example.demo.entities.Payroll;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayrollPostDTO {
    private String date;
    private Double amount;

    public PayrollPostDTO(Payroll payroll) {
        this.date = payroll.getDate();
        this.amount = payroll.getAmount();
    }
}
