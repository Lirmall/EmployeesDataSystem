package ru.klokov.employeesdatasystem.services;

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
import ru.klokov.employeesdatasystem.entities.RangeEntity;
import ru.klokov.employeesdatasystem.repositories.RangeRepository;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.specifications.rangesSpecification.RangeSearchModel;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {SortColumnChecker.class, RangeRepository.class, RangeService.class,
        SecurityConfig.class, DefaultPermissionEvaluator.class})
@ActiveProfiles("dataJpaTest")
class RangeServiceTest {

    @Autowired
    private RangeService rangeService;

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA})
    void findAllTest() {

        List<RangeEntity> entities = rangeService.findAll();
        assertEquals(4, entities.size());

        RangeEntity rangeEntity = entities.get(0);

        assertEquals(1L, rangeEntity.getId());
        assertEquals("1 range", rangeEntity.getName());

        rangeEntity = entities.get(1);

        assertEquals(2L, rangeEntity.getId());
        assertEquals("2 range", rangeEntity.getName());

        rangeEntity = entities.get(2);

        assertEquals(3L, rangeEntity.getId());
        assertEquals("3 range", rangeEntity.getName());

        rangeEntity = entities.get(3);

        assertEquals(4L, rangeEntity.getId());
        assertEquals("Non-range", rangeEntity.getName());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA})
    void findByIdTest() {
        RangeEntity rangeEntity = rangeService.findById(1L);

        assertEquals(1L, rangeEntity.getId());
        assertEquals("1 range", rangeEntity.getName());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA})
    void findByFilterTest() {
        RangeSearchModel rangeSearchModel = new RangeSearchModel();

        rangeSearchModel.setIds(Arrays.asList(1L, 2L, 3L, 4L));
        rangeSearchModel.setSortColumn("-id");

        Page<RangeEntity> entities = rangeService.findByFilter(rangeSearchModel);

        assertEquals(4, entities.getTotalElements());

        assertEquals(4L, entities.getContent().get(0).getId());

        rangeSearchModel.setIds(Collections.emptyList());
        rangeSearchModel.setNames(Collections.emptyList());

        entities = rangeService.findByFilter(rangeSearchModel);

        assertEquals(Page.empty(), entities);
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA})
    void getCountOfTotalItemsTest() {
        assertEquals(4L, rangeService.getCountOfTotalItems());
    }
}