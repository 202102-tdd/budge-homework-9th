package com.example.budge.homework;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Slf4j
public class BudgetCalculator {

    private DateTimeFormatter df = ofPattern("yyyyMM");
    private DateTimeFormatter df2 = ofPattern("yyyyMMdd");

    private BudgetRepo budgetRepo;

    public BudgetCalculator(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public Double query(LocalDate start, LocalDate end) {

        YearMonth startY = YearMonth.from(start);

        // get iterator months
        // (202101, 202103) -> (01, 02, 03)
        LocalDate tmp = LocalDate.of(start.getYear(), start.getMonthValue(), 1);
        List<String> monthRange = new ArrayList<>();
        while (!tmp.isAfter(end)) {
            monthRange.add(startY.format(df));
            tmp = tmp.plusMonths(1);
            startY = YearMonth.from(tmp);
        }

        List<Budget> budgets = budgetRepo.getAll().stream().filter(b ->
        {
            YearMonth yearMonthOfBudget = b.getYearMonthInstance();
            YearMonth startYearMonth = YearMonth.from(start);
            YearMonth endYearMonth = YearMonth.from(end);
            return (yearMonthOfBudget.equals(startYearMonth) || yearMonthOfBudget.isAfter(startYearMonth)) &&
                    (yearMonthOfBudget.equals(endYearMonth) || yearMonthOfBudget.isBefore(endYearMonth));
        }).collect(toList());

        List<Integer> dayCountsEachMonth = new ArrayList<>();
        if (budgets.size() == 1) {
            dayCountsEachMonth.add(end.getDayOfMonth() - start.getDayOfMonth() + 1);
        } else {
            for (int i = 0; i < budgets.size(); i++) {
                if (i == 0) {
                    dayCountsEachMonth.add(budgets.get(0).getYearMonthInstance().lengthOfMonth() - start.getDayOfMonth() + 1);
                } else if (i == budgets.size() - 1) {
                    dayCountsEachMonth.add(end.getDayOfMonth());
                } else {
                    dayCountsEachMonth.add(budgets.get(i).getYearMonthInstance().lengthOfMonth());
                }
            }
        }

        Map<String, Double> priceUnitEachMonth = budgets.stream()
                .collect(toMap(budget -> budget.getYearMonth(), budget -> budget.getAmount() / (double) (budget.getYearMonthInstance().lengthOfMonth())));
//        List<Double> priceUnitEachMonth = budgets.stream()
//                .map(budget -> BudgetVo.builder()
//                        .yearMonth(LocalDate.parse(budget.getYearMonth() + "01", df2))
//                        .amount(budget.getAmount())
//                        .build())
//                .filter(budgetVo -> monthRange.contains(df.format(budgetVo.getYearMonth())))
//                .collect(toList())
//                .stream()
//                .map(v -> {
//                    return v.getAmount() / (double) (v.getYearMonth().lengthOfMonth());
//                })
//                .collect(toList());

        double rtn = 0.0;
        for (int i = 0; i < priceUnitEachMonth.size(); i++) {
            rtn += dayCountsEachMonth.get(i) * priceUnitEachMonth.get(i);
        }

        return rtn;
    }
}
