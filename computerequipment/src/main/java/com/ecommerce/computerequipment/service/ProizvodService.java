package com.ecommerce.computerequipment.service;

import java.util.List;

import com.ecommerce.computerequipment.model.ProizvodResponse;

public interface ProizvodService {
	
	ProizvodResponse getOneById(Integer proizvodId);
	List<ProizvodResponse> getAll();

}
