package com.ecommerce.computerequipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.computerequipment.entity.Proizvod;

@Repository
public interface ProizvodRepository extends JpaRepository<Proizvod, Integer>{
	
}
