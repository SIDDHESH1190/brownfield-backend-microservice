package com.pss.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pss.entity.Flight;

@Service
public class FareService {

	@Autowired
	private RestTemplate restTemplate;

	public Integer getFare(Long flightId, LocalDate date) {
		Integer fare = 4000;

		Flight flight = restTemplate.getForObject("http://search-service/getFlight/" + flightId, Flight.class);

		if (flight != null) {
			fare = (int) Math.max(fare, flight.getDistance() * 50);
		}

		if (flight.getDepartureTime().isAfter(LocalTime.of(18, 00))
				&& flight.getDepartureTime().isBefore(LocalTime.of(23, 59))) {

			fare += 100;

		}

		if (Period.between(date, LocalDate.now()).getDays() <= 7) {
			fare += 100;
		}

		Integer passengerCount = restTemplate
				.getForEntity("http://booking-service/getPassengerCount/" + flightId + "/" + date, Integer.class)
				.getBody();

		if (passengerCount != null) {
			if (passengerCount >= 90) {
				fare += 300;
			} else if (passengerCount >= 70) {
				fare += 200;
			} else if (passengerCount >= 50) {
				fare += 100;
			}
		}
		return fare;
	}
}
