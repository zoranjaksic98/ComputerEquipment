package com.ecommerce.computerequipment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.computerequipment.entity.Tip;
import com.ecommerce.computerequipment.model.TipResponse;
import com.ecommerce.computerequipment.repository.TipRepository;

@Service
public class TipServiceImpl implements TipService{

	private final TipRepository tipRepository;
	
	public TipServiceImpl(TipRepository tipRepository) 
	{
		this.tipRepository = tipRepository;
	}
	
	@Override
	public List<TipResponse> getAll() {
		System.out.println("Preuzimanje svih tipova.");
		List<Tip> tipovi = tipRepository.findAll();
		List<TipResponse> tipoviResponse = tipovi.stream()
				.map(this::convertToTipResponse)
				.collect(Collectors.toList());
		System.out.println("Svi tipovi su preuzeti.");
		return tipoviResponse;
	}
	
	private TipResponse convertToTipResponse(Tip tip) 
	{
		return TipResponse.builder()
				.id(tip.getId())
				.naziv(tip.getNaziv())
				.build();
	}

}
