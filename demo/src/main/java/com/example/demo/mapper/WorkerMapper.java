package com.example.demo.mapper;

import com.example.demo.dto.WorkerDTO;
import com.example.demo.entities.Transfer;
import com.example.demo.entities.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = Transfer.class)
public interface WorkerMapper {
    WorkerMapper INSTANCE = Mappers.getMapper(WorkerMapper.class);

    WorkerDTO modelToDTO(Worker worker);
}
