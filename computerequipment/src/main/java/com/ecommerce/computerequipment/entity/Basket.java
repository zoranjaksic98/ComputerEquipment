package com.ecommerce.computerequipment.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RedisHash("Basket")
public class Basket {

	@Id
	private String id;
	private List<BasketItem> items = new ArrayList<>();
	
	public Basket(String id) {
		this.id = id;
	}
}
