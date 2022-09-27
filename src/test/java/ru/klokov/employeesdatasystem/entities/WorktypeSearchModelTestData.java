package ru.klokov.employeesdatasystem.entities;

import ru.klokov.employeesdatasystem.specifications.worktypesSpecification.WorktypeSearchModel;

import java.util.Arrays;

public class WorktypeSearchModelTestData {
    public static WorktypeSearchModel returnWorktypeSearchModel1() {
        WorktypeSearchModel worktypeSearchModel = new WorktypeSearchModel();

        worktypeSearchModel.setIds(Arrays.asList(1L, 2L));
        worktypeSearchModel.setSortColumn("id");

        return worktypeSearchModel;
    }
}
