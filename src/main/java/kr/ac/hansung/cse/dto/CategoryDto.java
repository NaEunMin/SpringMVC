package kr.ac.hansung.cse.dto;

import kr.ac.hansung.cse.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private Long id;
    private String name;

    public static CategoryDto from(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.id = category.getId();
        dto.name = category.getName();
        return dto;
    }
}
