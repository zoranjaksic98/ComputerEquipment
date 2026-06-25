package com.ecommerce.computerequipment.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.computerequipment.entity.OrderAggregate.Order;
import com.ecommerce.computerequipment.entity.OrderAggregate.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	
	List<Order> findByIdKorpe(String basketId);
	List<Order> findByStatusPorudzbine(OrderStatus orderStatus);
	List<Order> findBydatumPorudzbineBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	@Query("SELECT o FROM Order o JOIN o.porudzbine oi WHERE oi.naruceniProizvod.naziv LIKE %:naziv%")
	List<Order> findByProductNameInOrderItems(@Param("naziv") String productName);
	
	@Query("SELECT o FROM Order o WHERE o.adresa.grad = :grad")
	List<Order> findByAdresaIsporukeGrad(@Param("grad") String grad);

}
