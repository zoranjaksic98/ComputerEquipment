package com.ecommerce.computerequipment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.computerequipment.entity.OrderAggregate.Order;
import com.ecommerce.computerequipment.entity.OrderAggregate.OrderItem;
import com.ecommerce.computerequipment.entity.OrderAggregate.ProductItemOrdered;
import com.ecommerce.computerequipment.mapper.OrderMapper;
import com.ecommerce.computerequipment.model.BasketItemResponse;
import com.ecommerce.computerequipment.model.BasketResponse;
import com.ecommerce.computerequipment.model.OrderDTO;
import com.ecommerce.computerequipment.model.OrderResponse;
import com.ecommerce.computerequipment.repository.MarkaRepository;
import com.ecommerce.computerequipment.repository.OrderRepository;
import com.ecommerce.computerequipment.repository.TipRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;
	
	private final MarkaRepository markaRepository;
	
	private final TipRepository tipRepository;
	
	private final BasketService basketService;
	
	private final OrderMapper orderMapper;
	
	public OrderServiceImpl(OrderRepository orderRepository, MarkaRepository markaRepository, TipRepository tipRepository, BasketService basketService, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.markaRepository = markaRepository;
        this.tipRepository = tipRepository;
        this.basketService = basketService;
        this.orderMapper = orderMapper;
    }
	
	@Override
	public OrderResponse getOrderById(Integer orderId) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		return optionalOrder.map(orderMapper::OrderToOrderResponse).orElse(null);
	}

	@Override
	public List<OrderResponse> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		return orders.stream().map(orderMapper::OrderToOrderResponse).collect(Collectors.toList());
	}

	@Override
	public Page<OrderResponse> getAllOrders(Pageable pageable) {
		return orderRepository.findAll(pageable).map(orderMapper::OrderToOrderResponse);
	}
	
	@Override
	public void deleteOrder(Integer orderId) {
		orderRepository.deleteById(orderId);
		
	}

	@Override
	public Integer createOrder(OrderDTO order) {
		// Fetching Basket details
		BasketResponse basketResponse = basketService.getBasketById(order.getIdKorpe());
		if(basketResponse == null) {
			log.error("Bakset wirh ID {} not found.", order.getIdKorpe());
			return null;
		}
		//Map basket items to order items
		List<OrderItem> orderItems = basketResponse.getItems().stream()
				.map(this::mapBasketItemToOrder)
				.collect(Collectors.toList());
		// calculate subtotal
		double subTotal = basketResponse.getItems().stream()
				.mapToDouble(item -> item.getCena() * item.getKolicina())
				.sum();
		
		// set order subttoal
		Order orderr = orderMapper.orderResponseToOrder(order);
		orderr.setPorudzbine(orderItems);
		orderr.setKonacnaCena(subTotal);
		
		// save the order
		Order savedOrder = orderRepository.save(orderr);
		basketService.deleteBasketById(order.getIdKorpe());
		// return the response
		return savedOrder.getId();
	}
	
	private OrderItem mapBasketItemToOrder(BasketItemResponse basketItemResponse) {
		if(basketItemResponse != null) {
			OrderItem orderItem = new OrderItem();
			orderItem.setNaruceniProizvod(mapBasketItemToProduct(basketItemResponse));
			orderItem.setKolicina(basketItemResponse.getKolicina());
			return orderItem;
		}else {
			return null;
		}
	}
	
	private ProductItemOrdered mapBasketItemToProduct(BasketItemResponse basketItemResponse) {
		ProductItemOrdered productItemOrdered = new ProductItemOrdered();
		// Populate
		productItemOrdered.setNaziv(basketItemResponse.getNaziv());
		productItemOrdered.setUrlSlike(basketItemResponse.getUrlSlike());
		productItemOrdered.setIdProizvoda(basketItemResponse.getId());
		return productItemOrdered;	
	}
	

}
