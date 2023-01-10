package com.pss.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pss.entity.AddFlightRequest;
import com.pss.entity.AdminSearchRequest;
import com.pss.entity.Flight;
import com.pss.entity.SearchRequest;
import com.pss.entity.SearchResponse;
import com.pss.service.FlightService;

import jakarta.validation.Valid;

@RestController
@Validated
@CrossOrigin(origins = "*")
public class FlightController {

	@Autowired
	private FlightService flightService;

	// Admin can add flight
	@PostMapping("/addFlight")
	public Flight addFlight(@RequestBody @Valid AddFlightRequest newFlightRequest) {
		return flightService.addFlight(newFlightRequest);
	}

	// Admin remove flight
	@DeleteMapping("/removeFlight/{flightId}")
	public String removeFlight(@PathVariable("flightId") Long flightId) {
		return flightService.removeFlight(flightId);
	}

	// Admin can edit flight
	@PatchMapping("/editFlight")
	public Flight editFlight(@RequestBody @Valid Flight updatedFlight) {
		return flightService.editFlight(updatedFlight);
	}

	// To get flight by flightId
	@GetMapping("/getFlight/{flightId}")
	public Flight getFlightById(@PathVariable("flightId") Long flightId) {
		return flightService.getFlightById(flightId).get();
	}

	// Admin can get all flights
	@GetMapping("/getAllFlights")
	public List<Flight> getAllFlights() {
		return flightService.getAllFlight();
	}

	// To get flight by source and destination city
	@PostMapping("/search")
	public List<Flight> searchFlight(@RequestBody Map<String, String> airports) {
		return flightService.getFlightBySourceAndDestination(airports.get("source"), airports.get("destination"));
	}

	// To get flight by source, destination, date and no. of passenger
	@PostMapping("/userSearch")
	public List<SearchResponse> searchFlight(@RequestBody @Valid SearchRequest searchRequest) {
		return flightService.searchFlight(searchRequest);
	}

	// To get flight which runs in morning
	@GetMapping("/morningFlights")
	public List<Flight> morningFlights() {
		return flightService.getMorningFlights(null);
	}

	// To get flight which runs in afternoon
	@GetMapping("/afternoonFlights")
	public List<Flight> afternoonFlights() {
		return flightService.getAfternoonFlights(null);
	}

	// To get flight which runs in night
	@GetMapping("/nightFlights")
	public List<Flight> nightFlights() {
		return flightService.getNightFlights(null);
	}

	// Admin Search
	@PostMapping("/adminSearch")
	public List<Flight> adminSearch(@RequestBody AdminSearchRequest adminSearchRequest) {
		return flightService.adminSearch(adminSearchRequest);
	}

	// Get Distance for flight
	@GetMapping("/getDistance/{flightId}")
	public Double getDistance(@PathVariable("flightId") Long flightId) {
		return flightService.getFlightById(flightId).get().getDistance();
	}

}
