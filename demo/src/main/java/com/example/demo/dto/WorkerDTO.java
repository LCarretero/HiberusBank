package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WorkerDTO(String name, String lastName, double salary, double balance,
                        List<Long> transfersEmitted, List<Long> transfersReceived,
                        List<Long> payrolls) {

}

