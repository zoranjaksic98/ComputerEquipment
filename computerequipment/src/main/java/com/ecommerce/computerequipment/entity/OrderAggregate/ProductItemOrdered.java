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
public class ProductItemOrdered {
	private Integer idProizvoda;
	private String naziv;
	private String urlSlike;
}
