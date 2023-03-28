package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.klokov.employeesdatasystem.StaticSqlSchemaClasspathes;
import ru.klokov.employeesdatasystem.config.SecurityConfig;
import ru.klokov.employeesdatasystem.dto.PositionsSearchSpecificationsDTO;
import ru.klokov.employeesdatasystem.entities.PositionEntity;
import ru.klokov.employeesdatasystem.entities.PositionEntityTestData;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.repositories.PositionRepository;
import ru.klokov.employeesdatasystem.repositories.WorktypeRepository;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.specifications.pageSettings.PageSettings;
import ru.klokov.employeesdatasystem.specifications.positionsSpecification.PositionSearchCriteria;
import ru.klokov.employeesdatasystem.specifications.positionsSpecification.PositionSearchModel;
import ru.klokov.employeesdatasystem.specifications.positionsSpecification.PositionSpecificationWithCriteria;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {SortColumnChecker.class, WorktypeRepository.class,
        PositionRepository.class, PositionWorktypeService.class, EmplPosRangeService.class,
        WorktypeService.class, SecurityConfig.class, DefaultPermissionEvaluator.class})
@ActiveProfiles("dataJpaTest")
class PositionServiceTest {

    @Autowired
    private PositionService positionService;

    @Autowired
    private WorktypeService worktypeService;

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void createTest() {
        PositionEntity positionEntity = PositionEntityTestData.returnPosition();

        PositionEntity createdPosition = positionService.create(positionEntity);

        assertEquals(6, createdPosition.getId());
        assertEquals("Salary", createdPosition.getWorktype().getName());
        assertEquals(1000.0, createdPosition.getSalary());
        assertEquals("TestPosition", createdPosition.getName());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void findAllTest() {
        List<PositionEntity> positionEntityList = positionService.findAll();

        assertEquals(5, positionEntityList.size());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void findByIdTest() {
        PositionEntity result = positionService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Mechanic", result.getName());
        assertEquals("Hourly", result.getWorktype().getName());
        assertEquals(100.0, result.getSalary());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void findPositionByNameTest() {
        PositionEntity result = positionService.findPositionByName("Mechanic");

        assertEquals(1L, result.getId());
        assertEquals("Mechanic", result.getName());
        assertEquals("Hourly", result.getWorktype().getName());
        assertEquals(100.0, result.getSalary());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void findByFilterTest() {
        List<Long> ids = new ArrayList<>();
        ids.add(2L);
        ids.add(3L);
        ids.add(4L);

        List<String> names = new ArrayList<>();
        List<Long> worktypeId = new ArrayList<>();
        List<Double> salaries = new ArrayList<>();
        String sortColumn = "-id";

        PositionSearchModel searchModel = new PositionSearchModel();
        searchModel.setIds(ids);
        searchModel.setNames(names);
        searchModel.setWorktypeId(worktypeId);
        searchModel.setSalaries(salaries);
        searchModel.setSortColumn(sortColumn);

        Page<PositionEntity> result = positionService.findByFilter(searchModel);

        assertEquals(3, result.getTotalElements());
        assertEquals(4L, result.getContent().get(0).getId());
        assertEquals(3L, result.getContent().get(1).getId());
        assertEquals(2L, result.getContent().get(2).getId());
    }

    @Disabled
    @Test
    void findByFilterWithNewCriteriaAndSpecificationTest() {
        PositionSearchCriteria criteria = new PositionSearchCriteria();
        criteria.setFieldName("salary");
        criteria.setOperation(">");
        criteria.setFieldValue(19000);

        Integer pages = 0;

        Integer limit = 5;

        String sortColumn = "id";

        PageSettings pageSettings = new PageSettings(pages, limit, sortColumn);


        PositionSpecificationWithCriteria specification = new PositionSpecificationWithCriteria(criteria);

        WorktypeEntity worktype = worktypeService.findById(1L);

        PositionSearchCriteria criteria2 = new PositionSearchCriteria();
        criteria2.setFieldName("worktype");
        criteria2.setOperation(":");
        criteria2.setFieldValue(worktype);

        List<PositionSearchCriteria> criteriaList = new ArrayList<>();
        criteriaList.add(criteria);
        criteriaList.add(criteria2);

        PositionsSearchSpecificationsDTO dto = new PositionsSearchSpecificationsDTO();
        dto.setSpecifications(criteriaList);
        dto.setPageSettings(pageSettings);

        Page<PositionEntity> result = positionService.findByFilterWithNewCriteriaAndSpecification(dto);

        System.out.println("--------> " + result.getTotalElements());

//        Page<PositionEntity> result = positionService.findByFilterWithNewCriteriaAndSpecification(criteria);
//
//        assertNotNull(result);
////        assertEquals(1, result.getTotalElements());
//        System.out.println("elements ---------> " + result.getTotalElements());
//        PositionEntity resultEntity0 = result.getContent().get(0);
//        PositionEntity resultEntity1 = result.getContent().get(1);
//        PositionEntity resultEntity2 = result.getContent().get(2);
//        PositionEntity resultEntity3 = result.getContent().get(3);
//
//        System.out.println(resultEntity0.getId());
//        System.out.println(resultEntity0.getName());
//        System.out.println(resultEntity0.getSalary());
//
//        System.out.println(resultEntity1.getId());
//        System.out.println(resultEntity1.getName());
//        System.out.println(resultEntity1.getSalary());
//
//        System.out.println(resultEntity2.getId());
//        System.out.println(resultEntity2.getName());
//        System.out.println(resultEntity2.getSalary());
//
//        System.out.println(resultEntity3.getId());
//        System.out.println(resultEntity3.getName());
//        System.out.println(resultEntity3.getSalary());
//
//        WorktypeEntity worktype = worktypeService.findById(1L);
//        criteria.setFieldName("worktype");
//        criteria.setOperation(":");
//        criteria.setFieldValue(worktype);
//
//        Page<PositionEntity> result2 = positionService.findByFilterWithNewCriteriaAndSpecification(criteria);
//
//        assertNotNull(result2);
////        assertEquals(1, result.getTotalElements());
//        System.out.println("elements2 ---------> " + result2.getTotalElements());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void patchUpdateTest() {
        PositionEntity positionToUpdate = positionService.findPositionByName("Engineer");
        PositionEntity updateData = PositionEntityTestData.returnEngineerPosition();

        assertNotNull(positionToUpdate);

        assertEquals(20000.0, positionToUpdate.getSalary());

        PositionEntity result = positionService.patchUpdate(positionToUpdate, updateData);

        assertEquals(1000.0, result.getSalary());
    }

    @Disabled
    @Test
    void worktypeCheckByWorktypeDTOTest() {
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void worktypeCheckByWorktypeNameTest() {
        WorktypeEntity result = positionService.worktypeCheck("Salary");
        assertEquals(1L, result.getId());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA})
    void deleteByIdTest() {
        PositionEntity positionToDCreate = PositionEntityTestData.returnPosition();

        assertEquals(5, positionService.findAll().size());

        PositionEntity createdPosition = positionService.create(positionToDCreate);

        assertEquals(6, positionService.findAll().size());
        assertEquals(6L, createdPosition.getId());

        positionService.deleteById(6L);

        assertEquals(5, positionService.findAll().size());
        assertThrows(NoMatchingEntryInDatabaseException.class,
                () -> positionService.findById(6L));

    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void getCountOfTotalItemsTest() {
        assertEquals(5, positionService.getCountOfTotalItems());
    }
}