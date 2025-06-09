package in.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import in.product.requests.ProductRequest;
import in.product.response.ProductResponse;
import in.product.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	//To get all products based on page and size
	@GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Page<ProductResponse> products = productService.getAllProducts(page, size);
        return ResponseEntity.ok(products);
    }

    // To create a new product
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.createProduct(productRequest);
        return ResponseEntity.ok(response);
    }

    // To get product by Id
    @GetMapping("/{di}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(name = "di") Long id) {
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    // To update product by Id
    @PutMapping("/{di}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable(name = "di") Long id,
            @RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(response);
    }

    // To delete product by Id
    @DeleteMapping("/{di}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "di") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
