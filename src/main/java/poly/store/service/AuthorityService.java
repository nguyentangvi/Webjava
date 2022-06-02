package poly.store.service;

import java.util.List;

import poly.store.entity.Authority;

public interface AuthorityService {

	void save(Authority au);

	void delete(Integer id);

	Authority create(Authority auth);

	List<Authority> findAuthoritiesOfAdministrators();

	List<Authority> findAll();
	
}
