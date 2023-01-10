package com.pss.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pss.entity.BookFlight;
import com.pss.entity.Passenger;
import com.pss.repo.BookFlightRepo;

@Service
public class BookFlightService {

	@Autowired
	private BookFlightRepo bookFlightRepo;

	// To book a flight
	public BookFlight bookFlight(BookFlight newBooking) {

		// Get all the booked seats
		Collection<Integer> bookedSeats = bookFlightRepo.getBookedSeat(newBooking.getFlight().getFlightId(),
				newBooking.getDateOfTravelling());

		// assign next available seat to passenger
		int i = 1;
		for (Passenger passenger : newBooking.getPassengerInfo()) {
			if (passenger.getSeatNo() == null) {
				while(i < 101) {
					if (!bookedSeats.contains(i)) {
						passenger.setSeatNo(i);
						bookedSeats.add(i);
						break;
					}
					i++;
				}
			} else {
				if (bookedSeats.contains(passenger.getSeatNo())) {
					// Throws error here
					throw new RuntimeException("Seat no. " + passenger.getSeatNo() + " is already booked.");
				}
			}
		}

		// set date of booking as now
		newBooking.setDateOfBooking(LocalDateTime.now());

		return bookFlightRepo.saveAndFlush(newBooking);
	}

	// Delete booking
	public String cancelBooking(Long bookingId) {
		if (!bookFlightRepo.existsById(bookingId)) {
			throw new RuntimeException("Booking with ID : " + bookingId + " doesn't exist");
		}

		bookFlightRepo.deleteById(bookingId);
		return "Booking removed successfully";
	}

	// To get booking
	public BookFlight getBooking(Long bookingId) {
		if (!bookFlightRepo.existsById(bookingId)) {
			throw new RuntimeException("Booking with ID : " + bookingId + " doesn't exist");
		}
		return bookFlightRepo.findById(bookingId).get();
	}

	// Get All the bookings
	public List<BookFlight> getAllBookings() {
		return bookFlightRepo.findAll();
	}

	// Get booking by date
	public List<BookFlight> getBookingByDate(LocalDate date) {
		return bookFlightRepo.getBookingByDate(date);
	}

	// Get booked seats
	public Collection<Integer> getBookedSeats(Long flightId, LocalDate date) {
		return bookFlightRepo.getBookedSeat(flightId, date);
	}

	// Get booked flights between two cities on given date
	public List<BookFlight> getBookedFlights(String source, String destination, LocalDate date) {
		return bookFlightRepo.getBookedFlights(source, destination, date).stream().toList();
	}

	// Get passenger count
	public Integer getPassengerCount(Long flightId, LocalDate date) {
		return bookFlightRepo.getPassengerCount(flightId, date);
	}
}
