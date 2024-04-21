package io.psquared.blog.services;

import io.psquared.blog.entity.Category;
import io.psquared.blog.exceptions.NotFound;
import io.psquared.blog.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(String name){
        Category newCategory = new Category(name);
        return categoryRepository.save(newCategory);
    }

    public Optional<Category> findCategory(long id) {
        return categoryRepository.findById(id);
    }

    public Category replaceCategory(long id, String name) {
        Category category = categoryRepository
                .findById(id).orElseThrow(() -> new NotFound("No category with id: " + id));

        category.setName(name);
        return categoryRepository.save(category);
    }

    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
