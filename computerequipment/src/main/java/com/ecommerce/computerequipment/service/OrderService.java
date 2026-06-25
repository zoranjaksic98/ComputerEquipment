package com.ecommerce.computerequipment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.computerequipment.model.OrderDTO;
import com.ecommerce.computerequipment.model.OrderResponse;

public interface OrderService {
	
	OrderResponse getOrderById(Integer orderId);
	List<OrderResponse> getAllOrders();
	Page<OrderResponse> getAllOrders(Pageable pageable);
	Integer createOrder(OrderDTO order);
	void deleteOrder(Integer orderId);

}
