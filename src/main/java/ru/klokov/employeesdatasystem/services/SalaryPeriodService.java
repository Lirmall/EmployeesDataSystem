package ru.klokov.employeesdatasystem.services;

import org.springframework.stereotype.Service;
import ru.klokov.employeesdatasystem.entities.SalaryPeriodEntity;
import ru.klokov.employeesdatasystem.exceptions.PeriodsException;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryPeriodService {

    public List<SalaryPeriodEntity> returnMonthPeriods(LocalDate periodStart, LocalDate periodEnd) {
        List<SalaryPeriodEntity> entityList = new ArrayList<>();

        long daysBetweenDates = ChronoUnit.DAYS.between(periodStart, periodEnd);
        long monthBetweenDates = ChronoUnit.MONTHS.between(periodStart, periodEnd);

        long customFullMonthBetween = countOfFullMonthBetween(periodStart, periodEnd);

        if (periodStart.isAfter(periodEnd)) {
            throw new PeriodsException("Period start can't be after period end");
        }

        if (monthBetweenDates == 0) {
            return getSalaryPeriodEntitiesWhenLessOneMonthsBetweenStartAndEnd(periodStart, periodEnd, entityList, daysBetweenDates);
        } else if (monthBetweenDates == 1 && periodStart.getMonth() != periodEnd.getMonth()) {
            return getEntitiesWhenStartAndEndInDifferentMonths(periodStart, periodEnd, entityList);
        } else if (periodStart.isAfter(periodEnd)) {
            throw new PeriodsException("Period start can't be after period end");
        } else {
            entityList = returnMonthPeriodsAtLargePeriod(periodStart, periodEnd);
            return entityList;
        }
    }

    private List<SalaryPeriodEntity>
    getEntitiesWhenStartAndEndInDifferentMonths
            (LocalDate periodStart, LocalDate periodEnd, List<SalaryPeriodEntity> entityList) {
        Month startMonth = periodStart.getMonth();
        Month endMonth = periodEnd.getMonth();

        boolean leapYearStart = periodStart.getYear() % 4 == 0;
        boolean leapYearEnd = periodEnd.getYear() % 4 == 0;

        double workedInStartMonth = returnDoubleCountDaysOfMonth(periodStart) - periodStart.getDayOfMonth();
        double workedInEndMonth = periodEnd.getDayOfMonth();

        Double multiplierStartMonth = workedInStartMonth / returnDoubleCountDaysOfMonth(periodStart);
        Double multiplierEndMonth = workedInEndMonth / returnDoubleCountDaysOfMonth(periodEnd);

        if (periodStart.getDayOfMonth() == 1) {
            entityList.add(new SalaryPeriodEntity(periodStart, LocalDate.of(periodStart.getYear(), startMonth, startMonth.length(leapYearStart)), 1.0));
        } else {
            entityList.add(new SalaryPeriodEntity(periodStart, LocalDate.of(periodStart.getYear(), startMonth, startMonth.length(leapYearStart)), multiplierStartMonth));
        }

        long fullMonthBetween = countOfFullMonthBetween(periodStart, periodEnd);

        int fullMonthInEntityList = 0;

        for (SalaryPeriodEntity entity : entityList) {
            if (entity.getMultiplier() == 1.0) {
                fullMonthInEntityList++;
            }
        }

        if (fullMonthInEntityList < fullMonthBetween) {
            LocalDate perSt = LocalDate.of(periodEnd.getYear(), periodEnd.minusMonths(1).getMonth(), 1);
            LocalDate perEnd = LocalDate.of(periodEnd.getYear(), periodEnd.minusMonths(1).getMonth(), returnIntCountDaysOfMonth(perSt));
            entityList.add(new SalaryPeriodEntity(perSt, perEnd, 1.0));
        }

        entityList.add(new SalaryPeriodEntity(LocalDate.of(periodEnd.getYear(), endMonth, 1), periodEnd, multiplierEndMonth));

        return entityList;
    }

    private List<SalaryPeriodEntity> getSalaryPeriodEntitiesWhenStartAndEndInDifferentMonths0(LocalDate periodStart, LocalDate periodEnd, List<SalaryPeriodEntity> entityList) {
        Month startMonth = periodStart.getMonth();
        Month endMonth = periodEnd.getMonth();

        boolean leapYearStart = periodStart.getYear() % 4 == 0;
        boolean leapYearEnd = periodEnd.getYear() % 4 == 0;

        double workedInStartMonth = returnDoubleCountDaysOfMonth(periodStart) - periodStart.getDayOfMonth();
        double workedInEndMonth = periodEnd.getDayOfMonth();

        Double multiplierStartMonth = workedInStartMonth / returnDoubleCountDaysOfMonth(periodStart);
        Double multiplierEndMonth = workedInEndMonth / returnDoubleCountDaysOfMonth(periodEnd);

        if (periodStart.getDayOfMonth() == 1) {
            entityList.add(new SalaryPeriodEntity(periodStart, LocalDate.of(periodStart.getYear(), startMonth, startMonth.length(leapYearStart)), 1.0));
        } else {
            entityList.add(new SalaryPeriodEntity(periodStart, LocalDate.of(periodStart.getYear(), startMonth, startMonth.length(leapYearStart)), multiplierStartMonth));
        }

        long fullMonthBetween = countOfFullMonthBetween(periodStart, periodEnd);

        System.out.println("fullMonthBetween " + fullMonthBetween);

        int fullMonthInEntityList = 0;

        for (SalaryPeriodEntity entity : entityList) {
            if (entity.getMultiplier() == 1.0) {
                fullMonthInEntityList++;
            }
        }

        if (fullMonthInEntityList < fullMonthBetween) {
            LocalDate perSt = LocalDate.of(periodEnd.getYear(), periodEnd.minusMonths(1).getMonth(), 1);
            LocalDate perEnd = LocalDate.of(periodEnd.getYear(), periodEnd.minusMonths(1).getMonth(), returnIntCountDaysOfMonth(perSt));
            entityList.add(new SalaryPeriodEntity(perSt, perEnd, 1.0));
        }

        entityList.add(new SalaryPeriodEntity(LocalDate.of(periodEnd.getYear(), endMonth, 1), periodEnd, multiplierEndMonth));

        return entityList;
    }

    private List<SalaryPeriodEntity> getSalaryPeriodEntitiesWhenLessOneMonthsBetweenStartAndEnd(LocalDate periodStart, LocalDate periodEnd, List<SalaryPeriodEntity> entityList, long daysBetweenDates) {
        boolean periodStartWith1stDay = periodStart.getDayOfMonth() == 1;
        boolean periodEndWithLastDayOfMonth = periodEnd.getDayOfMonth() == returnIntCountDaysOfMonth(periodEnd);
        boolean endMonthIsNextFromStartMonth = periodStart.getMonth() == periodEnd.getMonth().minus(1);

        if (endMonthIsNextFromStartMonth) {
            double workedInStartMonth = returnDoubleCountDaysOfMonth(periodStart) - periodStart.getDayOfMonth();
            double workedInEndMonth = periodEnd.getDayOfMonth();
            Double multiplierStart = workedInStartMonth / returnDoubleCountDaysOfMonth(periodStart);
            Double multiplierEnd = workedInEndMonth / returnDoubleCountDaysOfMonth(periodEnd);
            entityList.add(new SalaryPeriodEntity(periodStart, LocalDate.of(periodStart.getYear(), periodStart.getMonth(), returnIntCountDaysOfMonth(periodStart)), multiplierStart));
            entityList.add(new SalaryPeriodEntity(LocalDate.of(periodEnd.getYear(), periodEnd.getMonth(), 1), periodEnd, multiplierEnd));
            return entityList;
        }

        if (periodStartWith1stDay && periodEndWithLastDayOfMonth) {
            entityList.add(new SalaryPeriodEntity(periodStart, periodEnd, 1.0));
        } else {
            Double multiplier = daysBetweenDates / returnDoubleCountDaysOfMonth(periodStart);
            entityList.add(new SalaryPeriodEntity(periodStart, periodEnd, multiplier));
        }
        return entityList;
    }

    private List<SalaryPeriodEntity> returnMonthPeriodsAtLargePeriod(LocalDate periodStart, LocalDate periodEnd) {
        Month startMonth = periodStart.getMonth();

        double workedInStartMonth = returnDoubleCountDaysOfMonth(periodStart) - periodStart.getDayOfMonth();

        List<SalaryPeriodEntity> entityList = new ArrayList<>();

        Double multiplierStartMonth = workedInStartMonth / returnDoubleCountDaysOfMonth(periodStart);

        if (periodStart.getDayOfMonth() == 1) {
            entityList.add(new SalaryPeriodEntity(periodStart, LocalDate.of(periodStart.getYear(), startMonth, returnIntCountDaysOfMonth(periodStart)), 1.0));
        } else {
            entityList.add(new SalaryPeriodEntity(periodStart, LocalDate.of(periodStart.getYear(), startMonth, returnIntCountDaysOfMonth(periodStart)), multiplierStartMonth));
        }

        long fullMonthBetween = countOfFullMonthBetween(periodStart, periodEnd);

        if (fullMonthBetween == 1L) {
            LocalDate date = periodStart.plusMonths(1L);
            LocalDate fullMonthStart = LocalDate.of(date.getYear(), date.getMonth(), 1);
            LocalDate fullMonthEnd = LocalDate.of(date.getYear(), date.getMonth(), returnIntCountDaysOfMonth(date));

            entityList.add(new SalaryPeriodEntity(fullMonthStart, fullMonthEnd, 1.0));
        } else if (periodStart.getDayOfMonth() != 1 && periodEnd.getDayOfMonth() == returnIntCountDaysOfMonth(periodEnd)) {
            for (long i = 1L; i < fullMonthBetween + 1L; i++) {
                LocalDate date = periodStart.plusMonths(i);
                LocalDate fullMonthStart = LocalDate.of(date.getYear(), date.getMonth(), 1);
                LocalDate fullMonthEnd = LocalDate.of(date.getYear(), date.getMonth(), returnIntCountDaysOfMonth(date));

                entityList.add(new SalaryPeriodEntity(fullMonthStart, fullMonthEnd, 1.0));
            }
        } else {
            for (long i = 1L; i < fullMonthBetween; i++) {
                LocalDate date = periodStart.plusMonths(i);
                LocalDate fullMonthStart = LocalDate.of(date.getYear(), date.getMonth(), 1);
                LocalDate fullMonthEnd = LocalDate.of(date.getYear(), date.getMonth(), returnIntCountDaysOfMonth(date));

                entityList.add(new SalaryPeriodEntity(fullMonthStart, fullMonthEnd, 1.0));
            }
        }

        double workedInEndMonth = periodEnd.getDayOfMonth();

        Double multiplierEndMonth = workedInEndMonth / returnDoubleCountDaysOfMonth(periodEnd);
        LocalDate endMonthFirstDate = LocalDate.of(periodEnd.getYear(), periodEnd.getMonth(), 1);

        int fullMonthInEntityList = 0;

        for (SalaryPeriodEntity entity : entityList) {
            if (entity.getMultiplier() == 1.0) {
                fullMonthInEntityList++;
            }
        }

        if (fullMonthInEntityList < fullMonthBetween) {
            LocalDate perSt = LocalDate.of(periodEnd.getYear(), periodEnd.minusMonths(1).getMonth(), 1);
            LocalDate perEnd = LocalDate.of(periodEnd.getYear(), periodEnd.minusMonths(1).getMonth(), returnIntCountDaysOfMonth(perSt));
            entityList.add(new SalaryPeriodEntity(perSt, perEnd, 1.0));
        }

        if (periodEnd.getDayOfMonth() != returnIntCountDaysOfMonth(periodEnd)) {
            entityList.add(new SalaryPeriodEntity(endMonthFirstDate, periodEnd, multiplierEndMonth));
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
            return ChronoUnit.MONTHS.between(start, end.plusDays(1));
        }
    }

    private int returnIntWorkdaysOnPeriod(SalaryPeriodEntity salaryPeriodEntity) {
        int workdays = 0;
        LocalDate periodStart = salaryPeriodEntity.getPeriodStart();
        LocalDate periodEnd = salaryPeriodEntity.getPeriodEnd();



        return workdays;
    }
}
