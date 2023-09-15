package com.example.demo;


import com.example.demo.controllers.WorkerController;
import com.example.demo.exceptions.workerExceptions.WorkerUnauthorizedException;
import com.example.demo.services.interfaces.WorkerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class MockitoTest {

    @Mock
    private WorkerService workerService;

    @InjectMocks
    private WorkerController workerController;


    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void pruebaAlgunaFuncionalidad() {

        //when(workerService.saveWorker(new Worker())).thenReturn("Resultado esperado");

        // Ejecuta la prueba
        // String resultado = workerController.postWorker();

        // Verifica que el mock fue llamado y que se obtuvo el resultado esperado
        try {
            verify(workerService).getAllWorkers("Eowyn");
        }catch (WorkerUnauthorizedException e){

        }
        //assertEquals("Resultado esperado", resultado);
    }

}

