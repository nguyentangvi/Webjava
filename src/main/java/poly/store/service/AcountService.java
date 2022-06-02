package poly.store.service;

import java.util.List;

import poly.store.entity.Account;

public interface AcountService{
	public List<Account> findAll();
	Account findById(String username);
	public List<Account> getAdministrator();
}

