package com.example.demo;


import com.example.demo.dto.WorkerDTO;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.workerExceptions.WorkerBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerConflictException;
import com.example.demo.repositories.WorkerRepository;
import com.example.demo.services.implementation.WorkerServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkerServiceTest {

    @InjectMocks
    private WorkerServiceImp workerService;

    @Mock
    private WorkerRepository workerRepository;

    private Worker workerTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        workerTest = new Worker(UUID.randomUUID(), "77991221V", "Lisardo", "Carretero", 6000, 0.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testSaveWorker() throws WorkerConflictException, WorkerBadRequestException {
        when(workerRepository.save(workerTest)).thenReturn(workerTest);

        WorkerDTO result = workerService.saveWorker(workerTest);

        verify(workerRepository).save(workerTest);

        assertEquals("77991221V", result.dni());
        assertEquals("Lisardo", result.name());
        assertEquals("Carretero", result.lastName());
        assertEquals(6000, result.salary());
        assertEquals(0.0, result.balance());
    }
}


