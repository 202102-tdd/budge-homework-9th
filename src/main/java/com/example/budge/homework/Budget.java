package com.example.budge.homework;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

import static java.time.format.DateTimeFormatter.ofPattern;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    private String yearMonth;

    private Integer amount;

    YearMonth getYearMonthInstance() {
        return YearMonth.parse(getYearMonth(), ofPattern("yyyyMM"));
    }
}
