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
        LocalDate overlappingEnd;
        overlappingEnd = end.isBefore(budget.lastDay()) ? end : budget.lastDay();
        LocalDate overlappingStart = start.isAfter(budget.firstDay()) ? start : budget.firstDay();
        if (budget.getYearMonth().equals(start.format(ofPattern("yyyyMM")))) {
//            overlappingEnd = end.isBefore(budget.lastDay()) ? end : budget.lastDay();
        } else if (budget.getYearMonth().equals(end.format(ofPattern("yyyyMM")))) {
//            overlappingEnd = end.isBefore(budget.lastDay()) ? end : budget.lastDay();
        } else {
//            overlappingEnd = end.isBefore(budget.lastDay()) ? end : budget.lastDay();
        }
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}
