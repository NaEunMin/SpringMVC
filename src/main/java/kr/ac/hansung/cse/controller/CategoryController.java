package kr.ac.hansung.cse.controller;

import kr.ac.hansung.cse.exception.DuplicateCategoryException;
import kr.ac.hansung.cse.model.CategoryForm;
import kr.ac.hansung.cse.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록 조회
     */
    @GetMapping()
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categoryList";
    }

    /**
     * 카테고리 등록 폼 표시
     */
    @GetMapping("/create")
    public String showCategoryForm(Model model) {
        model.addAttribute("categoryForm", new CategoryForm());
        return "categoryForm";
    }

    /**
     * 카테고리 등록 로직
     */
    @PostMapping("/create")
    public String createCategory(
            CategoryForm categoryForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "categoryForm";
        }
        try {
            categoryService.createCategory(categoryForm);
        } catch (DuplicateCategoryException e) {
            //오류핸들링
            bindingResult.rejectValue("name", "duplicate", e.getMessage());
            return "categoryForm";
        }
        //정상상황에서 등록완료알림
        redirectAttributes.addFlashAttribute("successMessage",
                "'" + categoryForm.getName() + "' 카테고리가 등록되었습니다.");
        return "redirect:/categories";
    }

    @PostMapping("{id}/delete")
    public String deleteCategory(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes){
        try{
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("successMessage","카테고리가 성공적으로 삭제되었습니다.");
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("errorMessage",e.getMessage());
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/categories";
    }
}
