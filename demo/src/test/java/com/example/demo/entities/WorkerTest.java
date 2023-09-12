package com.example.demo.entities;

import com.example.demo.dto.WorkerGetDTO;
import com.example.demo.dto.WorkerPostDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class WorkerTest {

    @Test
    public void workerPostDTO() {

        Worker worker = new Worker("11", "name", "lastName", 1.0, 0.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        WorkerPostDTO workerPostDTO = new WorkerPostDTO(worker);

        Assertions.assertEquals(worker.getName(), workerPostDTO.getName());
        Assertions.assertEquals(worker.getLastName(), workerPostDTO.getLastName());
        Assertions.assertEquals(worker.getSalary(), workerPostDTO.getSalary(), 0.001);
    }

    @Test
    public void workerGetDTO() {

        Worker worker = new Worker("11", "name", "lastName", 1.0, 0.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        WorkerGetDTO workerGetDTO = new WorkerGetDTO(worker);

        Assertions.assertEquals(worker.getName(), workerGetDTO.getName());
        Assertions.assertEquals(worker.getLastName(), workerGetDTO.getLastName());
        Assertions.assertEquals(worker.getSalary(), workerGetDTO.getSalary(), 0.001);
        Assertions.assertEquals(worker.getBalance(), workerGetDTO.getBalance(), 0.001);
    }
}