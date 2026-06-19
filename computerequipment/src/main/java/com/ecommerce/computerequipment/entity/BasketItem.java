package com.ecommerce.computerequipment.entity;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@RedisHash("BasketItem")
public class BasketItem {
	
	@Id
	private Integer id;
	private String naziv;
	private String opis;
	private Long cena;
	private String urlSLike;
	private String markaProizvoda;
	private String tipProizvoda;
	private Integer kolicina;
}
