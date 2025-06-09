package in.product.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.product.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Serializable>{

	 Optional<Category> findByName(String name);
}
