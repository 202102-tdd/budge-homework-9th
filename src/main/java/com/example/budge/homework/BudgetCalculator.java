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

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Slf4j
public class BudgetCalculator {

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMM");
    private DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyyMMdd");

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

        HashMap<String, Integer> dayCountsEachMonth = new HashMap<String, Integer>();
//        List<Integer> dayCountsEachMonth = new ArrayList<>();
        if (budgets.size() == 1) {
            dayCountsEachMonth.put(budgets.get(0).getYearMonth(), end.getDayOfMonth() - start.getDayOfMonth() + 1);
//            dayCountsEachMonth.add(end.getDayOfMonth() - start.getDayOfMonth() + 1);
        } else {
            for (int i = 0; i < budgets.size(); i++) {
                if (i == 0) {
                    dayCountsEachMonth.put(budgets.get(i).getYearMonth(), budgets.get(0).getYearMonthInstance().lengthOfMonth() - start.getDayOfMonth() + 1);
//                    dayCountsEachMonth.add(budgets.get(0).getYearMonthInstance().lengthOfMonth() - start.getDayOfMonth() + 1);
                } else if (i == budgets.size() - 1) {
                    dayCountsEachMonth.put(budgets.get(i).getYearMonth(), end.getDayOfMonth());
//                    dayCountsEachMonth.add(end.getDayOfMonth());
                } else {
                    dayCountsEachMonth.put(budgets.get(i).getYearMonth(), budgets.get(i).getYearMonthInstance().lengthOfMonth());
//                    dayCountsEachMonth.add(budgets.get(i).getYearMonthInstance().lengthOfMonth());
                }
            }
        }

        Map<String, Double> priceUnitEachMonth = budgets.stream()
                .collect(toMap(Budget::getYearMonth,
                        budget -> budget.getAmount() / (double) (budget.getYearMonthInstance().lengthOfMonth())
                ));

        double rtn = 0.0;
        for (Map.Entry<String, Double> entry : priceUnitEachMonth.entrySet()) {
            rtn += dayCountsEachMonth.get(entry.getKey()) * entry.getValue();
        }
//        for (int i = 0; i < priceUnitEachMonth.size(); i++) {
//            rtn += dayCountsEachMonth.get(i) * priceUnitEachMonth.get(i);
//        }

        return rtn;
    }
}
