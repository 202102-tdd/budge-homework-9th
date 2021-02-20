package com.example.budge.homework;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.temporal.ChronoUnit.DAYS;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    long getOverlappingDays(Budget budget) {
        LocalDate overlappingEnd = end.isBefore(budget.lastDay()) ? end : budget.lastDay();
        LocalDate overlappingStart = start.isAfter(budget.firstDay()) ? start : budget.firstDay();
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}
