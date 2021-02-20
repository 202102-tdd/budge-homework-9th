package com.example.budge.homework;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class BudgetCalculator {

    private final BudgetRepo budgetRepo;

    public BudgetCalculator(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public Double query(LocalDate start, LocalDate end) {

        Period period = new Period(start, end);

        return budgetRepo.getAll().stream().mapToDouble(budget -> budget.overlappingAmount(period)).sum();
    }
}
