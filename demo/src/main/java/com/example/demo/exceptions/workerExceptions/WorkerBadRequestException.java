package com.example.demo.exceptions.workerExceptions;

public class WorkerBadRequestException extends Exception {
    public WorkerBadRequestException(String msg) {
        super(msg);
    }
}
