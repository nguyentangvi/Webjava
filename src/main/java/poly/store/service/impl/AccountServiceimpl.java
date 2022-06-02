package poly.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.store.dao.AccountDao;
import poly.store.entity.Account;
import poly.store.service.AcountService;

@Service
public class AccountServiceimpl implements AcountService {
@Autowired
AccountDao dao;

@Override
public Account findById(String username) {
	return dao.findById(username).get();
}

public List<Account> findAll(){
	return dao.findAll();
}
public List<Account> getAdministrator(){
	return dao.getAdministrators();
}

}
