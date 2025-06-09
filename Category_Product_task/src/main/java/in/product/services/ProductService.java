package in.product.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.product.converters.ProductConverter;
import in.product.entities.Category;
import in.product.entities.Product;
import in.product.exceptions.CategoryDoesNotExist;
import in.product.exceptions.ProductAlreadyExists;
import in.product.exceptions.ProductDoesNotExist;
import in.product.repositories.CategoryRepository;
import in.product.repositories.ProductRepository;
import in.product.requests.ProductRequest;
import in.product.response.ProductResponse;
import org.springframework.data.domain.Pageable;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	public Page<ProductResponse> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductConverter::toResponse);
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        // Checks category existence
        Category category = categoryRepository.findById(productRequest.getCategoryId())
            .orElseThrow(()->new CategoryDoesNotExist("Category not found with ID: " + productRequest.getCategoryId()));

        // Optional: Check if product already exists by name and brand
        Optional<Product> existingProduct = productRepository.findByNameAndBrand(productRequest.getName(), productRequest.getBrand());
        if (existingProduct.isPresent()) {
            throw new ProductAlreadyExists("Product with name" + productRequest.getName() + " and brand " + productRequest.getBrand() + "already exists");
        }

        Product product = ProductConverter.productDtoToProduct(productRequest, category);
        Product saved = productRepository.save(product);
        return ProductConverter.toResponse(saved);
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(()->new ProductDoesNotExist("Product not found with ID: " + id));
        return ProductConverter.toResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductDoesNotExist("Product not found with ID: " + id));

        Category category = categoryRepository.findById(productRequest.getCategoryId())
            .orElseThrow(() -> new CategoryDoesNotExist("Category not found with ID: " + productRequest.getCategoryId()));

        // Optional: Check if product with same name and brand exists (exclude self)
        Optional<Product> productByNameAndBrand = productRepository.findByNameAndBrand(productRequest.getName(), productRequest.getBrand());
        if (productByNameAndBrand.isPresent() && !productByNameAndBrand.get().getId().equals(id)) {
            throw new ProductAlreadyExists("Product with name" + productRequest.getName() + "and brand" + productRequest.getBrand() + "already exists");
        }

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setBrand(productRequest.getBrand());
        product.setInStock(productRequest.getInStock());
        product.setCategory(category);

        Product updated = productRepository.save(product);
        return ProductConverter.toResponse(updated);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductDoesNotExist("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
