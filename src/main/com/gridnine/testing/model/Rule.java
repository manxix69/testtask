package main.com.gridnine.testing.model;

public enum Rule {
    DEPARTURE_WAS_TO_THE_CURRENT_POINT_IN_TIME,
    SEGMENTS_WITH_AN_ARRIVAL_DATE_EARLIER_THAN_THE_DEPARTURE_DATE,
    FLIGHTS_WHERE_THE_TOTAL_TIME_SPENT_ON_THE_GROUND_EXCEEDS_TWO_HOURS
}
