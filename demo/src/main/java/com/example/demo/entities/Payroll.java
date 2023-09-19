package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payrolls")
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private String date;
    @Column(name = "amount")
    private double amount;
    @Column(name = "worker")
    private String worker;

    @Override
    public String toString() {
        return "Payroll{" +
                "id = " + id +
                ", date = " + date + '\'' +
                ", amount = " + amount +
                ", worker name = " + worker +
                '}';
    }
}
