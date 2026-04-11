package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.dto.CategoryDto;
import kr.ac.hansung.cse.exception.DuplicateCategoryException;
import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.model.CategoryForm;
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


    /**
     * 카테고리 등록
     */
    @Transactional
    public void createCategory(CategoryForm categoryForm) {

        //이름 중복 검증
        if(categoryRepository.findByName(categoryForm.getName()).isPresent()) {
            throw new DuplicateCategoryException("이미 존재하는 카테고리 이름입니다.");
        }

        //저장
        Category category = new Category(categoryForm.getName());
        categoryRepository.save(category);
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Long id) {

        //결과가 있다면 상품이 연결되어 있다는 것 -> 삭제 불가
        if(categoryRepository.findByIdWithProducts(id).isPresent()) {
            throw new IllegalStateException("해당 카테고리에 연결된 상품이 있어 삭제할 수 없습니다.");
        }

        //카테고리가 없다면 예외처리
        Category category = categoryRepository.findById(id)
                        .orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리입니다."));


        categoryRepository.delete(category);
    }

}
