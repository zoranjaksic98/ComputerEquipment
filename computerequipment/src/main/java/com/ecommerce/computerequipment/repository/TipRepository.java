package com.ecommerce.computerequipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.computerequipment.entity.Tip;

@Repository
public interface TipRepository extends JpaRepository<Tip, Integer>{
	
}
