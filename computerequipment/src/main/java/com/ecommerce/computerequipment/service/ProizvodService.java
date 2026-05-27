package com.ecommerce.computerequipment.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.computerequipment.model.ProizvodResponse;

public interface ProizvodService {
	
	ProizvodResponse getOneById(Integer proizvodId);
	Page<ProizvodResponse> getAll(Pageable pageable, Integer markaId, Integer tipId, String kljucnaRec);

}
