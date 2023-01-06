package com.pss.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "airport")
public class Airport {

	@Id
	@NotNull(message = "Airport code is required")
	@Pattern(regexp = "^[A-Z]{3}$", message = "Airport code should be 3 characters long and should have only captial letters")
	private String code;

	@NotNull(message = "Airport name is required")
	private String name;

	@NotNull(message = "Airport city is required")
	private String city;

	@NotNull(message = "Airport state is required")
	private String state;

	@NotNull(message = "Airport country is required")
	private String country;

	@NotNull(message = "Airport latitude is required")
	private Double lat;

	@NotNull(message = "Airport longitude is required")
	private Double lon;

	private Integer elev;

	public Airport() {
		super();
	}

	public Airport(String code, String name, String city, String state, String country, Double lat, Double lon,
			Integer elev) {
		super();
		this.code = code;
		this.name = name;
		this.city = city;
		this.state = state;
		this.country = country;
		this.lat = lat;
		this.lon = lon;
		this.elev = elev;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLon() {
		return lon;
	}

	public Integer getElev() {
		return elev;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public void setElev(Integer elev) {
		this.elev = elev;
	}

}
