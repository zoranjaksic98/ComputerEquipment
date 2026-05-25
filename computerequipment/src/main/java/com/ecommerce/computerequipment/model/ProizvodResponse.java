package com.ecommerce.computerequipment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProizvodResponse {

	private Integer id;
	private String naziv;
	private String opis;
	private Long cena;
	private String urlSlike;
	private String nazivTipa;
	private String nazivMarke;

}
