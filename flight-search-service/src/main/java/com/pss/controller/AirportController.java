package com.pss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pss.entity.Airport;
import com.pss.service.AirportService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@CrossOrigin(origins = "*")
@Validated
@RequestMapping("/search")
public class AirportController {

	@Autowired
	private AirportService airportService;

	// Admin Service
	// Add Airport
	@PostMapping("/addAirport")
	public String addAirport(@RequestBody @Valid Airport newAirport) {
		return airportService.addAirport(newAirport);
	}

	// Admin Service
	// Add list of airports
	@PostMapping("/addAirports")
	public String addAirport(@RequestBody @Valid List<Airport> airports) {
		for (Airport airport : airports)
			airportService.addAirport(airport);
		return "Added successfully";
	}

	// Admin Service
	// Delete airport by code
	@DeleteMapping("/deleteAirport")
	public String removeAirport(
			@RequestParam("code") @Pattern(regexp = "^[A-Z]{3}$", message = "Airport code should be 3 characters long and should have only captial letters") String code) {
		return airportService.removeAirport(code);
	}

	// Admin Service
	// Update airport by airport code
	@PatchMapping("/updateAirport/{code}")
	public Airport updateAirport(
			@PathVariable("code") @Pattern(regexp = "^[A-Z]{3}$", message = "Airport code should be 3 characters long and should have only captial letters") String code,
			@RequestBody Airport updatedAirport) {
		return airportService.updateAirport(code, updatedAirport);
	}

	// Get airport by airport code
	@GetMapping("/getAirport/{code}")
	public Airport getAirport(
			@PathVariable("code") @Pattern(regexp = "^[A-Z]{3}$", message = "Airport code should be 3 characters long and should have only captial letters") String code) {
		return airportService.getAirportByCode(code).get();
	}

	// Get all airports
	@GetMapping("/getAllAirports")
	public List<Airport> getAllAirports() {
		return airportService.getAllAirports();
	}

}
