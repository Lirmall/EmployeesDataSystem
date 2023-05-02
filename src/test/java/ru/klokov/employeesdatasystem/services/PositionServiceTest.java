package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.klokov.employeesdatasystem.StaticSqlSchemaClasspathes;
import ru.klokov.employeesdatasystem.config.SecurityConfig;
import ru.klokov.employeesdatasystem.dto.PositionsSearchSpecificationsDTO;
import ru.klokov.employeesdatasystem.dto.WorktypeDTO;
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
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
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
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void findAllTest() {
        List<PositionEntity> positionEntityList = positionService.findAll();

        assertEquals(5, positionEntityList.size());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void findByIdTest() {
        PositionEntity result = positionService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Mechanic", result.getName());
        assertEquals("Hourly", result.getWorktype().getName());
        assertEquals(100.0, result.getSalary());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void findPositionByNameTest() {
        PositionEntity result = positionService.findPositionByName("Mechanic");

        assertEquals(1L, result.getId());
        assertEquals("Mechanic", result.getName());
        assertEquals("Hourly", result.getWorktype().getName());
        assertEquals(100.0, result.getSalary());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
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

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void findByFilterWithNewCriteriaAndSpecificationTest() {
        PositionSearchCriteria criteria = new PositionSearchCriteria();
        criteria.setFieldName("salary");
        criteria.setOperation(">");
        criteria.setFieldValue(19000);

        Integer pages = 0;
        Integer limit = 5;
        String sortColumn = "-id";
        PageSettings pageSettings = new PageSettings(pages, limit, sortColumn);

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

        assertNotNull(result);
        assertEquals(3, result.getTotalElements());

        assertEquals(30000.0, result.getContent().get(0).getSalary());
        assertEquals("Technical director", result.getContent().get(0).getName());
        assertEquals(5L, result.getContent().get(0).getId());
        assertEquals(1L, result.getContent().get(0).getWorktype().getId());
        assertEquals("Salary", result.getContent().get(0).getWorktype().getName());

        assertEquals(25000.0, result.getContent().get(1).getSalary());
        assertEquals("Chief engineer", result.getContent().get(1).getName());
        assertEquals(4L, result.getContent().get(1).getId());
        assertEquals(1L, result.getContent().get(1).getWorktype().getId());
        assertEquals("Salary", result.getContent().get(1).getWorktype().getName());

        assertEquals(20000.0, result.getContent().get(2).getSalary());
        assertEquals("Engineer", result.getContent().get(2).getName());
        assertEquals(3L, result.getContent().get(2).getId());
        assertEquals(1L, result.getContent().get(2).getWorktype().getId());
        assertEquals("Salary", result.getContent().get(2).getWorktype().getName());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void patchUpdateTest() {
        PositionEntity positionToUpdate = positionService.findPositionByName("Engineer");
        PositionEntity updateData = PositionEntityTestData.returnEngineerPosition();

        assertNotNull(positionToUpdate);

        assertEquals(20000.0, positionToUpdate.getSalary());

        PositionEntity result = positionService.patchUpdate(positionToUpdate, updateData);

        assertEquals(1000.0, result.getSalary());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void worktypeCheckByWorktypeDTOTest() {
        WorktypeDTO dto = new WorktypeDTO();
        dto.setName("Salary");
        dto.setId(1L);

        WorktypeEntity result = positionService.worktypeCheck(dto);
        assertEquals(1L, result.getId());
        assertEquals("Salary", result.getName());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void worktypeCheckByWorktypeNameTest() {
        WorktypeEntity result = positionService.worktypeCheck("Salary");
        assertEquals(1L, result.getId());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
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
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void getCountOfTotalItemsTest() {
        assertEquals(5, positionService.getCountOfTotalItems());
    }
}