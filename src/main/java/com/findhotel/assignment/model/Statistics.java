package com.findhotel.assignment.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Statistics {
    private int totalResults;
    private int totalRepeated;
    private int totalDiscarded;
    private long importTime;
}
