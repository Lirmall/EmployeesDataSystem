package ru.klokov.employeesdatasystem.entities;

public class GenderEntityTestData {

    public static GenderEntity returnMaleGenderEntity() {
        GenderEntity gender = new GenderEntity();

        gender.setId(1L);
        gender.setName("Male");

        return gender;
    }

    public static GenderEntity returnFemaleGenderEntity() {
        GenderEntity gender = new GenderEntity();

        gender.setId(2L);
        gender.setName("Female");

        return gender;
    }
}
