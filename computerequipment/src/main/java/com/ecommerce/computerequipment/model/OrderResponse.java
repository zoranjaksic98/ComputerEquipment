package com.ecommerce.computerequipment.model;

import java.time.LocalDateTime;

import com.ecommerce.computerequipment.entity.OrderAggregate.OrderStatus;
import com.ecommerce.computerequipment.entity.OrderAggregate.ShippingAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
	
	private Integer id;
	private String idKorpe;
	private ShippingAddress adresa;
	private Long konacnaCena;
	private Long naknadaZaDostavu;
	private Double cenaSaNaknadomZaDostavu;
	private LocalDateTime datumPorudzbine;
	private OrderStatus statusPorudzbine;

}
