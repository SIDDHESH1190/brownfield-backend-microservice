package com.pss.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public class NewBookingRequest {
	
	@NotNull(message = "Flight Id is required to book.")
	private Long flightId;
	
	@NotNull(message = "Passenger Information is required to book.")
	private List<Passenger> passengerInfo;
	
	@NotNull(message = "Email is required to book.")
	private String email;
	
	@NotNull(message = "Mobile No. is required to book.")
	private String mobileNo;
	
//	@NotNull(message = "Total Cost is required to book.")
//	private Double totalCost;
	
	@NotNull(message = "Date Of Travelling is required to book.")
	private LocalDate dateOfTravelling;
	
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	public List<Passenger> getPassengerInfo() {
		return passengerInfo;
	}
	public void setPassengerInfo(List<Passenger> passengerInfo) {
		this.passengerInfo = passengerInfo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
//	public Double getTotalCost() {
//		return totalCost;
//	}
//	public void setTotalCost(Double totalCost) {
//		this.totalCost = totalCost;
//	}
	public LocalDate getDateOfTravelling() {
		return dateOfTravelling;
	}
	public void setDateOfTravelling(LocalDate dateOfTravelling) {
		this.dateOfTravelling = dateOfTravelling;
	}

	

}
