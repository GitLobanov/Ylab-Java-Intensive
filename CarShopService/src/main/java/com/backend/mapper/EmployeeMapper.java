package com.backend.mapper;

import com.backend.dto.ClientDTO;
import com.backend.dto.EmployeeDTO;
import com.backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO toDTO(User employee);

    User toEntity(EmployeeDTO employeeDTO);

    void updateFromDto(EmployeeDTO employeeDTO, @MappingTarget User employee);

}
