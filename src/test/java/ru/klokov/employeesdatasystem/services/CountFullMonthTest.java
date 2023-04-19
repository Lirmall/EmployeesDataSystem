package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountFullMonthTest {

    @Test
    void returnMonthPeriodsTest01() {
        LocalDate periodStart = LocalDate.of(2022, 6, 1);
        LocalDate periodEnd = LocalDate.of(2022, 8, 31);
        System.out.println("Must be 3");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(3L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest02() {
        LocalDate periodStart = LocalDate.of(2022, 6, 11);
        LocalDate periodEnd = LocalDate.of(2022, 8, 31);
        System.out.println("Must be 2");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(2L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest03() {
        LocalDate periodStart = LocalDate.of(2022, 6, 1);
        LocalDate periodEnd = LocalDate.of(2022, 8, 15);
        System.out.println("Must be 2");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(2L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest04() {
        LocalDate periodStart = LocalDate.of(2022, 6, 12);
        LocalDate periodEnd = LocalDate.of(2022, 8, 3);
        System.out.println("Must be 1");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(1L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest05() {
        LocalDate periodStart = LocalDate.of(2022, 6, 2);
        LocalDate periodEnd = LocalDate.of(2022, 6, 15);
        System.out.println("Must be 0");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(0L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest06() {
        LocalDate periodStart = LocalDate.of(2022, 6, 1);
        LocalDate periodEnd = LocalDate.of(2022, 6, 15);
        System.out.println("Must be 0");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(0L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest07() {
        LocalDate periodStart = LocalDate.of(2022, 6, 16);
        LocalDate periodEnd = LocalDate.of(2022, 7, 11);
        System.out.println("Must be 0");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(0L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest08() {
        LocalDate periodStart = LocalDate.of(2022, 1, 1);
        LocalDate periodEnd = LocalDate.of(2022, 10, 31);
        System.out.println("Must be 10");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(10L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest09() {
        LocalDate periodStart = LocalDate.of(2022, 1, 11);
        LocalDate periodEnd = LocalDate.of(2022, 10, 31);
        System.out.println("Must be 9");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(9L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest10() {
        LocalDate periodStart = LocalDate.of(2022, 1, 1);
        LocalDate periodEnd = LocalDate.of(2022, 10, 15);
        System.out.println("Must be 9");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(9L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest11() {
        LocalDate periodStart = LocalDate.of(2022, 1, 12);
        LocalDate periodEnd = LocalDate.of(2022, 10, 15);
        System.out.println("Must be 8");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(8L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest12() {
        LocalDate periodStart = LocalDate.of(2021, 12, 1);
        LocalDate periodEnd = LocalDate.of(2022, 1, 31);
        System.out.println("Must be 2");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(2L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest13() {
        LocalDate periodStart = LocalDate.of(2021, 12, 5);
        LocalDate periodEnd = LocalDate.of(2022, 1, 31);
        System.out.println("Must be 1");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(1L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest14() {
        LocalDate periodStart = LocalDate.of(2021, 12, 1);
        LocalDate periodEnd = LocalDate.of(2022, 1, 15);
        System.out.println("Must be 1");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(1L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest15() {
        LocalDate periodStart = LocalDate.of(2022, 6, 1);
        LocalDate periodEnd = LocalDate.of(2022, 7, 15);
        System.out.println("Must be 1");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(1L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest16() {
        LocalDate periodStart = LocalDate.of(2021, 12, 12);
        LocalDate periodEnd = LocalDate.of(2022, 1, 15);
        System.out.println("Must be 0");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(0L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest17() {
        LocalDate periodStart = LocalDate.of(2021, Month.SEPTEMBER, 1);
        LocalDate periodEnd = LocalDate.of(2022, Month.JULY, 31);
        System.out.println("Must be 11");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(11L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest18() {
        LocalDate periodStart = LocalDate.of(2021, Month.SEPTEMBER, 15);
        LocalDate periodEnd = LocalDate.of(2022, Month.JULY, 31);
        System.out.println("Must be 10");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(10L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest19() {
        LocalDate periodStart = LocalDate.of(2021, Month.SEPTEMBER, 1);
        LocalDate periodEnd = LocalDate.of(2022, Month.JULY, 15);
        System.out.println("Must be 10");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(10L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest20() {
        LocalDate periodStart = LocalDate.of(2021, Month.SEPTEMBER, 12);
        LocalDate periodEnd = LocalDate.of(2022, Month.JULY, 15);
        System.out.println("Must be 9");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(9L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest21() {
        LocalDate periodStart = LocalDate.of(2021, Month.MARCH, 1);
        LocalDate periodEnd = LocalDate.of(2022, Month.JULY, 31);
        System.out.println("Must be 17");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(17L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest22() {
        LocalDate periodStart = LocalDate.of(2021, Month.MARCH, 12);
        LocalDate periodEnd = LocalDate.of(2022, Month.JULY, 31);
        System.out.println("Must be 16");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(16L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest23() {
        LocalDate periodStart = LocalDate.of(2021, 3, 1);
        LocalDate periodEnd = LocalDate.of(2022, 7, 15);
        System.out.println("Must be 16");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(16L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest24() {
        LocalDate periodStart = LocalDate.of(2021, 3, 12);
        LocalDate periodEnd = LocalDate.of(2022, 7, 15);
        System.out.println("Must be 15");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(15L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }

    @Test
    void returnMonthPeriodsTest25() {
        LocalDate periodStart = LocalDate.of(2021, 3, 1);
        LocalDate periodEnd = LocalDate.of(2021, 3, 31);
        System.out.println("Must be 1");

        System.out.println("customFullMonthBetween " + FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
        assertEquals(1L, FullMonthsCounter.countOfFullMonthBetween(periodStart, periodEnd));
    }
}