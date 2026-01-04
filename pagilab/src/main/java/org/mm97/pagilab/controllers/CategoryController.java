package org.mm97.pagilab.controllers;

import org.mm97.pagilab.dto.CategoryDto;
import org.mm97.pagilab.mappers.ApiMapper;
import org.mm97.pagilab.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> getAllCategories(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(categoryService.getCategoryPage(pageable).map(ApiMapper::toCategoryDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int id) {
        return ResponseEntity.ok(ApiMapper.toCategoryDto(categoryService.getCategoryById(id)));
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<Page<CategoryDto>> getByName(
            @RequestParam String name,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(categoryService.getByName(name, pageable).map(ApiMapper::toCategoryDto));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategoryDto>> searchByName(
            @RequestParam String q,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(categoryService.searchByName(q, pageable).map(ApiMapper::toCategoryDto));
    }

    @GetMapping("/by-film/{filmId}")
    public ResponseEntity<List<CategoryDto>> getCategoriesByFilmId(@PathVariable int filmId) {
        return ResponseEntity.ok(
                categoryService.getCategoriesByFilmId(filmId).stream().map(ApiMapper::toCategoryDto).toList()
        );
    }
}