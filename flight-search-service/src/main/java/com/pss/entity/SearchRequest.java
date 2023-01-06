package com.pss.entity;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SearchRequest {

	@NotNull(message = "Source airport is required.")
	private Airport source;

	@NotNull(message = "Destination airport is required.")
	private Airport destination;

	private LocalDate dateOfTravelling;

	@NotNull(message = "Passenger is required.")
	@Min(value = 1, message = "Minimum 1 passenger is required.")
	@Max(value = 10, message = "Maximum 10 passenger is allowed.")
	private Integer noOfPassenger;

	public SearchRequest() {
		super();
	}

	public SearchRequest(Airport source, Airport destination, LocalDate dateOfTravelling, Integer noOfPassenger) {
		super();
		this.source = source;
		this.destination = destination;
		this.dateOfTravelling = dateOfTravelling;
		this.noOfPassenger = noOfPassenger;
	}

	public Airport getSource() {
		return source;
	}

	public void setSource(Airport source) {
		this.source = source;
	}

	public Airport getDestination() {
		return destination;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	public LocalDate getDateOfTravelling() {
		return dateOfTravelling;
	}

	public void setDateOfTravelling(LocalDate dateOfTravelling) {
		this.dateOfTravelling = dateOfTravelling;
	}

	public Integer getNoOfPassenger() {
		return noOfPassenger;
	}

	public void setNoOfPassenger(Integer noOfPassenger) {
		this.noOfPassenger = noOfPassenger;
	}

	@Override
	public String toString() {
		return "SearchRequest [source=" + source + ", destination=" + destination + ", dateOfTravelling="
				+ dateOfTravelling + ", noOfPassenger=" + noOfPassenger + "]";
	}

}
