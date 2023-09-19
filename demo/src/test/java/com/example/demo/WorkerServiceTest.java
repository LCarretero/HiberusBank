package com.example.demo;


import com.example.demo.dto.WorkerDTO;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.workerExceptions.WorkerBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerConflictException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.repositories.WorkerRepository;
import com.example.demo.services.implementation.WorkerServiceImp;
import com.example.demo.services.interfaces.WorkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkerServiceTest {

    @Mock
    private WorkerService workerService;

    @Mock
    private WorkerRepository workerRepository;

    private Worker workerTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        workerService = new WorkerServiceImp();
        workerTest = new Worker(UUID.randomUUID(), "77991221V", "Lisardo", "Carretero", 6000, 0.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testSaveWorker() throws WorkerConflictException, WorkerBadRequestException {
        //When
        when(workerRepository.save(any(Worker.class))).thenReturn(workerTest);

        WorkerDTO result = workerService.saveWorker(workerTest);

        assertEquals(workerTest.getDni(), result.dni());
        assertEquals(workerTest.getName(), result.name());
        assertEquals(workerTest.getLastName(), result.lastName());
        assertEquals(workerTest.getSalary(), result.salary());
        assertEquals(workerTest.getBalance(), result.balance());
    }

    @Test
    public void testDeleteWorker() {
        workerRepository.delete(workerTest);
        verify(workerRepository, only()).delete(workerTest);
        verify(workerRepository, atMost(1)).delete(workerTest);
    }

    @Test
    public void testWorkerInformation() throws WorkerConflictException, WorkerBadRequestException, WorkerNotFoundException {
        //Given
        workerService.saveWorker(workerTest);

        //When
        when(workerRepository.getReferenceById(workerTest.getId())).thenReturn(workerTest);

        WorkerDTO result = workerService.workerInformation(workerTest.getDni());

        //Then
        verify(workerRepository, times(1)).getReferenceById(workerTest.getId());

        assertEquals("77991221V", result.dni());
        assertEquals("Lisardo", result.name());
        assertEquals("Carretero", result.lastName());
        assertEquals(6000, result.salary());
        assertEquals(0.0, result.balance());
    }
}


