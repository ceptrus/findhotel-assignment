package com.findhotel.assignment.service;

import com.findhotel.assignment.model.Statistics;
import com.findhotel.assignment.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public Statistics getStatistics() {
        return statisticsRepository.findAll().get(0);
    }
}
