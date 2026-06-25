package com.ecommerce.computerequipment.entity.OrderAggregate;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="porudzbine")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "idKorpe")
	private String idKorpe;
	
	@Embedded
	private ShippingAddress adresa;
	
	@Column(name = "Datum_Porudzbine")
	private LocalDateTime datumPorudzbine = LocalDateTime.now();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "porudzbina")
	private List<OrderItem> porudzbine;
	
	@Column(name = "konacnaCena")
	private Double konacnaCena;
	
	@Column(name = "Naknada_Za_Dostavu")
	private Long naknadaZaDostavu;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "Status_Porudzbine")
	private OrderStatus statusPorudzbine = OrderStatus.Na_Cekanju;
	
	public Double getTotal() {
		return getKonacnaCena()+getNaknadaZaDostavu();
	}
 	

}
