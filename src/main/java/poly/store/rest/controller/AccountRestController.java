package poly.store.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.store.entity.Account;
import poly.store.service.AcountService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/accounts")
public class AccountRestController {
	@Autowired
	AcountService accountService;
	
	@GetMapping
	public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin){
		if(admin.orElse(false)) { // nếu là false tải tất cả
			return accountService.getAdministrator();
		}
		return accountService.findAll();
	}

	
}
