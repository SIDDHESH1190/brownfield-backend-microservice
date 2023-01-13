package com.pss.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Passenger {

	@Id
	@GeneratedValue
	private Long passengerId;

	@NotNull(message = "Passenger's first name is required.")
	private String firstName;

	@NotNull(message = "Passenger's last name is required.")
	private String lastName;

//	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Min(value = 1, message = "Seat no. 1 is the first seat")
	@Max(value = 100, message = "Seat no. 120 is the last seat")
	private Integer seatNo;

	public Passenger() {
		super();
	}

	public Passenger(Long passengerId, String firstName, String lastName, Gender gender, Integer seatNo) {
		super();
		this.passengerId = passengerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.seatNo = seatNo;
	}

	public Long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Integer getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
	}

	@Override
	public String toString() {
		return "Passenger [passengerId=" + passengerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", gender=" + gender + ", seatNo=" + seatNo + "]";
	}

}
