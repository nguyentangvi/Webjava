package poly.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import poly.store.dao.ProductDao;
import poly.store.entity.Product;
import poly.store.service.ProductService;




@Service
public class ProductServiceImpl implements ProductService {
@Autowired
	ProductDao dao;
	
@Override
public Page<Product> findByNameContaining(String name, Pageable pageable) {
	return dao.findByNameContaining(name, pageable);
}
@Override
public Page<Product> findByKeyWord(String keyword, Pageable pageable) {
	// TODO Auto-generated method stub
	if(keyword!=null) {
		return dao.findByKeyWord(keyword,pageable);
	}
	return dao.findAll(pageable);
	
}
@Override
public Page<Product> findByProductCate(String categoryID, Pageable pageable) {
	// TODO Auto-generated method stub
	return dao.findByProductCate(categoryID,pageable);
}
@Override
public List<Product> findByProductCate(String categoryID) {
	// TODO Auto-generated method stub
	return dao.findByCategoryProductHome(categoryID);
}
@Override
public Page<Product> findAll(Pageable pageable) {
	return dao.findAll(pageable);
}


	@Override
	public List<Product> findAll() {
		return dao.findAll();
	}

	@Override
	public Product findByid(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get() ;
	}

	@Override
	public List<Product> findByCategoryId(String cid) {
		
		return dao.findByCategoryId(cid);
	}

	@Override
	public Product create(Product product) {		
		return dao.save(product);
	}

	@Override
	public Product update(Product product) {
		return dao.save(product);
	}

	@Override
	public void delete(Integer id) {
		
		 dao.deleteById(id);
	}
	
	
	

	

}