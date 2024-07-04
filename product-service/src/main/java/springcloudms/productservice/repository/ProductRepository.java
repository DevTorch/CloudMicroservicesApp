package springcloudms.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import springcloudms.productservice.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {
}
