package com.example.budge.homework;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;

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

    int getOverlappingDays(Budget budget) {
        int overlappingDays;
        if (budget.getYearMonth().equals(getStart().format(ofPattern("yyyyMM")))) {
            overlappingDays = budget.getYearMonthInstance().lengthOfMonth() - getStart().getDayOfMonth() + 1;
        } else if (budget.getYearMonth().equals(getEnd().format(ofPattern("yyyyMM")))) {
            overlappingDays = getEnd().getDayOfMonth();
        } else {
            overlappingDays = budget.getYearMonthInstance().lengthOfMonth();
        }
        return overlappingDays;
    }
}
