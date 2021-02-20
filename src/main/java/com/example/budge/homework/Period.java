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

    long getOverlappingDays(Budget budget) {
        long overlappingDays;
        if (budget.getYearMonth().equals(start.format(ofPattern("yyyyMM")))) {
            LocalDate overlappingEnd = budget.lastDay();
            LocalDate overlappingStart = start;
            overlappingDays = DAYS.between(overlappingStart, overlappingEnd) + 1;
        } else if (budget.getYearMonth().equals(end.format(ofPattern("yyyyMM")))) {
            LocalDate overlappingStart = budget.firstDay();
            LocalDate overlappingEnd = end;
            overlappingDays = DAYS.between(overlappingStart, overlappingEnd) + 1;
        } else {
            overlappingDays = budget.getYearMonthInstance().lengthOfMonth();
        }
        return overlappingDays;
    }
}
