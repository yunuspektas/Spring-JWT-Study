package com.tpe.security.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tpe.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String userName;
	
	@JsonIgnore  // bu class eğer dışarı çıkarsa , password fieldının dışarı çıkmasını önlüyor
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities; // Spring Security benim rolelerimi bilmez, 
	//									o GrantedAuthority tipinde objeler üzerinden role dağılımı yapıyor 															
	
	
	public static UserDetailsImpl build(User user) { // bu fonksiyon sadese userın role tanımlamasını securitye izah etmek için oluşturuldu
		List<SimpleGrantedAuthority> authorities= user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName().name())).
		collect(Collectors.toList());  // DB deki user ın role yapısını SpringSewcurity nin anlayacağı GrantedAuthority 
		//								türünde yapılara dönüştürdüm
		//   soru : niye List olarak tutuldu ? cevap : bir userın birden fazla rolü olabilir.
		
		return new UserDetailsImpl(user.getId(),user.getUserName(),user.getPassword(),authorities);
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
