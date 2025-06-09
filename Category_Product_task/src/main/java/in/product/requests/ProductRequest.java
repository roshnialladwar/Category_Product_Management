package in.product.requests;

import in.product.entities.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

	private String name;
	private Double price;
	private String brand;
	private Boolean inStock;
	private Long categoryId;
}
