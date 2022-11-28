package ru.klokov.employeesdatasystem.specifications.pageSettings;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageSettings {
    private Integer pages = 0;

    private Integer limit = 5;

    private String sortColumn;
}
