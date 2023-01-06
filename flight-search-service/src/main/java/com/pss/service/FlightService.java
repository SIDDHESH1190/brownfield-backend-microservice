package com.pss.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pss.entity.Airport;
import com.pss.entity.Flight;
import com.pss.entity.SearchRequest;
import com.pss.repo.FlightRepo;

@Service
public class FlightService {
	// Function to calculate distance between two airports
	public static double distance(double lat1, double lat2, double lon1, double lon2) {

		// The math module contains a function
		// named toRadians which converts from
		// degrees to radians.
		lon1 = Math.toRadians(lon1);
		lon2 = Math.toRadians(lon2);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// Haversine formula
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

		double c = 2 * Math.asin(Math.sqrt(a));

		// Radius of earth in kilometers. Use 3956
		// for miles
		double r = 6371;

		// calculate the result
		return (c * r);
	}

	@Autowired
	private FlightRepo flightRepo;

	@Autowired
	RestTemplate restTemplate;

//	@Autowired
//	private BookFlightRepo bookFlightRepo;

	// Admin Service
	// Add Flight
	public Flight addFlight(Flight newFlight) {

		// Calculate the distance between two airports
		newFlight.setDistance(distance(newFlight.getSource().getLat(), newFlight.getDestination().getLat(),
				newFlight.getSource().getLon(), newFlight.getDestination().getLon()));

		// Set DepartureTime
		newFlight.setDepartureTime(LocalTime.parse(newFlight.getTimeOfDeparture(), DateTimeFormatter.ISO_TIME));

		// Set ArrivalTime
		newFlight.setArrivalTime(LocalTime.parse(newFlight.getTimeOfArrival(), DateTimeFormatter.ISO_TIME));

		return flightRepo.saveAndFlush(newFlight);
	}

	// Admin Service
	// Get flight by flightId
	public Optional<Flight> getFlightById(Long flightId) {
		if (!flightRepo.existsById(flightId)) {
			throw new RuntimeException("Flight with flight Id : " + flightId + " is not found.");
		}

		return flightRepo.findById(flightId);
	}

	// Admin Service
	// Get all flight data
	public List<Flight> getAllFlight() {
		return flightRepo.findAll();
	}

	// Admin Service
	// Delete flight by flight Id
	public String removeFlight(Long flightId) {
		if (!flightRepo.existsById(flightId)) {
			throw new RuntimeException("Flight with flight Id : " + flightId + " is not found.");
		}

		flightRepo.deleteById(flightId);
		return "Flight removed successfully";
	}

	// Admin Service
	// Edit Flight
	public Flight editFlight(Flight updatedFlight) {
		if (!flightRepo.existsById(updatedFlight.getFlightId())) {
			throw new RuntimeException("Flight with flight Id : " + updatedFlight.getFlightId() + " is not found.");
		}

		// Calculate distance between two airports
		updatedFlight.setDistance(distance(updatedFlight.getSource().getLat(), updatedFlight.getDestination().getLat(),
				updatedFlight.getSource().getLon(), updatedFlight.getDestination().getLon()));

		// Set DepartureTime
		updatedFlight.setDepartureTime(LocalTime.parse(updatedFlight.getTimeOfDeparture(), DateTimeFormatter.ISO_TIME));

		// Set ArrivalTime
		updatedFlight.setArrivalTime(LocalTime.parse(updatedFlight.getTimeOfArrival(), DateTimeFormatter.ISO_TIME));

		return flightRepo.saveAndFlush(updatedFlight);

	}

	// Get flight which depart in morning
	public List<Flight> getMorningFlights() {
		return flightRepo.findAll().stream().filter(flight -> flight.getDepartureTime().isBefore(LocalTime.NOON))
				.toList();

	}

	// Get flight which depart in afternoon
	public List<Flight> getAfternoonFlights() {

		return flightRepo.findAll().stream().filter(flight -> flight.getDepartureTime().isAfter(LocalTime.NOON)
				&& flight.getDepartureTime().isBefore(LocalTime.of(18, 00))).toList();
	}

	// Get flight which depart in night
	public List<Flight> getNightFlights() {

		return flightRepo.findAll().stream().filter(flight -> flight.getDepartureTime().isAfter(LocalTime.of(18, 00))
				&& flight.getDepartureTime().isBefore(LocalTime.of(23, 59, 59))).toList();
	}

	// Get flight based on source and destination
	public List<Flight> getFlightBySourceAndDestination(Airport source, Airport destination) {
		return flightRepo.findBySourceAndDestination(source, destination);
	}

	// Get flight by source, destination, date and no. of passenger
	public List<Flight> searchFlight(SearchRequest searchRequest) {

		return flightRepo.findBySourceAndDestination(searchRequest.getSource(), searchRequest.getDestination()).stream()
				.filter((flight) -> {
					Integer passengerCount = restTemplate.getForEntity("http://booking-service/getPassengerCount/"
							+ flight.getFlightId() + "/" + searchRequest.getDateOfTravelling(), Integer.class)
							.getBody();

					if (passengerCount == null) {
						passengerCount = 0;
					}
					return passengerCount + searchRequest.getNoOfPassenger() <= 100;
				}).toList();

	}

}
