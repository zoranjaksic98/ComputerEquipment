package com.ecommerce.computerequipment.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	// Metoda za dobavljanje svih proizvoda
	/*
	@GetMapping()
	public ResponseEntity<List<ProizvodResponse>> getAllProducts()
	{
		List<ProizvodResponse> proizvodiResponse = proizvodService.getAll();
		return new ResponseEntity<>(proizvodiResponse, HttpStatus.OK);
	}
	*/
	
	// Metoda za dobavljanje deset po deset proizvoda
	@GetMapping()
	public ResponseEntity<Page<ProizvodResponse>> getAllProducts(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "kljucnaRec", required = false) String kljucnaRec,
			@RequestParam(name = "tipId", required = false) Integer tipId,
			@RequestParam(name = "markaId", required = false) Integer markaId,
			@RequestParam(name = "sort", defaultValue = "naziv") String sort,
			@RequestParam(name = "order", defaultValue = "asc") String order)
	{
		Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC: Sort.Direction.ASC;
		Sort sorting = Sort.by(direction, sort);
		Pageable pageable = PageRequest.of(page, size, sorting);
		Page<ProizvodResponse> proizvodiResponse = proizvodService.getAll(pageable, markaId, tipId, kljucnaRec);
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
