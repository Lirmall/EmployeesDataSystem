package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import ru.klokov.employeesdatasystem.entities.SalaryPeriodEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalaryPeriodServiceTest {

    private final SalaryPeriodService salaryPeriodService = new SalaryPeriodService();

    @Test
    void returnMonthPeriodsTest01() {
        LocalDate periodStart = LocalDate.of(2022, 6, 1);
        LocalDate periodEnd = LocalDate.of(2022, 8, 31);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(3, list.size());

        assertEquals(LocalDate.of(2022, 6, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 6, 30), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(1).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 31), list.get(1).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 8, 1), list.get(2).getPeriodStart());
        assertEquals(LocalDate.of(2022, 8, 31), list.get(2).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest02() {
        LocalDate periodStart = LocalDate.of(2022, 6, 11);
        LocalDate periodEnd = LocalDate.of(2022, 8, 31);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(3, list.size());

        assertEquals(LocalDate.of(2022, 6, 11), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 6, 30), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(1).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 31), list.get(1).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 8, 1), list.get(2).getPeriodStart());
        assertEquals(LocalDate.of(2022, 8, 31), list.get(2).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest03() {
        LocalDate periodStart = LocalDate.of(2022, 6, 1);
        LocalDate periodEnd = LocalDate.of(2022, 8, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(3, list.size());

        assertEquals(LocalDate.of(2022, 6, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 6, 30), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(1).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 31), list.get(1).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 8, 1), list.get(2).getPeriodStart());
        assertEquals(LocalDate.of(2022, 8, 15), list.get(2).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest04() {
        LocalDate periodStart = LocalDate.of(2022, 6, 12);
        LocalDate periodEnd = LocalDate.of(2022, 8, 3);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(3, list.size());

        assertEquals(LocalDate.of(2022, 6, 12), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 6, 30), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(1).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 31), list.get(1).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 8, 1), list.get(2).getPeriodStart());
        assertEquals(LocalDate.of(2022, 8, 3), list.get(2).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest05() {
        LocalDate periodStart = LocalDate.of(2022, 6, 2);
        LocalDate periodEnd = LocalDate.of(2022, 6, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(1, list.size());

        assertEquals(LocalDate.of(2022, 6, 2), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 6, 15), list.get(0).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest06() {
        LocalDate periodStart = LocalDate.of(2022, 6, 1);
        LocalDate periodEnd = LocalDate.of(2022, 6, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(1, list.size());

        assertEquals(LocalDate.of(2022, 6, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 6, 15), list.get(0).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest07() {
        LocalDate periodStart = LocalDate.of(2022, 6, 16);
        LocalDate periodEnd = LocalDate.of(2022, 7, 11);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(2, list.size());

        assertEquals(LocalDate.of(2022, 6, 16), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 6, 30), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(1).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 11), list.get(1).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest08() {
        LocalDate periodStart = LocalDate.of(2022, 1, 1);
        LocalDate periodEnd = LocalDate.of(2022, 10, 31);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(10, list.size());

        assertEquals(LocalDate.of(2022, 1, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 1, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 10, 1), list.get(9).getPeriodStart());
        assertEquals(LocalDate.of(2022, 10, 31), list.get(9).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest09() {
        LocalDate periodStart = LocalDate.of(2022, 1, 11);
        LocalDate periodEnd = LocalDate.of(2022, 10, 31);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(10, list.size());

        assertEquals(LocalDate.of(2022, 1, 11), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 1, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 10, 1), list.get(9).getPeriodStart());
        assertEquals(LocalDate.of(2022, 10, 31), list.get(9).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest10() {
        LocalDate periodStart = LocalDate.of(2022, 1, 1);
        LocalDate periodEnd = LocalDate.of(2022, 10, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(10, list.size());

        assertEquals(LocalDate.of(2022, 1, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 1, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 10, 1), list.get(9).getPeriodStart());
        assertEquals(LocalDate.of(2022, 10, 15), list.get(9).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest11() {
        LocalDate periodStart = LocalDate.of(2022, 1, 12);
        LocalDate periodEnd = LocalDate.of(2022, 10, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(10, list.size());

        assertEquals(LocalDate.of(2022, 1, 12), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 1, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 10, 1), list.get(9).getPeriodStart());
        assertEquals(LocalDate.of(2022, 10, 15), list.get(9).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest12() {
        LocalDate periodStart = LocalDate.of(2021, 12, 1);
        LocalDate periodEnd = LocalDate.of(2022, 1, 31);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        System.out.println("#12");
        System.out.println(periodStart + " - " + periodEnd);
        System.out.println(list.size());
        for (SalaryPeriodEntity e : list) {
            System.out.println(e.getPeriodStart() + " - " + e.getPeriodEnd() + " " + e.getMultiplier());
        }
        System.out.println();

        assertNotNull(list);
        assertEquals(2, list.size());

        assertEquals(LocalDate.of(2021, 12, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 12, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 1, 1), list.get(1).getPeriodStart());
        assertEquals(LocalDate.of(2022, 1, 31), list.get(1).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest13() {
        LocalDate periodStart = LocalDate.of(2021, 12, 5);
        LocalDate periodEnd = LocalDate.of(2022, 1, 31);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(2, list.size());

        assertEquals(LocalDate.of(2021, 12, 5), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 12, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 1, 1), list.get(1).getPeriodStart());
        assertEquals(LocalDate.of(2022, 1, 31), list.get(1).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest14() {
        LocalDate periodStart = LocalDate.of(2021, 12, 1);
        LocalDate periodEnd = LocalDate.of(2022, 1, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(2, list.size());

        assertEquals(LocalDate.of(2021, 12, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 12, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 1, 1), list.get(1).getPeriodStart());
        assertEquals(LocalDate.of(2022, 1, 15), list.get(1).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest15() {
        LocalDate periodStart = LocalDate.of(2022, 6, 1);
        LocalDate periodEnd = LocalDate.of(2022, 7, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(2, list.size());

        assertEquals(LocalDate.of(2022, 6, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 6, 30), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(1).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 15), list.get(1).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest16() {
        LocalDate periodStart = LocalDate.of(2021, 12, 12);
        LocalDate periodEnd = LocalDate.of(2022, 1, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(2, list.size());

        assertEquals(LocalDate.of(2021, 12, 12), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 12, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 1, 1), list.get(1).getPeriodStart());
        assertEquals(LocalDate.of(2022, 1, 15), list.get(1).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest17() {
        LocalDate periodStart = LocalDate.of(2021, 9, 1);
        LocalDate periodEnd = LocalDate.of(2022, 7, 31);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(11, list.size());

        assertEquals(LocalDate.of(2021, 9, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 9, 30), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(10).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 31), list.get(10).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest18() {
        LocalDate periodStart = LocalDate.of(2021, 9, 15);
        LocalDate periodEnd = LocalDate.of(2022, 7, 31);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(11, list.size());

        assertEquals(LocalDate.of(2021, 9, 15), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 9, 30), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(10).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 31), list.get(10).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest19() {
        LocalDate periodStart = LocalDate.of(2021, 9, 1);
        LocalDate periodEnd = LocalDate.of(2022, 7, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(11, list.size());

        assertEquals(LocalDate.of(2021, 9, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 9, 30), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(10).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 15), list.get(10).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest20() {
        LocalDate periodStart = LocalDate.of(2021, 9, 12);
        LocalDate periodEnd = LocalDate.of(2022, 7, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(11, list.size());

        assertEquals(LocalDate.of(2021, 9, 12), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 9, 30), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(10).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 15), list.get(10).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest21() {
        LocalDate periodStart = LocalDate.of(2021, 3, 1);
        LocalDate periodEnd = LocalDate.of(2022, 7, 31);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(17, list.size());

        assertEquals(LocalDate.of(2021, 3, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 3, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(16).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 31), list.get(16).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest22() {
        LocalDate periodStart = LocalDate.of(2021, 3, 12);
        LocalDate periodEnd = LocalDate.of(2022, 7, 31);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(17, list.size());

        assertEquals(LocalDate.of(2021, 3, 12), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 3, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(16).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 31), list.get(16).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest23() {
        LocalDate periodStart = LocalDate.of(2021, 3, 1);
        LocalDate periodEnd = LocalDate.of(2022, 7, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(17, list.size());

        assertEquals(LocalDate.of(2021, 3, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 3, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(16).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 15), list.get(16).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest24() {
        LocalDate periodStart = LocalDate.of(2021, 3, 12);
        LocalDate periodEnd = LocalDate.of(2022, 7, 15);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(17, list.size());

        assertEquals(LocalDate.of(2021, 3, 12), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 3, 31), list.get(0).getPeriodEnd());

        assertEquals(LocalDate.of(2022, 7, 1), list.get(16).getPeriodStart());
        assertEquals(LocalDate.of(2022, 7, 15), list.get(16).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest25() {
        LocalDate periodStart = LocalDate.of(2021, 3, 1);
        LocalDate periodEnd = LocalDate.of(2021, 3, 31);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);

        assertNotNull(list);
        assertEquals(1, list.size());

        assertEquals(LocalDate.of(2021, 3, 1), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2021, 3, 31), list.get(0).getPeriodEnd());
    }

    @Test
    void returnMonthPeriodsTest26() {
        LocalDate periodStart = LocalDate.of(2022, 9, 3);
        LocalDate periodEnd = LocalDate.of(2022, 9, 30);

        List<SalaryPeriodEntity> list = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);
        for (SalaryPeriodEntity e : list) {
            System.out.println(e.getPeriodStart() + " - " + e.getPeriodEnd() + " " + e.getMultiplier());
        }
        assertEquals(1, list.size());
        assertNotNull(list);
        assertEquals(1, list.size());

        assertEquals(LocalDate.of(2022, 9, 3), list.get(0).getPeriodStart());
        assertEquals(LocalDate.of(2022, 9, 30), list.get(0).getPeriodEnd());
    }
}