package com.findhotel.assignment.reader;

import com.findhotel.assignment.model.GeoLocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SuppressWarnings("unchecked")
public class FileReaderTest {

    @InjectMocks
    private FileReader fileReader = new FileReader();

    @Test
    public void buildGeoLocation1() {
        String s = "200.106.141.15,SI,Nepal,DuBuquemouth,-84.87503094689836,7.206435933364332,7823011346";
        Optional<GeoLocation> buildGeoLocation = ReflectionTestUtils.invokeMethod(fileReader, "buildGeoLocation", s);
        assertTrue(buildGeoLocation.isPresent());
        GeoLocation geoLocation = buildGeoLocation.get();
        assertEquals("200.106.141.15", geoLocation.getIpAddress());
        assertEquals("SI", geoLocation.getCountryCode());
        assertEquals("Nepal", geoLocation.getCountry());
        assertEquals("DuBuquemouth", geoLocation.getCity());
        assertEquals("-84.87503094689836", geoLocation.getLatitude());
        assertEquals("7.206435933364332", geoLocation.getLongitude());
        assertEquals("7823011346", geoLocation.getMysteryValue());
    }

    @Test
    public void buildGeoLocation2() {
        String s = "160.103.7.140,PY,\"Falkland Islands, (Malvinas)\",,75.41685191518815,-144.6943217219469,0";
        Optional<GeoLocation> buildGeoLocation = ReflectionTestUtils.invokeMethod(fileReader, "buildGeoLocation", s);
        assertTrue(buildGeoLocation.isPresent());
        GeoLocation geoLocation = buildGeoLocation.get();
        assertEquals("160.103.7.140", geoLocation.getIpAddress());
        assertEquals("PY", geoLocation.getCountryCode());
        assertEquals("\"Falkland Islands, (Malvinas)\"", geoLocation.getCountry());
        assertEquals("", geoLocation.getCity());
        assertEquals("75.41685191518815", geoLocation.getLatitude());
        assertEquals("-144.6943217219469", geoLocation.getLongitude());
        assertEquals("0", geoLocation.getMysteryValue());
    }

    @Test
    public void buildGeoLocation3() {
        String s = ",SI,Nepal,DuBuquemouth,-84.87503094689836,7.206435933364332,7823011346";
        Optional<GeoLocation> buildGeoLocation = ReflectionTestUtils.invokeMethod(fileReader, "buildGeoLocation", s);
        assertFalse(buildGeoLocation.isPresent());
    }

    @Test
    public void buildGeoLocation4() {
        String s = "123456789,SI,Nepal,DuBuquemouth,-84.87503094689836,7.206435933364332,7823011346";
        Optional<GeoLocation> buildGeoLocation = ReflectionTestUtils.invokeMethod(fileReader, "buildGeoLocation", s);
        assertFalse(buildGeoLocation.isPresent());
    }
}