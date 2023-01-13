package com.pss.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pss.service.FareService;

@RestController
@RequestMapping("/fare")
public class FareController {

	@Autowired
	private FareService fareService;

	@GetMapping("/getFare/{flightId}/{date}")
	public Integer getFare(@PathVariable("flightId") Long flightId, @PathVariable("date") LocalDate date) {
		return fareService.getFare(flightId, date);
	}
}
