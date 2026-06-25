package com.ecommerce.computerequipment.model;

import java.time.LocalDateTime;

import com.ecommerce.computerequipment.entity.OrderAggregate.ShippingAddress;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	
	@JsonProperty("idKorpe")
	private String idKorpe;
	@JsonProperty("adresa")
	private ShippingAddress adresa;
	private Long konacnaCena;
	private Long naknadaZaDostavu;
	private LocalDateTime datumPorudzbine;
	

}
