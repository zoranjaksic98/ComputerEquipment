package com.ecommerce.computerequipment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipResponse {
	
	private Integer id;
	private String naziv;

}
