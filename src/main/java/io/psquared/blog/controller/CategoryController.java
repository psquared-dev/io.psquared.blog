package io.psquared.blog.controller;

import io.psquared.blog.dto.CategoryRequest;
import io.psquared.blog.dto.CategoryResponse;
import io.psquared.blog.entity.Category;
import io.psquared.blog.exceptions.type.NotFound;
import io.psquared.blog.repository.CategoryRepository;
import io.psquared.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public CategoryController(CategoryRepository categoryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        CategoryResponse categoryResponse = new CategoryResponse();
        Category category = categoryService.addCategory(categoryRequest.getName());
        System.out.println(category);
        BeanUtils.copyProperties(category, categoryResponse);
        return categoryResponse;
    }

    @GetMapping("/{id}")
    public CategoryResponse addCategory(@PathVariable long id, Authentication authentication){
        System.out.println(authentication.getName());
        System.out.println(authentication.getAuthorities());
        CategoryResponse categoryResponse = new CategoryResponse();
        Category category = categoryService
                .findCategory(id)
                .orElseThrow(() -> new NotFound("No category with Id: " + id));
        BeanUtils.copyProperties(category, categoryResponse);
        return categoryResponse;
    }

    @PutMapping("/{id}")
    public CategoryResponse replaceCategory(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable long id){
        CategoryResponse categoryResponse = new CategoryResponse();
        Category category = categoryService.replaceCategory(id, categoryRequest.getName());
        BeanUtils.copyProperties(category, categoryResponse);
        return categoryResponse;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
    }
}
