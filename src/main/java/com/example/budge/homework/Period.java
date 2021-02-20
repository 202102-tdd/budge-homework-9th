package com.example.budge.homework;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.temporal.ChronoUnit.DAYS;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    long getOverlappingDays(Period another) {
        if (end.isBefore(another.start) || start.isAfter(another.end)) {
            return 0;
        }
        LocalDate overlappingEnd = end.isBefore(another.end) ? end : another.end;
        LocalDate overlappingStart = start.isAfter(another.start) ? start : another.start;
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}
