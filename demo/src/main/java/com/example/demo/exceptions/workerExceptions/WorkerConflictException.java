package com.example.demo.exceptions.workerExceptions;

public class WorkerConflictException extends Exception{
    public WorkerConflictException(String msg) {
        super(msg);
    }
}
