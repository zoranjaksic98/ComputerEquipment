package com.ecommerce.computerequipment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.computerequipment.entity.Marka;
import com.ecommerce.computerequipment.model.MarkaResponse;
import com.ecommerce.computerequipment.repository.MarkaRepository;

@Service
public class MarkaServiceImpl implements MarkaService{
	
	private final MarkaRepository markaRepository;
	
	public MarkaServiceImpl(MarkaRepository markaRepository) 
	{
		this.markaRepository = markaRepository;
	}

	@Override
	public List<MarkaResponse> getAll() {
		System.out.println("Preuzimanje svih marki!");
		List<Marka> marke = markaRepository.findAll();
		List<MarkaResponse> markeResponse = marke.stream()
				.map(this::convertToMarkaResponse)
				.collect(Collectors.toList());
		System.out.println("Sve marke su preuzete!");
		return markeResponse;
	}
	
	private MarkaResponse convertToMarkaResponse(Marka marka) 
	{
		return MarkaResponse.builder()
				.id(marka.getId())
				.naziv(marka.getNaziv())
				.build();
	}

}
