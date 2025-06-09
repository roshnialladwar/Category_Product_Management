package in.product.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import in.product.requests.CategoryRequest;
import in.product.response.CategoryResponse;
import in.product.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	   //To get all categories based on page number and size
	    @GetMapping
	    public ResponseEntity<Page<CategoryResponse>> getAllCategories(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "1") int size) {
	        Page<CategoryResponse> categories = categoryService.getAllCategories(page, size);
	        return ResponseEntity.ok(categories);
	    }

	    // To create a new category
	    @PostMapping
	    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
	        CategoryResponse response = categoryService.createCategory(categoryRequest);
	        return ResponseEntity.ok(response);
	    }

	    // To get category by Id
	    @GetMapping("/{di}")
	    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable(name = "di") Long id) {
	        CategoryResponse response = categoryService.getCategoryById(id);
	        return ResponseEntity.ok(response);
	    }

	    // To update category by Id
	    @PutMapping("/{di}")
	    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable(name = "di") Long id, @RequestBody CategoryRequest categoryRequest) {
	        CategoryResponse response = categoryService.updateCategory(id, categoryRequest);
	        return ResponseEntity.ok(response);
	    }

	    // To delete category by Id
	    @DeleteMapping("/{di}")
	    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "di") Long id) {
	        categoryService.deleteCategory(id);
	        return ResponseEntity.noContent().build();
	    }
	}
