package com.ecommerce.computerequipment.model;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketItemResponse {
	@Id
	private Integer id;
	private String naziv;
	private String opis;
	private Long cena;
	private String urlSlike;
	private String markaProizvoda;
	private String tipProizvoda;
	private Integer kolicina;
}
