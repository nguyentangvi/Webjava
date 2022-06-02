package poly.store.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import poly.store.entity.Product;

public interface ProductService {

	List<Product> findAll();

	Product findByid(Integer id);

	List<Product> findByCategoryId(String cid);

	Product create(Product product);

	Product update(Product product);

	void delete(Integer id);

Page<Product> findByNameContaining(String name, Pageable pageable);
	
	

	Page<Product> findAll(Pageable pageable);

	Page<Product> findByKeyWord(String keyword, Pageable pageable);

	Page<Product> findByProductCate(String categoryID, Pageable pageable);
	List<Product> findByProductCate(String categoryID);

}
