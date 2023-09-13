package com.example.demo.exceptions.workerExceptions;

public class WorkerNotFoundException extends Exception {
    public WorkerNotFoundException(String msg) {
        super(msg);
    }
}
