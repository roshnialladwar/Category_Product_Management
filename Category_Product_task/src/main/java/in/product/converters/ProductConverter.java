package in.product.converters;

import in.product.entities.Category;
import in.product.entities.Product;
import in.product.requests.ProductRequest;
import in.product.response.ProductResponse;

public class ProductConverter {

	public static Product productDtoToProduct(ProductRequest productRequest, Category category) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setBrand(productRequest.getBrand());
        product.setInStock(productRequest.getInStock());
        product.setCategory(category);
        return product;
    }

    // Convert Entity to DTO including category details
    public static ProductResponse toResponse(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setBrand(product.getBrand());
        dto.setInStock(product.getInStock());

        if (product.getCategory() != null) {
            dto.setCategory(CategoryConverter.toResponse(product.getCategory()));
        }

        return dto;
}
}
