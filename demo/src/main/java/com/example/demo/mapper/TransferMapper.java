package com.example.demo.mapper;

import com.example.demo.dto.TransferDTO;
import com.example.demo.entities.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = Transfer.class)
public interface TransferMapper {
    TransferMapper INSTANCE = Mappers.getMapper(TransferMapper.class);

    TransferDTO mapToDTO(Transfer transfer);
}
