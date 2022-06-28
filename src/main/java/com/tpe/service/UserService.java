package com.tpe.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tpe.controller.dto.RegisterRequest;
import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.UserRole;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.RoleRepository;
import com.tpe.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired PasswordEncoder passwordEncoder;
	
	public void registerUser(RegisterRequest request) {
		
		if(userRepository.existsByUserName(request.getUserName())) {
			throw new ConflictException("User already registered");
		}
		
		Role role=roleRepository.findByName(UserRole.ROLE_STUDENT).
		orElseThrow(()->new ResourceNotFoundException("Role Not Found"));
		
		Set<Role> roles=new HashSet<>();
		
		roles.add(role);
		
		User user=new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setUserName(request.getUserName());
		
		//password encode ediliyor.
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRoles(roles);
		
		userRepository.save(user);
	}
	
	
}
