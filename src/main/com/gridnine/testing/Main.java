package main.com.gridnine.testing;


import main.com.gridnine.testing.filter.FilterFlights;

import main.com.gridnine.testing.model.Rule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        // из тестового класса берем сгенерированные полеты
        FilterFlights filterFlights = new FilterFlights();
        List<Flight> flightsFromBuilder = FlightBuilder.createFlights();

        // преобразуем в свой список полетов
        List<main.com.gridnine.testing.model.Flight> flightList = flightsFromBuilder.stream()
                .map(flight ->
                        new main.com.gridnine.testing.model.Flight(flight.getSegments()
                                .stream()
                                .map(segment -> new main.com.gridnine.testing.model.Segment(segment.getDepartureDate(), segment.getArrivalDate()))
                                .collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());


        System.out.println("flights");
        System.out.println(flightList);

        System.out.println("LocalDateTime.now()=" + LocalDateTime.now());
        System.out.println("Вылет до текущего момента времени:");
        System.out.println(filterFlights.filter(Rule.DEPARTURE_WAS_TO_THE_CURRENT_POINT_IN_TIME, flightList));

        System.out.println("Сегменты с датой прилёта раньше даты вылета:");
        System.out.println(filterFlights.filter(Rule.SEGMENTS_WITH_AN_ARRIVAL_DATE_EARLIER_THAN_THE_DEPARTURE_DATE, flightList));

        System.out.println("Перелеты, где общее время, проведённое на земле, превышает два часа:");
        System.out.println(filterFlights.filter(Rule.FLIGHTS_WHERE_THE_TOTAL_TIME_SPENT_ON_THE_GROUND_EXCEEDS_TWO_HOURS, flightList));

    }
}
