package com.example.blogging.website.controllers;

import com.example.blogging.website.payload.ApiResponse;
import com.example.blogging.website.payload.CategoryDto;
import com.example.blogging.website.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCatogory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto cat = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(cat, HttpStatus.CREATED  );
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
        CategoryDto cat = this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(cat,HttpStatus.OK);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("category deleted Successfully",true),HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){
        CategoryDto cat = this.categoryService.getCategory(categoryId);
        return new ResponseEntity<>(cat,HttpStatus.FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAll(){
        List<CategoryDto> categories = this.categoryService.getCategories();
        return new ResponseEntity<>(categories,HttpStatus.FOUND);
    }
}
