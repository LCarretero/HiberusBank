package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "worker")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private UUID id;
    @Column(name = "dni")
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
    @JsonIgnore
    private List<Long> transfersEmitted;
    @Column(name = "received")
    @ElementCollection
    @JsonIgnore
    private List<Long> transfersReceived;
    @Column(name = "payrolls")
    @JsonIgnore
    @ElementCollection
    private List<Long> payrolls;
}
