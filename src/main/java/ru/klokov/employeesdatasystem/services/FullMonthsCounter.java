package ru.klokov.employeesdatasystem.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class FullMonthsCounter {
    public static long countOfFullMonthBetween(LocalDate periodStart, LocalDate periodEnd) {
        long fullMonthBetween;

        LocalDate periodStartNextMonthWith1stDate = periodStart.plusMonths(1).withDayOfMonth(1);
        LocalDate periodEnd1stDate = periodEnd.withDayOfMonth(1);
        LocalDate periodEndPlus1Day = periodEnd.plusDays(1);

        boolean perStartWith1stDate = periodStart.getDayOfMonth() == 1;
        boolean perEndWithLastDate = periodEnd.getDayOfMonth() == periodEnd.lengthOfMonth();

        if(periodStart.getYear() == periodEnd.getYear() && periodStart.getMonth() == periodEnd.getMonth()) {
            if(periodStart.withDayOfMonth(periodStart.lengthOfMonth()).equals(periodEnd)) {
                return 1L;
            }
            return 0L;
        }

        if(perStartWith1stDate && perEndWithLastDate) {
            fullMonthBetween = countFullMonthWitStart1stDateAndEndLastDate(periodStart, periodEndPlus1Day);
            return fullMonthBetween;
        } else if (perStartWith1stDate) {
            fullMonthBetween = countFullMonthWitStart1stDateAndEndLastDate(periodStart, periodEnd1stDate);
            return fullMonthBetween;
        } else if(perEndWithLastDate) {
            fullMonthBetween =
                    countFullMonthWitStart1stDateAndEndLastDate(periodStartNextMonthWith1stDate, periodEndPlus1Day);
            return fullMonthBetween;
        } else {
            Period period = Period.between(periodStartNextMonthWith1stDate, periodEnd1stDate);
            fullMonthBetween = period.toTotalMonths();
            return fullMonthBetween;
        }
    }

    private static long countFullMonthWitStart1stDateAndEndLastDate(LocalDate start, LocalDate end) {
        if (start.plusMonths(1).getYear() == end.getYear() && start.plusMonths(1).getMonth() == end.getMonth()) {
            return 1L;
        } else {
            long result;
            result = ChronoUnit.MONTHS.between(start, end.plusDays(1));
            return result;
        }
    }
}
