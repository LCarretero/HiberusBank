package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "worker")
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
    @Setter
    private double balance = 0.0;

    //podria ser una lista de ids
    @OneToMany(mappedBy = "source", cascade = CascadeType.REMOVE)
    private List<Transfer> transfersEmitted;
    @OneToMany(mappedBy = "destiny", cascade = CascadeType.REMOVE)
    private List<Transfer> transfersReceived;
    @OneToMany(mappedBy = "worker")
    private List<Payroll> payrolls;
}
