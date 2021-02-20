package com.example.budge.homework;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.*;
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

        List<Budget> budgets = budgetRepo.getAll().stream().filter(b ->
        {
            YearMonth yearMonthOfBudget = b.getYearMonthInstance();
            YearMonth startYearMonth = YearMonth.from(start);
            YearMonth endYearMonth = YearMonth.from(end);
            return (yearMonthOfBudget.equals(startYearMonth) || yearMonthOfBudget.isAfter(startYearMonth)) &&
                    (yearMonthOfBudget.equals(endYearMonth) || yearMonthOfBudget.isBefore(endYearMonth));
        }).collect(toList());

        double rtn = 0.0;
        HashMap<String, Integer> dayCountsEachMonth = new HashMap<>();
        if (budgets.size() == 1) {
            int overlappingDays = end.getDayOfMonth() - start.getDayOfMonth() + 1;
            double dailyAmount = budgets.get(0).getAmount() / (double) (budgets.get(0).getYearMonthInstance().lengthOfMonth());
            return overlappingDays * dailyAmount;
//            dayCountsEachMonth.put(budgets.get(0).getYearMonth(), overlappingDays);
        } else {
            for (Budget budget : budgets) {
                int overlappingDays;
                if (budget.getYearMonth().equals(start.format(ofPattern("yyyyMM")))) {
                    overlappingDays = budget.getYearMonthInstance().lengthOfMonth() - start.getDayOfMonth() + 1;
                } else if (budget.getYearMonth().equals(end.format(ofPattern("yyyyMM")))) {
                    overlappingDays = end.getDayOfMonth();
                } else {
                    overlappingDays = budget.getYearMonthInstance().lengthOfMonth();
                }
                double dailyAmount = budget.getAmount() / (double) (budget.getYearMonthInstance().lengthOfMonth());
                rtn += dailyAmount * overlappingDays;

//                dayCountsEachMonth.put(budget.getYearMonth(), overlappingDays);
            }
        }

//        Map<String, Double> priceUnitEachMonth = budgets.stream()
//                .collect(toMap(Budget::getYearMonth,
//                        budget -> budget.getAmount() / (double) (budget.getYearMonthInstance().lengthOfMonth())
//                ));
//
//        for (Map.Entry<String, Double> entry : priceUnitEachMonth.entrySet()) {
//            rtn += dayCountsEachMonth.get(entry.getKey()) * entry.getValue();
//        }

        return rtn;
    }
}
