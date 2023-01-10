package com.pss.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pss.entity.Flight;

@Repository
public interface FlightRepo extends JpaRepository<Flight, Long> {

	@Query("SELECT f FROM Flight f WHERE f.source.code = ?1 and f.destination.code = ?2")
	public List<Flight> findBySourceAndDestination(String source, String destination);

}
