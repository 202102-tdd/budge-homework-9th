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

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    long getOverlappingDays(Budget budget) {
        long overlappingDays;
        if (budget.getYearMonth().equals(getStart().format(ofPattern("yyyyMM")))) {
            LocalDate overlappingEnd = budget.lastDay();
            LocalDate overlappingStart = getStart();
            overlappingDays = DAYS.between(overlappingStart, overlappingEnd) + 1;
        } else if (budget.getYearMonth().equals(getEnd().format(ofPattern("yyyyMM")))) {
//            overlappingDays = getEnd().getDayOfMonth();
            LocalDate overlappingStart = budget.firstDay();
            LocalDate overlappingEnd = end;
            overlappingDays = DAYS.between(overlappingStart, overlappingEnd) + 1;
        } else {
            overlappingDays = budget.getYearMonthInstance().lengthOfMonth();
        }
        return overlappingDays;
    }
}
