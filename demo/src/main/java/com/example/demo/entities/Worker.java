package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Worker {
    @Id
    private String dni;
    @Column(name = "name")
    private String name;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "salary")
    @Setter
    private double salary;
    @Column(name = "balance")
    @JsonIgnore
    private double balance = 0.0;

    @OneToMany(mappedBy = "source")
    private List<Transfer> transfersEmitted;
    @OneToMany(mappedBy = "destiny")
    private List<Transfer> transfersReceived;
    @OneToMany(mappedBy = "worker")
    private List<Payroll> payrolls;
}
