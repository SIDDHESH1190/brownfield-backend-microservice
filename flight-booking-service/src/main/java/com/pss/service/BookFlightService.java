package com.pss.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pss.entity.AdminBookingSearchRequest;
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
				while (i < 101) {
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
	public Collection<BookFlight> getBooking(String source, String destination, LocalDate date) {
		return bookFlightRepo.getBooking(source, destination, date).stream().toList();
	}

	// Get passenger count
	public Integer getPassengerCount(Long flightId, LocalDate date) {
		return bookFlightRepo.getPassengerCount(flightId, date);
	}

	// Get Booking between two cities
	public Collection<BookFlight> getBookings(String source, String destination) {
		return bookFlightRepo.getBooking(source, destination);
	}

	// Get morning bookings
	public Collection<BookFlight> getMorningBookings(Collection<BookFlight> bookings) {
		return bookings.stream().filter((booking) -> {
			return booking.getFlight().getDepartureTime().isBefore(LocalTime.NOON);
		}).toList();
	}

	// Get Afternoon Bookings
	public Collection<BookFlight> getAfternoonBookings(Collection<BookFlight> bookings) {
		return bookings.stream().filter((booking) -> {
			return booking.getFlight().getDepartureTime().isAfter(LocalTime.NOON)
					&& booking.getFlight().getDepartureTime().isBefore(LocalTime.of(18, 00));
		}).toList();
	}

	// Get Night Bookings
	public Collection<BookFlight> getNightBookings(Collection<BookFlight> bookings) {
		return bookings.stream().filter((booking) -> {
			return booking.getFlight().getDepartureTime().isAfter(LocalTime.of(18, 00))
					&& booking.getFlight().getDepartureTime().isBefore(LocalTime.of(23, 59, 59));
		}).toList();
	}

	// Admin Booking search
	public Collection<BookFlight> adminSearch(AdminBookingSearchRequest request) {

		if (request.getBookingId() != null) {
			List<BookFlight> bookings = new ArrayList<>();
			bookings.add(getBooking(request.getBookingId()));
			return bookings;
		}

		if (request.getSourceCode() != null && request.getDestinationCode() != null
				&& !request.getSourceCode().equals("") && !request.getDestinationCode().equals("")) {

			if (request.getDate() != null && request.getTime() == null && request.getTime().equals("")) {
				return getBooking(request.getSourceCode(), request.getDestinationCode(), request.getDate());
			}

			if (request.getDate() != null && request.getTime() != null && !request.getTime().equals("")) {
				Collection<BookFlight> bookings = getBooking(request.getSourceCode(), request.getDestinationCode(),
						request.getDate());

				if (request.getTime().equals("Morning")) {
					return getMorningBookings(bookings);
				}

				if (request.getTime().equals("Afternoon")) {
					return getAfternoonBookings(bookings);
				}

				if (request.getTime().equals("Night")) {
					return getNightBookings(bookings);
				}

				return bookings;
			}
			return getBookings(request.getSourceCode(), request.getDestinationCode());
		}

		if (request.getDate() != null && request.getTime() == null) {
			return getBookingByDate(request.getDate());
		}

		if (request.getDate() != null && request.getTime() != null && !request.getTime().equals("")) {
			List<BookFlight> bookings = getBookingByDate(request.getDate());
			if (request.getTime().equals("Morning")) {
				return getMorningBookings(bookings);
			}

			if (request.getTime().equals("Afternoon")) {
				return getAfternoonBookings(bookings);
			}

			if (request.getTime().equals("Night")) {
				return getNightBookings(bookings);
			}

		}

		if (request.getTime() != null) {
			if (request.getTime().equals("Morning")) {
				return getMorningBookings(getAllBookings());
			}

			if (request.getTime().equals("Afternoon")) {
				return getAfternoonBookings(getAllBookings());
			}

			if (request.getTime().equals("Night")) {
				return getNightBookings(getAllBookings());
			}
		}
		return getAllBookings();
	}
}
