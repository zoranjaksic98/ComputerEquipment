package com.ecommerce.computerequipment.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.ecommerce.computerequipment.mapper.OrderMapper;

@Component
public class MapperConfig {
	
	@Bean
    public OrderMapper orderMapper() {
        return Mappers.getMapper(OrderMapper.class);
    }
}
