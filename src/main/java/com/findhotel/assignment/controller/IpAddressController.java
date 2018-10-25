package com.findhotel.assignment.controller;

import com.findhotel.assignment.model.GeoLocation;
import com.findhotel.assignment.service.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IpAddressController extends RuntimeException {

    @Autowired
    private GeoLocationService geoLocationService;

    @RequestMapping(value = "/ip/{ipAddress}", method = RequestMethod.GET)
    public GeoLocation getGeoLocationInfo(@PathVariable String ipAddress) {
        return geoLocationService.findByIP(ipAddress);
    }

    @ExceptionHandler(IllegalArgumentException.class )
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<GeoLocation> handleException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
