package com.ecommerce.computerequipment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.computerequipment.model.MarkaResponse;
import com.ecommerce.computerequipment.model.ProizvodResponse;
import com.ecommerce.computerequipment.model.TipResponse;
import com.ecommerce.computerequipment.service.MarkaService;
import com.ecommerce.computerequipment.service.ProizvodService;
import com.ecommerce.computerequipment.service.TipService;

@RestController
@RequestMapping("/api/proizvodi")
public class ProizvodController {
	
	private final ProizvodService proizvodService;
	
	private final TipService tipService;
	
	private final MarkaService markaService;
	
	public ProizvodController(ProizvodService proizvodService, TipService tipService, MarkaService markaService) 
	{
		this.proizvodService = proizvodService;
		this.tipService = tipService;
		this.markaService = markaService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProizvodResponse> getOneProductById(@PathVariable("id")Integer proizvodId)
	{
		ProizvodResponse proizvodResponse = proizvodService.getOneById(proizvodId);
		return new ResponseEntity<>(proizvodResponse, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<ProizvodResponse>> getAllProducts()
	{
		List<ProizvodResponse> proizvodiResponse = proizvodService.getAll();
		return new ResponseEntity<>(proizvodiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/tipovi")
	public ResponseEntity<List<TipResponse>> getAllTypes()
	{
		List<TipResponse> tipoviResponse = tipService.getAll();
		return new ResponseEntity<>(tipoviResponse, HttpStatus.OK);
	}
	
	@GetMapping("/marke")
	public ResponseEntity<List<MarkaResponse>> getAllBrands()
	{
		List<MarkaResponse> markeResponse = markaService.getAll();
		return new ResponseEntity<>(markeResponse, HttpStatus.OK);
	}
}
