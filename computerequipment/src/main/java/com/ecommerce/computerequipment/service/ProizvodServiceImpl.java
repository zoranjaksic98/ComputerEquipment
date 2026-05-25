package com.ecommerce.computerequipment.service;

import java.util.List;
import java.util.stream.Collectors;

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
	public List<ProizvodResponse> getAll() {
		System.out.println("Preuzimanje svih proizvoda.");
		List<Proizvod> proizvodi = proizvodRepository.findAll();
		List<ProizvodResponse> proizvodiResponse = proizvodi.stream()
				.map(this::convertToProizvodResponse)
				.collect(Collectors.toList());
		System.out.println("Svi proizvodi su preuzeti.");
		return proizvodiResponse;
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
