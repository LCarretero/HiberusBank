package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@AllArgsConstructor
public class TransferCreateDTO {

    private String  sourceDNI;
    private String destinyDNI;
    private double amount;
}
