package com.ecommerce.computerequipment.entity.OrderAggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@Table(name="OrderItem")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Embedded
	private ProductItemOrdered naruceniProizvod;
	
	@Column
	private Long cena;
	
	@Column
	private Integer kolicina;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_Id")
	private Order porudzbina;
}
