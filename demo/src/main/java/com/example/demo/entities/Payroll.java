package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private String date;
    @Column(name = "amount")
    private double amount;

    @Override
    public String toString() {
        return "Payroll{" +
                "id = " + id +
                ", date = " + date + '\'' +
                ", amount = " + amount +
                ", worker name = " + worker.getName() +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "payrolls")
    private Worker worker;
}
