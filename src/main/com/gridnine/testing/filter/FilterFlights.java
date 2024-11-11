package main.com.gridnine.testing.filter;

import main.com.gridnine.testing.model.Flight;
import main.com.gridnine.testing.model.Segment;
import main.com.gridnine.testing.model.Rule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterFlights {

    public FilterFlights() {
    }

    public List<Flight> filter(Rule rule, List<Flight> flights) {
        List<Flight> filteringFlights = new ArrayList<>();
        switch (rule) {
            case DEPARTURE_WAS_TO_THE_CURRENT_POINT_IN_TIME:
                filteringFlights = departureWasToTheCurrentPointInTime(flights);
                break;
            case SEGMENTS_WITH_AN_ARRIVAL_DATE_EARLIER_THAN_THE_DEPARTURE_DATE:
                filteringFlights = segmentsWithAnArrivalDateEarlierThanTheDepartureDate(flights);
                break;
            case FLIGHTS_WHERE_THE_TOTAL_TIME_SPENT_ON_THE_GROUND_EXCEEDS_TWO_HOURS:
                filteringFlights = flightsWhereTheTotalTimeSpentOnTheGroundExceedsTwoHours(flights);
                break;
            default:
                throw new IllegalArgumentException("Rule must be define!");
        }

        return filteringFlights;
    }

    private List<Flight> departureWasToTheCurrentPointInTime(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {
                    Duration duration = Duration.between(
                            flight.getSegments().stream()
                                    .map(Segment::getArrivalDate)
                                    .min(LocalDateTime::compareTo)
                                    .orElse(LocalDateTime.now())
                            , LocalDateTime.now());
                    return duration.getSeconds() > 0;
                })
                .collect(Collectors.toList());
    }

    private List<Flight> segmentsWithAnArrivalDateEarlierThanTheDepartureDate(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {

                    List<Segment> segments = flight.getSegments();
                    Duration durationTotal = Duration.ZERO;
                    for (int i = 0; i < segments.size(); i++) {
                        Duration duration = Duration.between(segments.get(i).getDepartureDate(), segments.get(i).getArrivalDate());
                        if (duration.getSeconds() > 0) {
                            return true;
                        }
                    }
                    return false;

                })
                .collect(Collectors.toList());
    }

    private List<Flight> flightsWhereTheTotalTimeSpentOnTheGroundExceedsTwoHours(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream().count() > 1) // more than 1 segment flight
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    Duration durationTotal = Duration.ZERO;
                    for (int i = 0; i < segments.size() - 1; i++) {
                        Duration duration = Duration.between(segments.get(i).getArrivalDate(), segments.get(i + 1).getDepartureDate());
                        durationTotal = durationTotal.plus(duration);
                    }
                    return durationTotal.toHours() > 2;
                })
                .collect(Collectors.toList())
                ;
    }
}
