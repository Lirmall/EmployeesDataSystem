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
import ru.klokov.employeesdatasystem.entities.GenderEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;
import ru.klokov.employeesdatasystem.repositories.GenderRepository;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.specifications.gendersSpecification.GendersSearchModel;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {SortColumnChecker.class, GenderRepository.class, GenderService.class,
        SecurityConfig.class, DefaultPermissionEvaluator.class})
@ActiveProfiles("dataJpaTest")
class GenderServiceTest {

    @Autowired
    private GenderService genderService;

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.GENDERS_SCHEMA,
            StaticSqlSchemaClasspathes.GENDERS_DATA})
    void findAllTest() {
        int gendersCount = 2;
        List<GenderEntity> genderEntities = genderService.findAll();

        assertEquals(gendersCount, genderEntities.size());

        assertEquals(1L, genderEntities.get(0).getId());
        assertEquals("Male", genderEntities.get(0).getName());

        assertEquals(2L, genderEntities.get(1).getId());
        assertEquals("Female", genderEntities.get(1).getName());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.GENDERS_SCHEMA,
            StaticSqlSchemaClasspathes.GENDERS_DATA})
    void findByIdTest() {
        Long id = 1L;

        GenderEntity genderEntity = genderService.findById(id);

        assertEquals(id, genderEntity.getId());
        assertEquals("Male", genderEntity.getName());

        assertThrows(NullOrEmptyArgumentexception.class, () -> genderService.findById(null));

        assertThrows(NoMatchingEntryInDatabaseException.class, () -> genderService.findById(3L));
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.GENDERS_SCHEMA,
            StaticSqlSchemaClasspathes.GENDERS_DATA})
    void findGenderByNameTest() {
        String name = "Male";

        GenderEntity genderEntity = genderService.findGenderByName(name);

        assertEquals(1L, genderEntity.getId());
        assertEquals("Male", genderEntity.getName());

        assertThrows(NullOrEmptyArgumentexception.class, () -> genderService.findGenderByName(null));
        assertThrows(NullOrEmptyArgumentexception.class, () -> genderService.findGenderByName(""));
        assertThrows(NoMatchingEntryInDatabaseException.class, () -> genderService.findGenderByName("Test"));
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.GENDERS_SCHEMA,
            StaticSqlSchemaClasspathes.GENDERS_DATA})
    void findByFilterTest() {
        GendersSearchModel gendersSearchModel = new GendersSearchModel();
        gendersSearchModel.setIds(Arrays.asList(1L, 2L));
        gendersSearchModel.setSortColumn("-id");

        Page<GenderEntity> entityPage = genderService.findByFilter(gendersSearchModel);

        assertEquals("Female", entityPage.getContent().get(0).getName());
    }
}