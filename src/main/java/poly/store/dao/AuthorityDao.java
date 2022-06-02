package poly.store.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.store.entity.Account;
import poly.store.entity.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Integer>{	
	@Query("SELECT DISTINCT a FROM Authority a WHERE a.account IN ?1") // quyen duoc cap nhung account nam trong nhom tai khoang admin
	List<Authority> authoritiesOf(List<Account> accounts);
}
