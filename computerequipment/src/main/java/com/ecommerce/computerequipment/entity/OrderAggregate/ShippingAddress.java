package com.ecommerce.computerequipment.entity.OrderAggregate;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingAddress {
	private String naziv;
	private String adresa1;
	private String adresa2;
	private String grad;
	private String opstina;
	private String postanskiBroj;
	private String drzava;
}
