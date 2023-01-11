package com.pss.entity;

import java.time.LocalDate;

public class AdminBookingSearchRequest {

	private Long bookingId;
	private String sourceCode;
	private String destinationCode;
	private LocalDate date;
	private String time;

	public AdminBookingSearchRequest() {
		super();
	}

	public AdminBookingSearchRequest(Long bookingId, String sourceCode, String destinationCode, LocalDate date,
			String time) {
		super();
		this.bookingId = bookingId;
		this.sourceCode = sourceCode;
		this.destinationCode = destinationCode;
		this.date = date;
		this.time = time;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "AdminBookingSearchRequest [bookingId=" + bookingId + ", sourceCode=" + sourceCode + ", destinationCode="
				+ destinationCode + ", date=" + date + ", time=" + time + "]";
	}

}
