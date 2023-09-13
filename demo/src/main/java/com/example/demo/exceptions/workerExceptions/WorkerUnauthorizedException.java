package com.example.demo.exceptions.workerExceptions;

public class WorkerUnauthorizedException extends Exception {
    public WorkerUnauthorizedException(String msg) {
        super(msg);
    }
}
