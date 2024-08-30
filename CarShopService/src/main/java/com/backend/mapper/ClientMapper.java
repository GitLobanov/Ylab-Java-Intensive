package com.backend.mapper;

import com.backend.dto.CarDTO;
import com.backend.dto.ClientDTO;
import com.backend.model.Car;
import com.backend.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
@JsonIgnoreProperties
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDTO toDTO(User client);

    User toEntity(ClientDTO clientDTO);

    void updateFromDto(ClientDTO clientDTO, @MappingTarget User client);

    List<ClientDTO> getDTOs(List<User> users);
}
