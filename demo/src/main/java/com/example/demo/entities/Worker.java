package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
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
    @Column(name = "emitted")
    @ElementCollection
    private List<String> transfersEmitted;
    @Column(name = "received")
    @ElementCollection
    private List<String> transfersReceived;
    @Column(name = "payrolls")
    @ElementCollection
    private List<String> payrolls;
}
