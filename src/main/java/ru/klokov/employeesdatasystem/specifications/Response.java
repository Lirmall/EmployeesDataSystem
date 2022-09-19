package ru.klokov.employeesdatasystem.specifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Response<T> {
//    private String items = "items: ";
//    private Long totalItems;
//    private String stringOfTotalItems = "totalItems: " + totalItems;
//    private Long filteredCount;
//    private String stringOfFilteredCount = "filteredCount: " + filteredCount;

    private List<T> items;
    private Long totalItems;
    private Long filteredCount;

    public void toResponse(Page<T> page, Long totalItems, Long filteredCount) {
        this.setItems(page.toList());
        this.setTotalItems(totalItems);
        this.setFilteredCount(filteredCount);
    }

    public void toResponse(Long totalItems) {
        this.setTotalItems(totalItems);
        this.setFilteredCount(0L);
    }
}
