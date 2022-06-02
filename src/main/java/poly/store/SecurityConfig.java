package poly.store;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import poly.store.entity.Account;
import poly.store.service.AcountService;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter{
@Autowired
AcountService accountservice;
@Autowired
BCryptPasswordEncoder pe;

// cung cấp dữ liệu đăng nhập
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception{
	auth.userDetailsService(username -> {
		try {
			
			Account user = accountservice.findById(username);
			String password = pe.encode(user.getPassword()); //mã hóa
			String[] roles = user.getAuthorities().stream() // vai trò
					.map(er -> er.getRole().getId())
					.collect(Collectors.toList()).toArray(new String[0]);
			return User.withUsername(username).password(password).roles(roles).build();
		} catch (NoSuchElementException e) {
			throw new UsernameNotFoundException(username + "not found!");
		}
		
	});
}
	// phân quyền
@Override
protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/order/**").authenticated() // order thi phai dang nhap 
		.antMatchers("/admin/**").hasAnyRole("STAF","DIRE") // phan quyen giam doc nhan vien khach hang
		.antMatchers("/rest/authorities").hasRole("DIRE")
		.anyRequest().permitAll();
		
		http.formLogin()
		.loginPage("/security/login/form")
		.loginProcessingUrl("/security/login")
		.defaultSuccessUrl("/security/login/success",false)
		.failureUrl("/security/login/error");
		
		http.rememberMe()
		.tokenValiditySeconds(86400);
		
		http.exceptionHandling()
		.accessDeniedPage("/security/unauthoried");
		
		http.logout()
		.logoutUrl("/security/logoff")
		.logoutSuccessUrl("/security/logoff/success");
}

// co che ma hoa mat khau
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

		// CHO PHEP TRUY XUAT RESTAPT TU BEN NGOAI (DOMAIN KHAC)
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**");
	}

}
