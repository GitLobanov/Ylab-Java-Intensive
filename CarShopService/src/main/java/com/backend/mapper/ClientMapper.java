package com.backend.mapper;

import ch.qos.logback.core.net.server.Client;
import com.backend.dto.CarDTO;
import com.backend.dto.ClientDTO;
import com.backend.model.Car;
import com.backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDTO toDTO(User client);

    User toEntity(ClientDTO clientDTO);

    void updateFromDto(ClientDTO clientDTO, @MappingTarget User client);

}
