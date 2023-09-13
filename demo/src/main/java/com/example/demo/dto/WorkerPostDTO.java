package com.example.demo.dto;

import com.example.demo.entities.Payroll;
import com.example.demo.entities.Transfer;
import com.example.demo.entities.Worker;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class WorkerPostDTO {

    private String name;
    private String lastName;
    private double salary;

    public WorkerPostDTO() {

    }

    public WorkerPostDTO(Worker worker) {
        this.name = worker.getName();
        this.lastName = worker.getLastName();
        this.salary = worker.getSalary();
    }
}
