package com.ecommerce.computerequipment.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Marka")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Marka {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "Naziv")
	private String naziv;
	
	@OneToMany(mappedBy = "marka", fetch = FetchType.LAZY)
	private List<Proizvod> proizvodi;

}
