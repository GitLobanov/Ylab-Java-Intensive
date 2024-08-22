package com.backend.mapper;

import com.backend.dto.ClientDTO;
import com.backend.dto.OrderDTO;
import com.backend.model.Order;
import com.backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDTO(Order order);

    Order toEntity(OrderDTO orderDTO);

    void updateFromDto(OrderDTO orderDTO, @MappingTarget Order order);

}
