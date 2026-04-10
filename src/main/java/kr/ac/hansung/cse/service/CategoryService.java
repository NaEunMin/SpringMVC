package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.dto.CategoryDto;
import kr.ac.hansung.cse.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // ============= 카테고리 관리 ==================== */
    /**
     * 카테고리 목록 조회
     */
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::from)
                .toList();
    }

}
