package ru.klokov.employeesdatasystem.entities;

public class RangeEntityTestData {

    public static RangeEntity returnNoRangeEntity() {
        RangeEntity range = new RangeEntity();

        range.setId(4L);
        range.setBonus(1.0);
        range.setName("Non-range");

        return range;
    }

    public static RangeEntity returnThirdRangeEntity() {
        RangeEntity range = new RangeEntity();

        range.setId(3L);
        range.setBonus(1.1);
        range.setName("3 range");

        return range;
    }

    public static RangeEntity returnSecondRangeEntity() {
        RangeEntity range = new RangeEntity();

        range.setId(2L);
        range.setBonus(1.2);
        range.setName("2 range");

        return range;
    }

    public static RangeEntity returnFirstRangeEntity() {
        RangeEntity range = new RangeEntity();

        range.setId(1L);
        range.setBonus(1.3);
        range.setName("1 range");

        return range;
    }
}
