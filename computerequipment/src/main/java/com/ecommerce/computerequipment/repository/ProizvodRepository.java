package com.ecommerce.computerequipment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.computerequipment.entity.Proizvod;

@Repository
public interface ProizvodRepository extends JpaRepository<Proizvod, Integer>{
	
	Page<Proizvod> findAll(Specification<Proizvod> spec, Pageable pageable);
	
	Specification<Proizvod> searchByNaziv(String naziv);
	
	Specification<Proizvod> searchByMarkaId(Integer markaId);
	
	Specification<Proizvod> searchByTipId(Integer tipId);
	
	Specification<Proizvod> searchByTipIdAndMarkaId(Integer tipId, Integer markaId);
}
