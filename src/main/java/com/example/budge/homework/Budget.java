package com.example.budge.homework;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    private String yearMonth;

    private Integer amount;

    double overlappingAmount(Period period) {
        return getDailyAmount() * period.getOverlappingDays(createPeriod());
    }

    private LocalDate lastDay() {
        return getYearMonthInstance().atEndOfMonth();
    }

    private LocalDate firstDay() {
        return getYearMonthInstance().atDay(1);
    }

    private YearMonth getYearMonthInstance() {
        return YearMonth.parse(getYearMonth(), DateTimeFormatter.ofPattern("yyyyMM"));
    }

    private double getDailyAmount() {
        return getAmount() / (double) (getYearMonthInstance().lengthOfMonth());
    }

    private Period createPeriod() {
        return new Period(firstDay(), lastDay());
    }
}
