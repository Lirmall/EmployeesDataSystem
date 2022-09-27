package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.klokov.employeesdatasystem.entities.RangeEntity;
import ru.klokov.employeesdatasystem.specifications.rangesSpecification.RangeSearchModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class RangeServiceTest {

    @Autowired
    private RangeService rangeService;

    @Test
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
    void findByIdTest() {
        RangeEntity rangeEntity = rangeService.findById(1L);

        assertEquals(1L, rangeEntity.getId());
        assertEquals("1 range", rangeEntity.getName());
    }

    @Test
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
    void getCountOfTotalItemsTest() {
        assertEquals(4L, rangeService.getCountOfTotalItems());
    }
}