package in.product.converters;

import in.product.entities.Category;
import in.product.requests.CategoryRequest;
import in.product.response.CategoryResponse;

public class CategoryConverter {

	public static Category categoryDtoToCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        return category;
    }

    
    public static CategoryResponse toResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setDescription(category.getDescription());
        return categoryResponse;
}
}