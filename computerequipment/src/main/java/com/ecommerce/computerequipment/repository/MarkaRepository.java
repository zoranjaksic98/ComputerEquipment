package com.ecommerce.computerequipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.computerequipment.entity.Marka;

@Repository
public interface MarkaRepository extends JpaRepository<Marka, Integer>{
	
}
