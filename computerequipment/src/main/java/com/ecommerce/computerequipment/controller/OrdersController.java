package com.ecommerce.computerequipment.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.computerequipment.model.OrderDTO;
import com.ecommerce.computerequipment.model.OrderResponse;
import com.ecommerce.computerequipment.service.OrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
	
	private final OrderService orderService;
	
	public OrdersController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer orderId){
		OrderResponse order = orderService.getOrderById(orderId);
		if(order != null) {
			return ResponseEntity.ok(order);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getAllOrders(){
		List<OrderResponse> orders = orderService.getAllOrders();
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("/paged")
	public ResponseEntity<Page<OrderResponse>> getAllOrdersPaged(Pageable pageable){
		Page<OrderResponse> orders = orderService.getAllOrders(pageable);
		return ResponseEntity.ok(orders);
	}
	
	@PostMapping
	public ResponseEntity<Integer> createOrder(@Valid @RequestBody OrderDTO orderDTO){
		Integer orderId = orderService.createOrder(orderDTO);
		if(orderId != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId){
		orderService.deleteOrder(orderId);
		return ResponseEntity.noContent().build();	
	}

}
