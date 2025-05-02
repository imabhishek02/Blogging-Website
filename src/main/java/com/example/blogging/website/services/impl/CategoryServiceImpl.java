package com.example.blogging.website.services.impl;

import com.example.blogging.website.entity.Category;
import com.example.blogging.website.exception.ResourceNotFoundException;
import com.example.blogging.website.payload.CategoryDto;
import com.example.blogging.website.repository.CategoryRepo;
import com.example.blogging.website.repository.UserRepo;
import com.example.blogging.website.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = modelMapper.map(categoryDto,Category.class);
        Category addCat =  this.categoryRepo.save(cat);
        return this.modelMapper.map(addCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
       Category cat = this.categoryRepo.findById(categoryId)
               .orElseThrow(()->new ResourceNotFoundException("Category ","Category Id ",categoryId));
       cat.setCategoryTile(categoryDto.getCategoryTile());
       cat.setCategoryDescription(categoryDto.getCategoryDescription());
       Category updateCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(updateCat,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
      Category cat = this.categoryRepo.findById(categoryId)
              .orElseThrow(()->new ResourceNotFoundException("Category ","CategoryId ",categoryId));
      this.categoryRepo.deleteById(categoryId);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category ","CategoryId ",categoryId));

        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {

        List<Category> categories = this.categoryRepo.findAll();
       List<CategoryDto> catDto =  categories.stream().map((cat)->this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
        return catDto;
    }
}
