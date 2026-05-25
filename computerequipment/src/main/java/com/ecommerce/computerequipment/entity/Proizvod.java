package com.ecommerce.computerequipment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Proizvod")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Proizvod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "Naziv")
	private String naziv;
	
	@Column(name = "Opis", columnDefinition = "TEXT")
	private String opis;
	
	@Column(name = "Cena")
	private Long cena;
	
	@Column(name = "UrlSlike")
	private String urlSlike;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipProizvodaId", referencedColumnName = "Id")
	private Tip tip;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MarkaProizvodaId", referencedColumnName = "Id")
	private Marka marka;

}
