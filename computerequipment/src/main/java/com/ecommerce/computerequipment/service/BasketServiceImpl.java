package com.ecommerce.computerequipment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.computerequipment.entity.Basket;
import com.ecommerce.computerequipment.entity.BasketItem;
import com.ecommerce.computerequipment.model.BasketItemResponse;
import com.ecommerce.computerequipment.model.BasketResponse;
import com.ecommerce.computerequipment.repository.BasketRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BasketServiceImpl implements BasketService{
	
	private final BasketRepository basketRepository;
	
	public BasketServiceImpl(BasketRepository basketRepository) {
		this.basketRepository = basketRepository;
	}

	@Override
	public List<BasketResponse> getAllBaskets() {
		log.info("Dobavi sve proizvode");
		List<Basket> basketList = (List<Basket>) basketRepository.findAll();
		List<BasketResponse> basketResponses = basketList.stream()
				.map(this::convertToBasketResponse)
				.collect(Collectors.toList());
		log.info("Dobavljeni su svi proizvodi");
		return basketResponses;
	}
	
	

	@Override
	public BasketResponse getBasketById(String basketId) {
		log.info("Pronadji korpu sa id-em: {}", basketId);
		Optional<Basket> basketOptional = basketRepository.findById(basketId);
		if(basketOptional.isPresent()) {
			Basket basket = basketOptional.get();
			log.info("Pronadji korpu sa id-em: {}", basketId);
			return convertToBasketResponse(basket);
		}else {
			log.info("Korpa sa id-em: {} nije pronadjena.", basketId);
			return null;
		}
	}

	@Override
	public void deleteBasketById(String basketId) {
		log.info("Obrisi korpu sa id-em: {}", basketId);
		basketRepository.deleteById(basketId);
		log.info("Obrisana korpu sa id-em: {}", basketId);	
	}

	@Override
	public BasketResponse createBasket(Basket basket) {
		log.info("Kreiraj korpu");
		Basket savedBasket = basketRepository.save(basket);
		log.info("Korpa sa Id-em: {} je kreirana.", savedBasket.getId());
		return convertToBasketResponse(savedBasket);
	}
	
	private BasketResponse convertToBasketResponse(Basket basket) {
		if(basket == null) {
			return null;
		}
		List<BasketItemResponse> itemsResponses = basket.getItems().stream()
				.map(this::convertToBasketItemResponse)
				.collect(Collectors.toList());
		return BasketResponse.builder()
				.id(basket.getId())
				.items(itemsResponses)
				.build();
	}
	
	private BasketItemResponse convertToBasketItemResponse(BasketItem basketItem) {
		return BasketItemResponse.builder()
				.id(basketItem.getId())
				.naziv(basketItem.getNaziv())
				.opis(basketItem.getOpis())
				.cena(basketItem.getCena())
				.urlSlike(basketItem.getUrlSlike())
				.markaProizvoda(basketItem.getMarkaProizvoda())
				.tipProizvoda(basketItem.getTipProizvoda())
				.kolicina(basketItem.getKolicina())
				.build();
	}

}
