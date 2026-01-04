package org.mm97.pagilab.services;

import org.mm97.pagilab.exceptions.ResourceNotFoundException;
import org.mm97.pagilab.models.Category;
import org.mm97.pagilab.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> getCategoryPage(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category getCategoryById(int id) {
        validatePositiveId(id, "categoryId");
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public Page<Category> getByName(String name, Pageable pageable) {
        return categoryRepository.findByNameContainingIgnoreCase(requireText(name, "name"), pageable);
    }

    public Page<Category> searchByName(String name, Pageable pageable) {
        return categoryRepository.findByNameContainingIgnoreCase(requireText(name, "name"), pageable);
    }

    public List<Category> getCategoriesByFilmId(int filmId) {
        validatePositiveId(filmId, "filmId");
        return categoryRepository.findAllByFilmId(filmId);
    }

    private void validatePositiveId(int value, String fieldName) {
        if (value <= 0) throw new IllegalArgumentException(fieldName + " must be > 0");
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(fieldName + " must not be blank");
        return value.trim();
    }
}