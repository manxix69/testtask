package test.com.gridnine.testing;

import main.com.gridnine.testing.filter.FilterFlights;
import main.com.gridnine.testing.model.Flight;
import main.com.gridnine.testing.model.Rule;
import main.com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class FilterFlightsTest {
    private List<Flight> flights;
    private Flight flight;
    private List<Segment> segments;

    private Segment segment1;
    private Segment segment2;
    private Segment segment3;

    @BeforeEach
    void init() {
        flights = new ArrayList<>();
        segment1 = new Segment(
                LocalDateTime.of(2024, 10, 1, 10, 1),
                LocalDateTime.of(2024, 10, 1, 15, 12)
        );

        segment2 = new Segment(
                LocalDateTime.of(2029, 10, 1, 18, 19),
                LocalDateTime.of(2029, 10, 1, 23, 23)
        );

        segment3 = new Segment(
                LocalDateTime.of(2099, 10, 3, 10, 29),
                LocalDateTime.of(2099, 10, 2, 15, 23)
        );






    }

    @org.junit.jupiter.api.Test
    void filter_DEPARTURE_WAS_TO_THE_CURRENT_POINT_IN_TIME() {
        FilterFlights filterFlights = new FilterFlights();

        segments = new ArrayList<>();
        segments.add(segment1);
        segments.add(segment2);
        segments.add(segment3);
        flight = new Flight(segments);

        flights.add(flight);
        segments = new ArrayList<>();
        segments.add(segment1);
        flight = new Flight(segments);
        flights.add(flight);

        Assertions.assertEquals(2 , filterFlights.filter(Rule.DEPARTURE_WAS_TO_THE_CURRENT_POINT_IN_TIME, flights).size());
    }

    @org.junit.jupiter.api.Test
    void filter_SEGMENTS_WITH_AN_ARRIVAL_DATE_EARLIER_THAN_THE_DEPARTURE_DATE() {
        FilterFlights filterFlights = new FilterFlights();

        segments = new ArrayList<>();
        segments.add(segment1);
        segments.add(segment2);
        flight = new Flight(segments);
        flights.add(flight);

        segments = new ArrayList<>();
        segments.add(segment2);
        segments.add(segment3);
        flight = new Flight(segments);
        flights.add(flight);

        segments = new ArrayList<>();
        segments.add(segment3);
        flight = new Flight(segments);
        flights.add(flight);

        Assertions.assertEquals(2 , filterFlights.filter(Rule.SEGMENTS_WITH_AN_ARRIVAL_DATE_EARLIER_THAN_THE_DEPARTURE_DATE, flights).size());
    }

    @org.junit.jupiter.api.Test
    void filter_FLIGHTS_WHERE_THE_TOTAL_TIME_SPENT_ON_THE_GROUND_EXCEEDS_TWO_HOURS() {
        FilterFlights filterFlights = new FilterFlights();

        segments = new ArrayList<>();
        segments.add(segment3);
        flight = new Flight(segments);
        flights.add(flight);

        segments = new ArrayList<>();
        segments.add(segment1);
        segments.add(segment3);
        flight = new Flight(segments);
        flights.add(flight);

        Assertions.assertEquals(1 , filterFlights.filter(Rule.FLIGHTS_WHERE_THE_TOTAL_TIME_SPENT_ON_THE_GROUND_EXCEEDS_TWO_HOURS, flights).size());
    }
}