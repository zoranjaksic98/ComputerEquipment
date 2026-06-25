package com.ecommerce.computerequipment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.computerequipment.entity.Basket;
import com.ecommerce.computerequipment.entity.BasketItem;
import com.ecommerce.computerequipment.model.BasketItemResponse;
import com.ecommerce.computerequipment.model.BasketResponse;
import com.ecommerce.computerequipment.service.BasketService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {
	
	private final BasketService basketService;
	
	public BasketController(BasketService basketService) {
		this.basketService = basketService;
	}
	
	@GetMapping
	public List<BasketResponse> getAllBaskets(){
		return basketService.getAllBaskets();
	}
	
	@GetMapping("/{basketId}")
	public BasketResponse getBasketById(@PathVariable String basketId) {
		return basketService.getBasketById(basketId);
	}
	
	@DeleteMapping("/{basketId}")
	public void deleteBasketById(@PathVariable String basketId) {
		basketService.deleteBasketById(basketId);
	}
	
	@PostMapping
	public ResponseEntity<BasketResponse> createBasket(@RequestBody BasketResponse basketResponse){
		Basket basket = convertToBasketEntity(basketResponse);
		BasketResponse createdBasket = basketService.createBasket(basket);
		return new ResponseEntity<>(createdBasket, HttpStatus.CREATED);
	}
	
	private Basket convertToBasketEntity(BasketResponse basketResponse) {
		Basket basket = new Basket();
		basket.setId(basketResponse.getId());
		basket.setItems(mapBasketItemResponsesToEntities(basketResponse.getItems()));
		return basket;
	}
	
	private List<BasketItem> mapBasketItemResponsesToEntities(List<BasketItemResponse> itemResponses){
		if(itemResponses == null) {
			return new ArrayList<>();
		}
		return itemResponses.stream()
				.map(this::convertToBasketItemEntity)
				.collect(Collectors.toList());
	}
	
	private BasketItem convertToBasketItemEntity(BasketItemResponse itemResponse) {
		BasketItem basketItem = new BasketItem();
		basketItem.setId(itemResponse.getId());
		basketItem.setNaziv(itemResponse.getNaziv());
		basketItem.setOpis(itemResponse.getOpis());
		basketItem.setCena(itemResponse.getCena());
		basketItem.setUrlSlike(itemResponse.getUrlSlike());
		basketItem.setMarkaProizvoda(itemResponse.getMarkaProizvoda());
		basketItem.setTipProizvoda(itemResponse.getTipProizvoda());
		basketItem.setKolicina(itemResponse.getKolicina());
		return basketItem;
	}

}
