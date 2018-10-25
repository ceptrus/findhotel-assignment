package com.findhotel.assignment.repository;

import com.findhotel.assignment.model.GeoLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GeoLocationRepository extends MongoRepository<GeoLocation, String> {
    Optional<GeoLocation> findByIpAddress(String ip);
}
