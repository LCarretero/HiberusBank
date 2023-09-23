package com.example.demo;


import com.example.demo.dto.WorkerDTO;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.transferExceptions.TransferBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerBadRequestException;
import com.example.demo.exceptions.workerExceptions.WorkerConflictException;
import com.example.demo.exceptions.workerExceptions.WorkerNotFoundException;
import com.example.demo.exceptions.workerExceptions.WorkerUnauthorizedException;
import com.example.demo.repositories.PayrollRepository;
import com.example.demo.repositories.TransferRepository;
import com.example.demo.repositories.WorkerRepository;
import com.example.demo.services.implementation.WorkerServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
    private Worker workerTest2;
    private List<Worker> workerList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        workerTest = new Worker(UUID.randomUUID(), "77991221V", "Lisardo", "Carretero", 6000, 0.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        workerTest2 = new Worker(UUID.randomUUID(), "88771221V", "David", "Gomez", 12000, 4000, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        workerList = List.of(workerTest, workerTest2);
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
        Worker workerWithBadDNI = new Worker(UUID.randomUUID(), "7991221V", "Lisardo", "Carretero", 6000, 0.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        assertThrows(WorkerBadRequestException.class, () -> workerService.saveWorker(workerWithBadDNI));
    }

    @Test
    public void testSaveWorkerTheListsAreNull() throws WorkerConflictException, WorkerBadRequestException {
        Worker workerWithListsNull = new Worker(UUID.randomUUID(), "77991221V", "Lisardo", "Carretero", 6000, 0.0, null, null, null);

        when(workerRepository.save(any(Worker.class))).thenReturn(workerWithListsNull);

        WorkerDTO result = workerService.saveWorker(workerTest);

        assertEquals(workerTest.getDni(), result.getDni());
        assertEquals(workerTest.getName(), result.getName());
        assertEquals(workerTest.getLastName(), result.getLastName());
        assertEquals(workerTest.getSalary(), result.getSalary());
        assertEquals(workerTest.getBalance(), result.getBalance());
        assertNotNull(result.getPayrolls());
        assertNotNull(result.getTransfersEmitted());
        assertNotNull(result.getTransfersReceived());
    }

    @Test
    public void testSaveWorker() throws WorkerConflictException, WorkerBadRequestException {

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

        when(workerRepository.findByDni(any())).thenReturn(workerTest);

        WorkerDTO result = workerService.workerInformation(workerTest.getDni());

        verify(workerRepository, times(1)).findByDni(any());

        assertEquals("77991221V", result.getDni());
        assertEquals("Lisardo", result.getName());
        assertEquals("Carretero", result.getLastName());
        assertEquals(6000, result.getSalary());
        assertEquals(0.0, result.getBalance());
    }

    @Test
    public void testGetAllWorkers_Unauthorized() throws NoSuchFieldException, IllegalAccessException {
        Field field = WorkerServiceImp.class.getDeclaredField("LORE");
        field.setAccessible(true);
        field.set(workerService, "É0wyn");
        field.setAccessible(false);

        assertThrows(WorkerUnauthorizedException.class, () -> {
            workerService.getAllWorkers("Théoden");
        });
    }

    @Test
    public void testGetAllWorkers_Authorized() throws WorkerUnauthorizedException, NoSuchFieldException, IllegalAccessException {
        Field field = WorkerServiceImp.class.getDeclaredField("LORE");
        field.setAccessible(true);
        field.set(workerService, "É0wyn");
        field.setAccessible(false);

        when(workerRepository.findAll()).thenReturn(workerList);

        List<WorkerDTO> resultList = workerService.getAllWorkers("É0wyn");

        assertEquals(workerTest.getName(), resultList.get(0).getName());
        assertEquals(workerTest.getLastName(), resultList.get(0).getLastName());
        assertEquals(workerTest.getDni(), resultList.get(0).getDni());
        assertEquals(workerTest2.getName(), resultList.get(1).getName());
        assertEquals(workerTest2.getLastName(), resultList.get(1).getLastName());
        assertEquals(workerTest2.getDni(), resultList.get(1).getDni());
    }

    @Test
    public void testRiseSalary_UserNotFound() {
        when(workerRepository.findByDni(any())).thenReturn(null);
        assertThrows(WorkerNotFoundException.class,
                () -> workerService.riseSalary("1111", 300));
    }

    @Test
    public void testRiseSalary_AmountNotValid() {
        when(workerRepository.findByDni(any())).thenReturn(workerTest);
        assertThrows(TransferBadRequestException.class,
                () -> workerService.riseSalary("1111", -1));
    }

    @Test
    public void testRiseSalary_Valid() throws WorkerNotFoundException, TransferBadRequestException {
        when(workerRepository.findByDni(any())).thenReturn(workerTest);
        when(workerRepository.save(any(Worker.class))).thenReturn(workerTest);

        WorkerDTO result = workerService.riseSalary("77991221V", 14);
        assertEquals(workerTest.getSalary(), result.getSalary());
    }
}


