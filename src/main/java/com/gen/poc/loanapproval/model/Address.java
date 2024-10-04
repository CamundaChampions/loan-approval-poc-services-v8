package com.gen.poc.loanapproval.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Access(AccessType.FIELD)
public class Address {
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "street")
	private String street;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "zipcode")
	private String zipCode;

	@Column(name = "country")
	private String country;

	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	private Customer customer;

}
