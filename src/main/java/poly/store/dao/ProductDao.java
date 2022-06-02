package poly.store.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import poly.store.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer>{

	@Query("SELECT p FROM Product p WHERE p.category.id= ?1 ")
	List<Product> findByCategoryId(String cid);

	 Page<Product> findByNameContaining(String name, Pageable pageable);

	

	 @Query(value = "select * from products where name like %?1%", nativeQuery = true)
	Page<Product> findByKeyWord(String keyword, Pageable pageable);
	 
	 @Query(value = "select * from products where categoryid like ?1", nativeQuery = true)
	    Page<Product> findByProductCate(String categoryID, Pageable pageable);

	 @Query(value = "select * from duantotnghiep.products where categoryid = ?", nativeQuery = true)
	 List<Product> findByCategoryProductHome(String categoryID);


	
}
