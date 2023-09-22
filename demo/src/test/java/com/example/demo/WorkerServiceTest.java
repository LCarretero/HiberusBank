package com.example.demo;


import com.example.demo.dto.WorkerDTO;
import com.example.demo.entities.Payroll;
import com.example.demo.entities.Transfer;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.workerExceptions.WorkerBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerConflictException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.repositories.PayrollRepository;
import com.example.demo.repositories.TransferRepository;
import com.example.demo.repositories.WorkerRepository;
import com.example.demo.services.implementation.WorkerServiceImp;
import com.example.demo.services.interfaces.WorkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkerServiceTest {

    @InjectMocks
    private WorkerServiceImp workerService;

    @Mock
    private WorkerRepository workerRepository;
    @Mock
    private PayrollRepository payrollRepository;
    @Mock
    private TransferRepository transferRepository;

    private Worker workerTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        workerTest = new Worker(UUID.randomUUID(), "77991221V", "Lisardo", "Carretero", 6000, 0.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        workerTest.getPayrolls().add(1L);
        workerTest.getTransfersReceived().add(1L);
        workerTest.getTransfersEmitted().add(1L);
    }

    @Test
    public void testConflictInSaveWorker() throws WorkerConflictException, WorkerBadRequestException {
        when(workerRepository.findByDni(any())).thenReturn(workerTest);
        assertThrows(WorkerConflictException.class, () -> workerService.saveWorker(workerTest));
    }

    @Test
    public void testWorkerBadRequestExceptionInSaveWorker() throws WorkerConflictException, WorkerBadRequestException {
        Worker workerWithBadDNI = workerTest = new Worker(UUID.randomUUID(), "7991221V", "Lisardo", "Carretero", 6000, 0.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        assertThrows(WorkerBadRequestException.class, () -> workerService.saveWorker(workerTest));
    }
    @Test
    public void testSaveWorker() throws WorkerConflictException, WorkerBadRequestException {
        //When
        when(workerRepository.save(any(Worker.class))).thenReturn(workerTest);

        WorkerDTO result = workerService.saveWorker(workerTest);

        assertEquals(workerTest.getDni(), result.getDni());
        assertEquals(workerTest.getName(), result.getName());
        assertEquals(workerTest.getLastName(), result.getLastName());
        assertEquals(workerTest.getSalary(), result.getSalary());
        assertEquals(workerTest.getBalance(), result.getBalance());
    }

    @Test
    public void testDeleteWorker() {
        doNothing().when(workerRepository).delete(workerTest);
        when(workerRepository.findByDni(any())).thenReturn(workerTest);
        workerService.deleteWorker(workerTest.getDni());
    }
    @Test
    public void testDeleteWorkerOnCascade() {
        doNothing().when(workerRepository).delete(workerTest);
        doNothing().when(payrollRepository).deleteById(any());
        doNothing().when(transferRepository).deleteById(any());
        when(workerRepository.findByDni(any())).thenReturn(workerTest);

        workerService.deleteWorker(workerTest.getDni());
    }

    @Test
    public void testWorkerInformation() throws WorkerConflictException, WorkerBadRequestException, WorkerNotFoundException {

        //When
        when(workerRepository.findByDni(any())).thenReturn(workerTest);

        //Given

        WorkerDTO result = workerService.workerInformation(workerTest.getDni());

        //Then
        verify(workerRepository, times(1)).findByDni(any());

        assertEquals("77991221V", result.getDni());
        assertEquals("Lisardo", result.getName());
        assertEquals("Carretero", result.getLastName());
        assertEquals(6000, result.getSalary());
        assertEquals(0.0, result.getBalance());
    }
}


