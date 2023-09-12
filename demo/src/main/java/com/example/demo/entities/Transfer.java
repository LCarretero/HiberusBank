package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source", referencedColumnName = "dni")
    private Worker source;

    @ManyToOne
    @JoinColumn(name = "destiny", referencedColumnName = "dni")
    private Worker destiny;

    private double amount;
    @Column(name = "valid", columnDefinition = "boolean default true")
    private boolean valid;

    public Transfer(Worker sourceWorker, Worker destinyWorker, double amount) {
        this.source = sourceWorker;
        this.destiny = destinyWorker;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id = " + id +
                ", source name = " + source.getName() +
                ", destiny name = " + destiny.getName() +
                ", amount = " + amount +
                ", valid = " + valid +
                '}';
    }
}
