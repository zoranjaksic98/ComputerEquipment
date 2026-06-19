package com.ecommerce.computerequipment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.computerequipment.entity.Basket;

@Repository
public interface BasketRepository extends CrudRepository<Basket, String>{
	

}
