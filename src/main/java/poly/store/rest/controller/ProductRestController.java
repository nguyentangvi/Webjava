package poly.store.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.store.entity.Product;
import poly.store.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
	@Autowired
	ProductService productService;

	@GetMapping("{id}")
	public Product getOne(@PathVariable("id") Integer id) { // get one lay san pham theo id
		return productService.findByid(id);
	}

	@GetMapping()
	public List<Product> getall() { // get all để lấy hết sp về bỏ vào list
		return productService.findAll();
	}
	@PostMapping
	public Product create(@RequestBody Product product) { // phuong thuc create tao
		return productService.create(product);
	}
	@PutMapping("{id}")
	public Product update(@PathVariable("id") Integer id ,@RequestBody Product product) { // put cap nhat
		return productService.update(product);
	}
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id ) { // deletemaping xóa
		 productService.delete(id);
	}
}
