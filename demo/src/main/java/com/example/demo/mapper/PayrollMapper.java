package com.example.demo.mapper;

import com.example.demo.dto.PayrollDTO;
import com.example.demo.entities.Payroll;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = Payroll.class)
public interface PayrollMapper {
    PayrollMapper INSTANCE = Mappers.getMapper(PayrollMapper.class);

    PayrollDTO mapToDTO(Payroll payroll);
}
