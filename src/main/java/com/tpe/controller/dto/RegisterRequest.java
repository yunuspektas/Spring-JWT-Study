package com.tpe.controller.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterRequest {
	
    @NotBlank(message="Please provide not blank first name")
    @NotNull(message="Please provide your first name")
    @Size(min=1, max=15, message="Your first name '${validatedValue}' must be between {min} and {max} chars long")
	private String firstName;
	

    @NotBlank(message="Please provide not blank last name")
    @NotNull(message="Please provide your last name")
    @Size(min=1, max=15, message="Your last name '${validatedValue}' must be between {min} and {max} chars long")
	private String lastName;
	


    @NotBlank(message="Please provide not blank username")
    @NotNull(message="Please provide your username")
    @Size(min=5, max=20, message="Your username'${validatedValue}' must be between {min} and {max} chars long")
	private String userName;
	


    @NotBlank(message="Please provide not blank password")
    @NotNull(message="Please provide your password")
    @Size(min=5, max=20, message="Your password'${validatedValue}' must be between {min} and {max} chars long")
	private String password;
	
	
	private Set<String> roles;
	
}
