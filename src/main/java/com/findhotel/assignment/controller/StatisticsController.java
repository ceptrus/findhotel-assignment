package com.findhotel.assignment.controller;

import com.findhotel.assignment.model.Statistics;
import com.findhotel.assignment.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public Statistics getGeoLocationInfo() {
        return statisticsService.getStatistics();
    }
}
