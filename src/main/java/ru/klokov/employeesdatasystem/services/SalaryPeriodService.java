package ru.klokov.employeesdatasystem.services;

import org.springframework.stereotype.Service;
import ru.klokov.employeesdatasystem.entities.SalaryPeriodEntity;
import ru.klokov.employeesdatasystem.exceptions.PeriodsException;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryPeriodService {

    public List<SalaryPeriodEntity> returnMonthPeriods(LocalDate periodStart, LocalDate periodEnd) {
        if (periodStart.isAfter(periodEnd)) {
            throw new PeriodsException("Period start can't be after period end");
        }

        List<SalaryPeriodEntity> entityList = new ArrayList<>();

        long customFullMonthBetween = countOfFullMonthBetween(periodStart, periodEnd);

        double workedInStartMonth = returnDoubleCountDaysOfMonth(periodStart) - periodStart.getDayOfMonth();
        double workedInEndMonth = periodEnd.getDayOfMonth();

        Double multiplierStartMonth = workedInStartMonth / periodStart.lengthOfMonth();
        Double multiplierEndMonth = workedInEndMonth / periodEnd.lengthOfMonth();

        boolean perStartWith1stDate = periodStart.getDayOfMonth() == 1;
        boolean perEndWithLastDate = periodEnd.getDayOfMonth() == periodEnd.lengthOfMonth();

        if(customFullMonthBetween == 0) {
            if(periodStart.getMonth() == periodEnd.getMonth()) {
                double multiplier = ChronoUnit.DAYS.between(periodStart, periodEnd.plusDays(1)) / returnDoubleCountDaysOfMonth(periodStart);
                entityList.add(new SalaryPeriodEntity(periodStart, periodEnd, multiplier));
            } else {
                entityList.add(new SalaryPeriodEntity(periodStart, periodStart.withDayOfMonth(periodStart.lengthOfMonth()), multiplierStartMonth));
                entityList.add(new SalaryPeriodEntity(periodEnd.withDayOfMonth(1), periodEnd, multiplierEndMonth));
            }
        } else if(customFullMonthBetween == 1) {
            if(perStartWith1stDate && perEndWithLastDate) {
                entityList.add(new SalaryPeriodEntity(periodStart, periodEnd, 1.0));
            } else if(periodStart.plusMonths(2).getMonth() == periodEnd.getMonth()) {
                entityList.add(new SalaryPeriodEntity(periodStart, periodStart.withDayOfMonth(periodStart.lengthOfMonth()), multiplierStartMonth));
                entityList.add(new SalaryPeriodEntity(periodStart.plusMonths(1).withDayOfMonth(1), periodStart.plusMonths(1).withDayOfMonth(periodStart.plusMonths(1).lengthOfMonth()), 1.0));
                entityList.add(new SalaryPeriodEntity(periodEnd.withDayOfMonth(1), periodEnd, multiplierEndMonth));
            } else {
                entityList.add(new SalaryPeriodEntity(periodStart, periodStart.withDayOfMonth(periodStart.lengthOfMonth()), multiplierStartMonth));
                entityList.add(new SalaryPeriodEntity(periodEnd.withDayOfMonth(1), periodEnd, multiplierEndMonth));
            }
        } else {
            LocalDate st = periodStart;
            LocalDate end = periodStart.withDayOfMonth(periodStart.lengthOfMonth());
            while (!(end.getYear() == periodEnd.getYear() && end.getMonth() == periodEnd.getMonth())) {
                double multiplier = ChronoUnit.DAYS.between(st, end.plusDays(1)) / returnDoubleCountDaysOfMonth(st);
                entityList.add(new SalaryPeriodEntity(st, end, multiplier));
                st = st.plusMonths(1).withDayOfMonth(1);
                end = st.withDayOfMonth(st.lengthOfMonth());

                if(end.getYear() == periodEnd.getYear() && end.getMonth() == periodEnd.getMonth()) {
                    end = periodEnd;
                    double multiplier2 = ChronoUnit.DAYS.between(st, end.plusDays(1)) / returnDoubleCountDaysOfMonth(st);
                    entityList.add(new SalaryPeriodEntity(st, end, multiplier2));
                }
            }
        }

        return entityList;
    }

    private double returnDoubleCountDaysOfMonth(LocalDate date) {
        boolean leapYear = date.getYear() % 4 == 0;
        return date.getMonth().length(leapYear);
    }

    private int returnIntCountDaysOfMonth(LocalDate date) {
        boolean leapYear = date.getYear() % 4 == 0;
        return date.getMonth().length(leapYear);
    }

    private long countOfFullMonthBetween(LocalDate periodStart, LocalDate periodEnd) {
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

    private long countFullMonthWitStart1stDateAndEndLastDate(LocalDate start, LocalDate end) {
        if (start.plusMonths(1).getYear() == end.getYear() && start.plusMonths(1).getMonth() == end.getMonth()) {
            return 1L;
        } else {
            long result;
            result = ChronoUnit.MONTHS.between(start, end.plusDays(1));
            return result;
        }
    }

    private int returnIntWorkdaysOnPeriod(SalaryPeriodEntity salaryPeriodEntity) {
        int workdays = 0;
        LocalDate periodStart = salaryPeriodEntity.getPeriodStart();
        LocalDate periodEnd = salaryPeriodEntity.getPeriodEnd();

        return workdays;
    }
}
