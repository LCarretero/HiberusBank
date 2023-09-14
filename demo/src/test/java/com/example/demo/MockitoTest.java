package com.example.demo;


import com.example.demo.controllers.WorkerController;
import com.example.demo.entities.Worker;
import com.example.demo.exceptions.workerExceptions.WorkerUnauthorizedException;
import com.example.demo.services.interfaces.WorkerService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    @Mock
    private WorkerService workerService;

    @InjectMocks
    private WorkerController workerController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void pruebaAlgunaFuncionalidad() {

        when(workerService.saveWorker(new Worker())).thenReturn("Resultado esperado");

        // Ejecuta la prueba
        String resultado = workerController.postWorker();

        // Verifica que el mock fue llamado y que se obtuvo el resultado esperado
        try {
            verify(workerService).getAllWorkers("Eowyn");
        }catch (WorkerUnauthorizedException e){

        }
        assertEquals("Resultado esperado", resultado);
    }

}

