package com.findhotel.assignment.reader;

import com.findhotel.assignment.model.GeoLocation;
import com.findhotel.assignment.model.Statistics;
import com.findhotel.assignment.repository.GeoLocationRepository;
import com.findhotel.assignment.repository.StatisticsRepository;
import com.google.common.collect.Iterators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class FileReader {
    private final static String SPLIT = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
    private Pattern pattern = Pattern.compile(SPLIT);

    @Autowired
    private GeoLocationRepository geoLocationRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Value("${file.location}")
    private String fileLocation;

    private AtomicInteger discarded = new AtomicInteger(0);
    private AtomicInteger accepted = new AtomicInteger(0);

    @PostConstruct
    public void init() throws IOException {
        log.info("Start importing the file");

        try (Stream<String> stream = Files.lines(Paths.get(fileLocation))) {
            long startTime = System.currentTimeMillis();

            List<GeoLocation> collect = stream.parallel()
                    .map(this::buildGeoLocation)
                    .filter(this::filterGeoLocation)
                    .map(Optional::get)
                    .distinct()
                    .collect(Collectors.toList());

            log.info("File parsed");
            log.info("Writing to DB");

            Iterators.partition(collect.iterator(), 10000)
                    .forEachRemaining(ts -> geoLocationRepository.saveAll(ts));

            log.info("Saving statistics");

            long endTime = System.currentTimeMillis();

            int repeated = accepted.get() - collect.size();
            int totalResults = accepted.get() - repeated;
            Statistics statistics = new Statistics(totalResults, repeated, discarded.get(), endTime - startTime);
            statisticsRepository.insert(statistics);
        } catch (IOException e) {
            log.error("Error parsing the file", e);
            throw e;
        }
    }

    private boolean filterGeoLocation(Optional<GeoLocation> geoLocation) {
        if (geoLocation.isPresent()) {
            accepted.incrementAndGet();
            return true;
        }
        discarded.incrementAndGet();
        return false;
    }

    private Optional<GeoLocation> buildGeoLocation(String s) {
        String[] split = s.split(",");

        if (split.length < 7 || split.length > 8) {
            return Optional.empty();
        }
        if (split.length == 8) {
            split = pattern.split(s);
        }

        String ip = split[0];
        String countryCode = split[1];
        String country = split[2];
        String city = split[3];
        String latitude = split[4];
        String longitude = split[5];
        String mystery = split[6];

        if (!isValidIP(ip)) {
            // Not valid IP
            return Optional.empty();
        }

        if (StringUtils.isEmpty(country) && StringUtils.isEmpty(countryCode)) {
            return Optional.empty();
        }

        GeoLocation builder = GeoLocation.builder()
                .ipAddress(ip)
                .city(city)
                .country(country)
                .countryCode(countryCode)
                .latitude(latitude)
                .longitude(longitude)
                .mysteryValue(mystery)
                .build();

        return Optional.of(builder);
    }

    private boolean isValidIP(String ipAddress) {
        Pattern pattern = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher matched = pattern.matcher(ipAddress);
        return matched.find();
    }
}
