package com.pss.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AddFlightRequest {
	
	@NotNull(message = "Flight source airport is required")
	@Pattern(regexp = "^[A-Z]{3}$", message = "Airport code should be 3 characters long and should have only captial letters")
	private String sourceCode;
	
	@NotNull(message = "Flight destination airport is required")
	@Pattern(regexp = "^[A-Z]{3}$", message = "Airport code should be 3 characters long and should have only captial letters")
	private String destinationCode;
	
	@NotNull(message = "Flight departure time is required")
	private String timeOfDeparture;
	
	@NotNull(message = "Flight arrival time is required")
	private String timeOfArrival;
	
	public AddFlightRequest() {
		super();
	}

	public AddFlightRequest(String sourceCode, String destinationCode, String timeOfDeparture, String timeOfArrival) {
		super();
		this.sourceCode = sourceCode;
		this.destinationCode = destinationCode;
		this.timeOfDeparture = timeOfDeparture;
		this.timeOfArrival = timeOfArrival;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public String getTimeOfDeparture() {
		return timeOfDeparture;
	}

	public void setTimeOfDeparture(String timeOfDeparture) {
		this.timeOfDeparture = timeOfDeparture;
	}

	public String getTimeOfArrival() {
		return timeOfArrival;
	}

	public void setTimeOfArrival(String timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}

	@Override
	public String toString() {
		return "AddFlightRequest [sourceCode=" + sourceCode + ", destinationCode=" + destinationCode
				+ ", timeOfDeparture=" + timeOfDeparture + ", timeOfArrival=" + timeOfArrival + "]";
	}
	
	
	
	

}
