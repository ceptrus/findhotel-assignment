package com.findhotel.assignment.service;

import com.findhotel.assignment.model.GeoLocation;
import com.findhotel.assignment.repository.GeoLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeoLocationService {

    @Autowired
    private GeoLocationRepository geoLocationRepository;

    public GeoLocation findByIP(String ip) {
        return geoLocationRepository.findByIpAddress(ip)
                .orElseThrow(() -> new IllegalArgumentException("IP not found."));
    }

}
