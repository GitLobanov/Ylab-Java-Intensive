package com.backend.mapper;

import com.backend.dto.CarDTO;
import com.backend.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper  {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDTO toDTO(Car car);

    Car toEntity(CarDTO carDTO);

    void updateFromDto(CarDTO carDTO, @MappingTarget Car car);

}
