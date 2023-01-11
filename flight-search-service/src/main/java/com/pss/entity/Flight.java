package com.pss.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "flight")
public class Flight {

	@Id
	@NotNull(message = "Flight ID is required")
	@GeneratedValue
	private Long flightId;

	@OneToOne
	@NotNull(message = "Flight source airport is required")
	private Airport source;

	@OneToOne()
	@NotNull(message = "Flight destination airport is required")
	private Airport destination;

	@NotNull(message = "Flight distance is required")
	private Double distance;

	@NotNull(message = "Flight departure time is required")
	private LocalTime departureTime;

	@NotNull(message = "Flight arrival time is required")
	private LocalTime arrivalTime;

	@Column(name = "flight_status")
	private Boolean FlightStatus;

	public Flight() {
		super();
	}

	public Flight(@NotNull(message = "Flight ID is required") Long flightId,
			@NotNull(message = "Flight source airport is required") Airport source,
			@NotNull(message = "Flight destination airport is required") Airport destination,
			@NotNull(message = "Flight distance is required") Double distance,
			@NotNull(message = "Flight departure time is required") LocalTime departureTime,
			@NotNull(message = "Flight arrival time is required") LocalTime arrivalTime, Boolean flightStatus) {
		super();
		this.flightId = flightId;
		this.source = source;
		this.destination = destination;
		this.distance = distance;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		FlightStatus = flightStatus;
	}

	public Long getFlightId() {
		return flightId;
	}

	public Airport getSource() {
		return source;
	}

	public Airport getDestination() {
		return destination;
	}

	public Double getDistance() {
		return distance;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public void setSource(Airport source) {
		this.source = source;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Boolean getFlightStatus() {
		return FlightStatus;
	}

	public void setFlightStatus(Boolean flightStatus) {
		FlightStatus = flightStatus;
	}

	@Override
	public String toString() {
		return "Flight [flightId=" + flightId + ", source=" + source + ", destination=" + destination + ", distance="
				+ distance + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", FlightStatus="
				+ FlightStatus + "]";
	}

}
