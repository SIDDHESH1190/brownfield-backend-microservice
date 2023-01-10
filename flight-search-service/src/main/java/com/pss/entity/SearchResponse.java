package com.pss.entity;

public class SearchResponse {

	private Flight flight;
	private Integer fare;

	public SearchResponse() {
		super();
	}

	public SearchResponse(Flight flight, Integer fare) {
		super();
		this.flight = flight;
		this.fare = fare;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Integer getFare() {
		return fare;
	}

	public void setFare(Integer fare) {
		this.fare = fare;
	}

}
