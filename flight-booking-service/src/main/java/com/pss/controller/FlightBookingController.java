package com.pss.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pss.entity.AdminBookingSearchRequest;
import com.pss.entity.BookFlight;
import com.pss.entity.NewBookingRequest;
import com.pss.service.BookFlightService;

import jakarta.validation.Valid;

@RestController
@Validated
@CrossOrigin(origins = "*")
@RequestMapping("/booking")
public class FlightBookingController {

	@Autowired
	private BookFlightService bookFlightService;

	// To create a new booking
	@PostMapping("/bookFlight")
	public BookFlight newFlightBooking(@RequestBody @Valid NewBookingRequest newBookingReq) {
		return bookFlightService.bookFlight(newBookingReq);
	}

	// To get booking by booking id
	@GetMapping("/getBooking/{bookingId}")
	public BookFlight getBooking(@PathVariable("bookingId") Long bookingId) {
		return bookFlightService.getBooking(bookingId);
	}

	// can cancel a booking
	@DeleteMapping("/cancelBooking/{bookingId}")
	public String cancelBooking(@PathVariable("bookingId") Long bookingId) {
		return bookFlightService.cancelBooking(bookingId);
	}

	// To get all the bookings. Admin can view all the bookings
	@GetMapping("/getAllBooking")
	public List<BookFlight> getAllBookings() {
		return bookFlightService.getAllBookings();
	}

	// To get booking by selected date. Admin can view all the bookings of a
	// particular date
	@GetMapping("/getBookingByDate")
	public List<BookFlight> getBookingByDate(@RequestBody LocalDate date) {
		return bookFlightService.getBookingByDate(date);
	}

	// To get all the booked seats
	@GetMapping("/getBookedSeats/{flightId}/{date}")
	public Collection<Integer> getBookedSeats(@PathVariable("flightId") Long flightId,
			@PathVariable("date") LocalDate date) {
		return bookFlightService.getBookedSeats(flightId, date);
	}

	// To get all booked flights between two cities on given date
	@GetMapping("/getBookings")
	public Collection<BookFlight> getBooking(@RequestBody Map<String, String> request) {
		return bookFlightService.getBooking(request.get("source"), request.get("destination"),
				LocalDate.parse(request.get("date")));
	}

	// To get passenger count for a flight
	@GetMapping("/getPassengerCount/{flightId}/{date}")
	public Integer getPassengerCount(@PathVariable("flightId") Long flightId, @PathVariable("date") LocalDate date) {
		return bookFlightService.getPassengerCount(flightId, date);
	}

	// Admin Booking search
	@PostMapping("/adminBookingSearch")
	public Collection<BookFlight> adminBookingSearch(@RequestBody AdminBookingSearchRequest request) {
		return bookFlightService.adminSearch(request);
	}
}
