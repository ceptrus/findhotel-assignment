package com.findhotel.assignment.model;

import lombok.*;

import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeoLocation {
    private String ipAddress;
    private String countryCode;
    private String country;
    private String city;
    private String latitude;
    private String longitude;
    private String mysteryValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoLocation that = (GeoLocation) o;
        return Objects.equals(ipAddress, that.ipAddress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ipAddress);
    }
}
