package in.product.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.product.entities.Product;



@Repository
public interface ProductRepository extends JpaRepository<Product, Serializable>{

	 Optional<Product> findByNameAndBrand(String name, String brand);
}
