package com.pss.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pss.entity.AdminBookingSearchRequest;
import com.pss.entity.BookFlight;
import com.pss.entity.Flight;
import com.pss.entity.NewBookingRequest;
import com.pss.entity.Passenger;
import com.pss.repo.BookFlightRepo;

@Service
public class BookFlightService {

	@Autowired
	private BookFlightRepo bookFlightRepo;
	
	@Autowired
	private RestTemplate restTemplate;

	// To book a flight
	public BookFlight bookFlight(NewBookingRequest newBookingReq) {
		
		// Create new booking
		BookFlight newBooking = new BookFlight();
		
		// Get all the booked seats
		Collection<Integer> bookedSeats = bookFlightRepo.getBookedSeat(newBookingReq.getFlightId(),
				newBookingReq.getDateOfTravelling());

		// assign next available seat to passenger
		int i = 1;
		for (Passenger passenger : newBookingReq.getPassengerInfo()) {
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
		
		// Set Booking data
		newBooking.setFlight(restTemplate
				.getForEntity("http://search-service/search/getFlight/" + newBookingReq.getFlightId(), Flight.class).getBody());
		
//		newBooking.setFlight(new Flight());
		
		newBooking.setEmail(newBookingReq.getEmail());
		newBooking.setMobileNo(newBookingReq.getMobileNo());
//		newBooking.setTotalCost(newBookingReq.getTotalCost());
		newBooking.setDateOfBooking(LocalDateTime.now());
		newBooking.setDateOfTravelling(newBookingReq.getDateOfTravelling());
		newBooking.setPassengerInfo(newBookingReq.getPassengerInfo());
		newBooking.setNoOfPassenger(newBookingReq.getPassengerInfo().size());
		

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

	// Get bookings by time
	public Collection<BookFlight> getBookingByTime(String time, Collection<BookFlight> bookings) {

		if (time.equals("Morning")) {
			return getMorningBookings(bookings);
		}

		if (time.equals("Afternoon")) {
			return getAfternoonBookings(bookings);
		}

		return getNightBookings(bookings);

	}

	// Admin Booking search
//	public Collection<BookFlight> adminSearch(AdminBookingSearchRequest request) {
//		
//		System.out.println(LocalDateTime.now() + " Request = " + request);
//
//		if (request.getBookingId() != null) {
//			List<BookFlight> bookings = new ArrayList<>();
//			bookings.add(getBooking(request.getBookingId()));
//			return bookings;
//		}
//
//		if (request.getSourceCode() != null && request.getDestinationCode() != null
//				&& !request.getSourceCode().equals("") && !request.getDestinationCode().equals("")) {
//
//			if (request.getDate() != null && (request.getTime() == null || request.getTime().equals(""))) {
//				return getBooking(request.getSourceCode(), request.getDestinationCode(), request.getDate());
//			}
//
//			if (request.getDate() != null && (request.getTime() != null || !request.getTime().equals(""))) {
//				Collection<BookFlight> bookings = getBooking(request.getSourceCode(), request.getDestinationCode(),
//						request.getDate());
//				if(request.getTime() != null)
//				return getBookingByTime(request.getTime(), bookings);
//
//				return bookings;
//			}
//			return getBookings(request.getSourceCode(), request.getDestinationCode());
//		}
//
//		if (request.getDate() != null && request.getTime() == null) {
//			return getBookingByDate(request.getDate());
//		}
//
//		if (request.getDate() != null && request.getTime() != null && !request.getTime().equals("")) {
//			List<BookFlight> bookings = getBookingByDate(request.getDate());
//
//			return getBookingByTime(request.getTime(), bookings);
//
//		}
//
//		if (request.getTime() != null) {
//			return getBookingByTime(request.getTime(), getAllBookings());
//		}
//		return getAllBookings();
//	}
	
	public Collection<BookFlight> adminSearch(AdminBookingSearchRequest request){
		
		System.out.println(LocalDateTime.now() + " Request = " + request);
			
		// Get Booking if booking id is present
		if(request.getBookingId() != null) {
			List<BookFlight> bookings = new ArrayList<>(); 			bookings.add(getBooking(request.getBookingId()));
			return bookings;
		}
		
		// Get Booking if source and destination is present else are absent
		if(!request.getSourceCode().equals("") && !request.getDestinationCode().equals("") && request.getDate() == null && request.getTime().equals("")) {
			return getBookings(request.getSourceCode(), request.getDestinationCode());
		}
		
		// Get Booking if source, destination and date is present else are absent
		if(!request.getSourceCode().equals("") && !request.getDestinationCode().equals("") && request.getDate() != null && request.getTime().equals("")) {
			return getBooking(request.getSourceCode(), request.getDestinationCode(),request.getDate());
		}
		
		// If only date is present
		if(request.getSourceCode().equals("") && request.getDestinationCode().equals("") && request.getDate() != null && request.getTime().equals("")) {
			return getBookingByDate(request.getDate());
		}
		
		// Get Booking if everything is present
				if(!request.getSourceCode().equals("") && !request.getDestinationCode().equals("") && request.getDate() != null && !request.getTime().equals("")) {
					
					
					return getBookingByTime(request.getTime(), getBooking(request.getSourceCode(), request.getDestinationCode(),request.getDate()));
					
				}
				
		// if only time is present
		if(request.getSourceCode().equals("") && request.getDestinationCode().equals("") && request.getDate() == null && !request.getTime().equals("")) {
			return getBookingByTime(request.getTime(), getAllBookings());
		}
		
		// if source, destination and time is present
		if(!request.getSourceCode().equals("") && !request.getDestinationCode().equals("") && request.getDate() == null && !request.getTime().equals("")) {
			return getBookingByTime(request.getTime(), getBookings(request.getSourceCode(), request.getDestinationCode()));
		}
		
		
		return getAllBookings();
		
	}
}
