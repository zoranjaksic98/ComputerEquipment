package com.ecommerce.computerequipment.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ecommerce.computerequipment.entity.Proizvod;
import com.ecommerce.computerequipment.model.ProizvodResponse;
import com.ecommerce.computerequipment.repository.ProizvodRepository;

@Service
public class ProizvodServiceImpl implements ProizvodService{
	
	private final ProizvodRepository proizvodRepository;
	
	public ProizvodServiceImpl(ProizvodRepository proizvodRepository) 
	{
		this.proizvodRepository = proizvodRepository;
	}

	@Override
	public ProizvodResponse getOneById(Integer proizvodId) {
		System.out.println("Preuzimanje proizvoda po sa Id-em: " + proizvodId);
		Proizvod proizvod = proizvodRepository.findById(proizvodId)
				.orElseThrow(()->new RuntimeException("Proizvod ne postoji!"));
		ProizvodResponse proizvodResponse = convertToProizvodResponse(proizvod);
		System.out.println("Proizvod je preuzet!");
		return proizvodResponse;
	}

	@Override
	public Page<ProizvodResponse> getAll(Pageable pageable, Integer markaId, Integer tipId, String kljucnaRec) {
		System.out.println("Preuzimanje svih proizvoda.");
		Specification<Proizvod> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
		if(markaId != null) 
		{
			spec = spec.and((root, query, criteriaBuilder)->criteriaBuilder.equal(root.get("marka").get("id"), markaId));
		}
		if(tipId != null) 
		{
			spec = spec.and((root, query, criteriaBuilder)->criteriaBuilder.equal(root.get("tip").get("id"), tipId));
		}
		if(kljucnaRec != null && !kljucnaRec.isEmpty())
		{
			spec = spec.and((root, query, criteriaBuilder)->criteriaBuilder.like(root.get("naziv"), "%" + kljucnaRec + "%"));
		}
		System.out.println("Svi proizvodi su preuzeti.");
		
		return proizvodRepository.findAll(spec, pageable).map(this::convertToProizvodResponse);
	}
	
	public ProizvodResponse convertToProizvodResponse(Proizvod proizvod) 
	{
		return ProizvodResponse.builder()
				.id(proizvod.getId())
				.naziv(proizvod.getNaziv())
				.opis(proizvod.getOpis())
				.cena(proizvod.getCena())
				.urlSlike(proizvod.getUrlSlike())
				.nazivTipa(proizvod.getTip().getNaziv())
				.nazivMarke(proizvod.getMarka().getNaziv())
				.build();
	}

}
