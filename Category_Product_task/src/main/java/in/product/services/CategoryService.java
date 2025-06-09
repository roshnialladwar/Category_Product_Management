package in.product.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import in.product.converters.CategoryConverter;
import in.product.entities.Category;
import in.product.exceptions.CategoryAlreadyExists;
import in.product.exceptions.CategoryDoesNotExist;
import in.product.repositories.CategoryRepository;
import in.product.requests.CategoryRequest;
import in.product.response.CategoryResponse;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
    
	 public Page<CategoryResponse> getAllCategories(int page, int size) {
	        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
	        Page<Category> categories = categoryRepository.findAll(pageable);
	        return categories.map(CategoryConverter::toResponse);
	    }
	 
	 public CategoryResponse createCategory(CategoryRequest categoryRequest) {
	        Optional<Category> existingCategory = categoryRepository.findByName(categoryRequest.getName());
	        if (existingCategory.isPresent()) {
	            throw new CategoryAlreadyExists("Category with name" + categoryRequest.getName() + "already exists");
	        }
	        Category category = CategoryConverter.categoryDtoToCategory(categoryRequest);
	        Category saved = categoryRepository.save(category);
	        return CategoryConverter.toResponse(saved);
	    }
	 
	 public CategoryResponse getCategoryById(Long id) {
	        Category category = categoryRepository.findById(id)
	            .orElseThrow(()->new CategoryDoesNotExist("Category not found with ID: " + id));
	        return CategoryConverter.toResponse(category);
	    }

	    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
	        Category category = categoryRepository.findById(id)
	            .orElseThrow(()->new CategoryDoesNotExist("Category not found with ID: " + id));

	        // Optional:To avoid NullPointerException
	        Optional<Category> categoryByName = categoryRepository.findByName(categoryRequest.getName());
	        if (categoryByName.isPresent() && !categoryByName.get().getId().equals(id)) {
	            throw new CategoryAlreadyExists("Category with name " + categoryRequest.getName() + "already exists");
	        }

	        category.setName(categoryRequest.getName());
	        category.setDescription(categoryRequest.getDescription());
	        Category updated = categoryRepository.save(category);
	        return CategoryConverter.toResponse(updated);
	    }

	    public void deleteCategory(Long id) {
	        if (!categoryRepository.existsById(id)) {
	            throw new CategoryDoesNotExist("Category not found with ID: " + id);
	        }
	        categoryRepository.deleteById(id);
	    }
	
}
