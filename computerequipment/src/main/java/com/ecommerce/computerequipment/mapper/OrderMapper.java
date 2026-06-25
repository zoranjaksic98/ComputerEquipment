package com.ecommerce.computerequipment.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.computerequipment.entity.OrderAggregate.Order;
import com.ecommerce.computerequipment.model.OrderDTO;
import com.ecommerce.computerequipment.model.OrderResponse;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	
	@Mapping(source = "id", target = "id")
    @Mapping(source = "idKorpe", target = "idKorpe")
    @Mapping(source = "adresa", target = "adresa")
    @Mapping(source = "konacnaCena", target = "konacnaCena")
    @Mapping(source = "naknadaZaDostavu", target = "naknadaZaDostavu")
    @Mapping(target = "cenaSaNaknadomZaDostavu", expression = "java(order.getKonacnaCena() + order.getNaknadaZaDostavu())")
    @Mapping(target = "datumPorudzbine", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "statusPorudzbine", constant = "Na_Cekanju")
	OrderResponse OrderToOrderResponse(Order order);
	
	@Mapping(target = "datumPorudzbine", expression = "java(orderDTO.getDatumPorudzbine())")
    @Mapping(target = "statusPorudzbine", constant = "Na_Cekanju") // Reference enum constant directly
    Order orderResponseToOrder(OrderDTO orderDTO);

    List<OrderDTO> ordersToOrderResponses(List<Order> orders);

    void updateOrderFromOrderResponse(OrderDTO orderDto, @MappingTarget Order order);
}
