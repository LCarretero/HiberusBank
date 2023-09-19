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
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "source", referencedColumnName = "dni")
    private String source;

    @JoinColumn(name = "destiny", referencedColumnName = "dni")
    private String destiny;

    private double amount;
    @Column(name = "valid", columnDefinition = "boolean default true")
    private boolean valid;

    public Transfer(Worker sourceWorker, Worker destinyWorker, double amount) {
        this.source = sourceWorker.getDni();
        this.destiny = destinyWorker.getDni();
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id = " + id +
                ", source name = " + source +
                ", destiny name = " + destiny +
                ", amount = " + amount +
                ", valid = " + valid +
                '}';
    }
}
