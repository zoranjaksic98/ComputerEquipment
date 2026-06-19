package com.ecommerce.computerequipment.service;

import java.util.List;

import com.ecommerce.computerequipment.entity.Basket;
import com.ecommerce.computerequipment.model.BasketResponse;

public interface BasketService {
	List<BasketResponse> getAllBaskets();
	BasketResponse getBasketById(String basketId);
	void deleteBasketById(String basketId);
	BasketResponse createBasket(Basket basket);
}
