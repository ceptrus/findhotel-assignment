package com.findhotel.assignment.repository;

import com.findhotel.assignment.model.GeoLocation;
import com.findhotel.assignment.model.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {
}
